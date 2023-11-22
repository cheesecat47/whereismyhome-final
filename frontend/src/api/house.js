import { localAxios } from '../util/http-commons';

const local = localAxios();

// export const loginMember = (user, success, fail) => {
//   local.post('/login', { user_id: user.user_id, password: user.password }).then(success).catch(fail);
// };

export const getSidoNames = (success, fail) => {
  local.get('/house/location-name').then(success).catch(fail);
};

export const getGugunNames = (sido, success, fail) => {
  local.get(`/house/location-name/${sido}`).then(success).catch(fail);
};

export const getDongNames = (sido, gugun, success, fail) => {
  local.get(`/house/location-name/${sido}/${gugun}`).then(success).catch(fail);
};

export const getDongCodeByDongName = (sido, gugun, dong, success, fail) => {
  local
    .get(`/house/dongCode`, {
      params: {
        sidoName: sido,
        gugunName: gugun,
        dongName: dong,
      },
    })
    .then(success)
    .catch(fail);
};

export const getHouseInfoByDongCode = (dongCode, success, fail) => {
  local
    .get(`/house/houseinfo`, {
      params: {
        dongCode: dongCode,
      },
    })
    .then(success)
    .catch(fail);
};

export const getHouseDealsByAptCodeYearMonth = (aptCode, success, fail) => {
  local
    .get(`/house/housedeal`, {
      params: {
        aptCode: aptCode,
      },
    })
    .then(success)
    .catch(fail);
};
