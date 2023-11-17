import { localAxios } from '../util/http-commons';

const local = localAxios();

export const loginMember = (user, success, fail) => {
  local.post('/login', { user_id: user.user_id, password: user.password }).then(success).catch(fail);
};

export const signupMember = (user, success, fail) => {
  local.post('/user', JSON.stringify(user)).then(success).catch(fail);
};

export const getAllMemberInfo = (success, fail) => {
  local.get('/user').then(success).catch(fail);
};
