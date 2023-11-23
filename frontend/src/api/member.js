import { localAxios } from '../util/http-commons';

const local = localAxios();

export const loginMember = async (user, success, fail) => {
  await local.post('/member/login', { email: user.email, password: user.password }).then(success).catch(fail);
};

export const signupMember = async (user, success, fail) => {
  await local.post('/member/', JSON.stringify(user)).then(success).catch(fail);
};

export const getMemberInfo = (memberId, success, fail) => {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken');
  local.get(`/member/${memberId}`).then(success).catch(fail);
};

export const editMemberInfo = (memberId, user, success, fail) => {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken');
  local.put(`/member/${memberId}`, JSON.stringify(user)).then(success).catch(fail);
};
