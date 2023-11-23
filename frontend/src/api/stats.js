import { localAxios } from '../util/http-commons';

const local = localAxios();

export const getFavouriteAptTop5 = (success, fail) => {
  local.get('/stats/favourite/apartment').then(success).catch(fail);
};

export const getFavouriteDongTop5 = (success, fail) => {
  local.get(`/stats/favourite/dong`).then(success).catch(fail);
};

export const getAptDealAmountAvg = (aptCode, success, fail) => {
  local.get(`/stats/deal-amount-avg/${aptCode}`).then(success).catch(fail);
};
