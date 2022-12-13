import uniRequest from '@/utils/uniRequest';

/**
 * 微信用户授权登录
 */
export const loginAuth = (data) => {
  return uniRequest({
    url: '/api/ums/wx/decryptInfo',
    method: 'post',
    data: {
      code: data.code,
      encryptedData: data.encryptedData,
      iv: data.iv,
    },
  });
};
/**
 * 用户授权后通过id登录
 */
export const loginId = (id) => {
  return uniRequest({
    url: `/api/ums/member/info/${id}`,
    method: 'get',
  });
};
/**
 * 个人资料修改
 */
export const updateUserInfo = async (data) => {
  return uniRequest({
    url: '/api/ums/member/update',
    method: 'post',
    data,
  });
};