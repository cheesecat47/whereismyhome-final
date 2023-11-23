import { localAxios } from '../util/http-commons';

const local = localAxios();

export const getNotices = (success, fail) => {
  local.get('/board/notice').then(success).catch(fail);
};

export const getCommunityArticles = (dongCode, searchBy, searchKeyword, orderBy, success, fail) => {
  local
    .get(`board/community/${dongCode}`, {
      params: {
        searchBy: searchBy,
        searchKeyword: searchKeyword,
        orderBy: orderBy,
      },
    })
    .then(success)
    .catch(fail);
};

export const getArticleById = (boardId, success, fail) => {
  local.get(`/board/${boardId}`).then(success).catch(fail);
};

export const writeArticle = async (board, success, fail) => {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken');
  await local.post('/board/', JSON.stringify(board)).then(success).catch(fail);
};
