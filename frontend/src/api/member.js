import { localAxios } from '../util/http-commons';

const local = localAxios();

export const loginMember = async (user, success, fail) => {
  await local.post('/member/login', { email: user.email, password: user.password }).then(success).catch(fail);
};

export const signupMember = async (user, success, fail) => {
  await local.post('/member/', JSON.stringify(user)).then(success).catch(fail);
};

// export const getAllMemberInfo = async (success, fail) => {
//   await local.get('/user').then(success).catch(fail);
// };
