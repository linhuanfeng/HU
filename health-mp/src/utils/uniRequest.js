import uni_request from './uni_request.js';
import Qs from 'qs';
let afHLoading = true;
let reqConfig = null;
import config from '@/config';
const request = uni_request({
  // 有效配置项只有三个
  baseURL: config.VITE_APP_IMAGE_URL, //baseURL
  timeout: 8000, // 超时时间
  header: {}, // 设置请求头，建议放在请求拦截器中
  statusCode: [0, 200, 401, 20000], // 服务器相应状态码为 200/401 时，网络请求不会 reject
});

//request interceptors
request.interceptors.request.use(async (config, ...args) => {
  console.log('req', args);
  //save req for res to using
  reqConfig = config;
  return config;
});

//res interceptors
request.interceptors.response.use(async (res, ...args) => {
  //关闭微信loading
  uni.hideLoading();
  const { flag, msg, code, isNeedUpdateToken, updateToken } = res.data;
  const successCode = '0,200,20000';
  if (successCode.indexOf(code) !== -1) {
    return res.data;
  } else {
    if (reqConfig.isAlertErrorMsg) {
      if (res.data && res.data.message) {
        uni.showToast({
          title: `${res.data && res.data.message}`,
          icon: 'none',
          duration: 3000,
        });
      }
    }
  }
});
//全局错误监听
request.onerror = async (...args) => {
  // 请求失败统一处理方法（可以也可以使用异步方法）
  //关闭loading
  uni.showToast({
    title: `服务器或网络异常`,
    icon: 'none',
    duration: 3000,
  });
  uni.hideLoading();
};

export const uniRequest = function uniRequest({ url, data, method, isParams, bfLoading, afHLoading, isAlertErrorMsg }) {
  //参数发动的形式
  if (isParams) url = `${url}?` + Qs.stringify(data);
  //是否Loading
  if (bfLoading) uni.showLoading({ title: '数据加载中..', mask: true });
  afHLoading = afHLoading || true;
  return request[method](url, data);
};

export default uniRequest;
