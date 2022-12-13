import uniRequest from '@/utils/uniRequest';

/**
 * 获取帖子列表
 */
 export const listQuestion = async (data) => {
  return uniRequest({
    url: '/api/community/question/listByEs',
    method: 'get',
    data,
  });
};

/**
 * 获取最新消息
 */
 export const listMessage = async (data) => {
  return uniRequest({
    url: '/api/ums/message/list',
    method: 'get',
    data,
  });
};

/**
 * 获取一级评论列表
 * ?page=1&limit=7
 * 
 */
 export const listComment = async (data) => {
  return uniRequest({
    url: '/api/community/comment/listByRedis',
    method: 'get',
    data,
  });
};

/**
 * 获取二级评论列表
 * ?page=1&limit=7
 * 
 */
 export const listSecComment = async (data) => {
  return uniRequest({
    url: '/api/community/comment/listSecComment',
    method: 'get',
    data,
  });
};

/**
 * 点赞帖子
 */
export const questionThumb = async (data) => {
  return uniRequest({
    url: '/api/community/thumb/save',
    method: 'post',
    data,
  });
};

/**
 * 取消点赞帖子
 */
 export const questionUnThumb = async (data) => {
  return uniRequest({
    url: '/api/community/thumb/cancel',
    method: 'post',
    data,
  });
};

/**
 * 评论帖子
 */
 export const saveComment = async (data) => {
  return uniRequest({
    url: '/api/community/comment/save',
    method: 'post',
    data,
  });
};

/**
 * 删除评论
 */
 export const deleteComment = async (data) => {
  return uniRequest({
    url: '/api/community/comment/deleteOne',
    method: 'post',
    data,
  });
};

/**
 * 获取热搜
 * 
 */
 export const searchHotWrd = async () => {
  return uniRequest({
    url: '/api/search/search/hot_word',
    method: 'get'
  });
};