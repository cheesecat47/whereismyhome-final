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
