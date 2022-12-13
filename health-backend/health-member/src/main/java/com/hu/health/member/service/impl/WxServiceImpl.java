package com.hu.health.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hu.health.common.exception.RRException;
import com.hu.health.member.entity.MemberEntity;
import com.hu.health.member.service.MemberService;
import com.hu.health.member.service.WxService;
import com.hu.health.member.config.WXConfig;
import com.hu.health.member.service.RestService;
import com.hu.health.member.to.LoginInfo;
import com.hu.health.member.utils.CalBMIUtil;
import com.hu.health.member.utils.WxPKCS7Encoder;
import com.hu.health.member.utils.WxUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 解密encryptedData，获取微信用户信息
 * 调用接口获取登录凭证（code）。通过凭证进而换取用户登录态信息，包括用户的唯一标识（openid）及本次登录的
 * 会话密钥（session_key）等。用户数据的加解密通讯需要依赖会话密钥完成。
 * <p>
 * 通过wx.login获取登录凭证code  ，然后调用后台接口wx.request发送参数code调用接口换取openid和session_key,
 * wx.checkSession检测当前用户登录态是否有效
 */
@Service
@Slf4j
public class WxServiceImpl implements WxService {

    @Autowired
    WXConfig wxConfig;

    @Autowired
    RestService restService;

    @Autowired
    MemberService userService;

    /**
     * 解密用户的数据
     *
     * @param loginInfo
     * @return User 用户信息
     */
    @Override
    public MemberEntity getUserInfo(LoginInfo loginInfo) {
        String code = loginInfo.getCode();
        String encryptedData = loginInfo.getEncryptedData();
        String iv = loginInfo.getIv();

        // 1.根据code获取sessionKey和openId
        Map tempMap = getSessionKeyOpenId(code);
//        Assert.notNull();
        if (tempMap.get("errcode") != null)
            throw new RRException((String) tempMap.get("errmsg"), (int) tempMap.get("errcode"));
        String sessionKey = (String) tempMap.get("session_key");
        String openId = (String) tempMap.get("openid");
        String unionId = (String) tempMap.get("unionid");

        // 2.根据sessionKey和 iv解密encryptedData，得到用户信息realMsg
        String realMsg = doGetUserInfo(encryptedData, sessionKey, iv);
        log.debug("根据sessionKey和 iv解密encryptedData后结果为{}", realMsg);
        if (!StringUtils.hasLength(realMsg)) throw new RRException("微信解密后的realMsg为null");
        // 2.1.调用工具函数处理用户信息realMsg，得到返回结果
        Map resMap = WxUtil.strToMap(realMsg);
        // 2.2.调用工具函数，加密openId
//        resMap.put("myEncrypt", EncryptUtil.encryptUtil(openId));

        return updateUser(openId, sessionKey, resMap);
    }

    /**
     * 更新用户信息或者插入新用户;同时为map添加userId和resume
     *
     * @param openId
     * @param sessionKey
     * @param map
     * @return User(返回用户)
     */
    public MemberEntity updateUser(String openId, String sessionKey, Map map) {
        if (!StringUtils.hasLength(openId)) {
            log.error("openId为null");
            throw new RRException("openId为空");
        }
        MemberEntity user = userService.getUserByOpenId(openId);
        Long userId;
        String resume = "";
        if (user != null) {
            userId = user.getId();
            // 因为用户信息可能会变，及时更新
            user.setNickname((String) map.getOrDefault("nickName", ""));
            user.setGender((Integer) map.getOrDefault("gender", 0));
            user.setCity((String) map.getOrDefault("city", ""));
            user.setProvince((String) map.getOrDefault("province", ""));
            user.setAvatar((String) map.getOrDefault("avatarUrl", ""));
            user.setSessionKey(sessionKey);
            boolean i = userService.update(user, new QueryWrapper<MemberEntity>().eq("id", user.getId()));
            log.info("老用户更新登录信息，update返回值：{}，完整信息：{}", i, user);
        } else {
            // 当前为新用户
            user = new MemberEntity();
            user.setOpenId(openId);
            user.setNickname((String) map.getOrDefault("nickName", ""));
            user.setGender((Integer) map.getOrDefault("gender", 0));
            user.setCity((String) map.getOrDefault("city", ""));
            user.setProvince((String) map.getOrDefault("province", ""));
            user.setAvatar((String) map.getOrDefault("avatarUrl", ""));
            user.setSessionKey(sessionKey);
            user.setUsername(user.getNickname()); // 新用户username默认等于nickName
            userId = userService.insertUser(user);
            log.info("新用户登录信息，完整信息：{}", user);
        }
        user.setBMI(CalBMIUtil.calBMI(user.getWeight(), user.getHeight()));
        return user;
    }

    /**
     * 获取sessionKey 调用官方接口
     * https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
     * 注意这里如果对getSessionKey进行增强的话会失败，因为调用者的不是增强后的方法，而是增强前的方法
     */
    public Map getSessionKeyOpenId(String code) {
        String protocol = "https";
        String url = wxConfig.getUrl();
        String appid = wxConfig.getAppid();
        String secret = wxConfig.getSecret();
        String js_code = code;
        String grant_type = wxConfig.getGrantType();
        // 请求参数
        HashMap<String, String> jsonMap = new HashMap<>();
        jsonMap.put("appid", appid);
        jsonMap.put("secret", secret);
        jsonMap.put("js_code", js_code);
        jsonMap.put("grant_type", grant_type);

        // 调用http客户端服务向微信接口code2Session获取session_key和openId
        String json = restService.sendGet(protocol, url, jsonMap);
        log.debug("调用http客户端服务向微信接口code2Session获取session_key和openId获取到的结果为{}", json);
        HashMap map = JSON.parseObject(json, HashMap.class);
        return map;
    }

    /**
     * 真正处理解密，并返回解密后字符串
     * {"nickName":"moon","gender":0,"language":"zh_CN","city":"","province":"","country":"","avatarUrl":"https://thirdwx.qlogo.cn/mmopen/vi_32/IUekiUPtlw/132",
     * "watermark":{"timestamp":1635261332,"appid":"wx6ba09ab0d4dcc6e3"}}
     *
     * @param encryptedData
     * @param sessionKey
     * @param iv
     * @return
     */
    public String doGetUserInfo(String encryptedData, String sessionKey, String iv) {
        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");

            BASE64Decoder base64Decoder = new BASE64Decoder();

            byte[] _encryptedData = base64Decoder.decodeBuffer(encryptedData);

            byte[] _sessionKey = base64Decoder.decodeBuffer(sessionKey);

            byte[] _iv = base64Decoder.decodeBuffer(iv);

            SecretKeySpec secretKeySpec = new SecretKeySpec(_sessionKey, "AES");

            IvParameterSpec ivParameterSpec = new IvParameterSpec(_iv);

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] original = cipher.doFinal(_encryptedData);

            byte[] bytes = WxPKCS7Encoder.decode(original);

            String originalString = new String(bytes, StandardCharsets.UTF_8);

            return originalString;
        } catch (
                Exception ex) {

            return null;

        }
    }

/**
 * 这种不知道为什么解密失败
 *
 * @param encryptedData
 * @param sessionKey
 * @param iv
 * @return
 */
//    @Deprecated
//    public JSONObject doGetUserInfo(String encryptedData, String sessionKey, String iv){
//        // 被加密的数据
//        byte[] dataByte = Base64.decode(encryptedData);
//        // 加密秘钥
//        byte[] keyByte = Base64.decode(sessionKey);
//        // 偏移量
//        byte[] ivByte = Base64.decode(iv);
//
//        try {
//            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
//            int base = 16;
//            if (keyByte.length % base != 0) {
//                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
//                byte[] temp = new byte[groups * base];
//                Arrays.fill(temp, (byte) 0);
//                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
//                keyByte = temp;
//            }
//            // 初始化
//            Security.addProvider(new BouncyCastleProvider());
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
//            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
//            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
//            parameters.init(new IvParameterSpec(ivByte));
//            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
//            byte[] resultByte = cipher.doFinal(dataByte);
//            if (null != resultByte && resultByte.length > 0) {
//                String result = new String(resultByte, "UTF-8");
//                return JSONObject.parseObject(result);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
