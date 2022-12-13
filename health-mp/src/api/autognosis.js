import uniRequest from '@/utils/uniRequest';

/**
 * 获取自诊数据
 */
export const getAllAutognosisData = () => {
  return uniRequest({
    url: '/api/selfdiagonsis/selfdiagonsissymptom/listtree',
    method: 'get',
  });
};

export const getSymptomData = (catId) => {
  return uniRequest({
    url: `/api/selfdiagonsis/selfdiagonsissymptom/info/${catId}`,
    method: 'get',
  });
};
