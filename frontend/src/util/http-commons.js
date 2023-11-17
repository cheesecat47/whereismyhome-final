import axios from 'axios';

function localAxios() {
  const instance = axios.create({
    baseURL: 'http://localhost:80/',
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
    },
  });
  return instance;
}

export { localAxios };
