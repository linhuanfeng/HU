import uniRequest from '@/utils/uniRequest';

/**
 * 获取评测题目
 */
export const evaluationQuestion = async () => {
  return uniRequest({
    url: '/api/ums/evaluation/list',
    method: 'get',
  });
};
/**
 * 获取评测报告
 */
export const evaluationReport = (score) => {
  return uniRequest({
    url: `/api/ums/report/result/ ${score}`,
    method: 'get',
  });
};
