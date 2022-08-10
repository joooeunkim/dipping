import axios, { AxiosInstance, AxiosRequestConfig } from 'axios';

const SERVER_ADDRESS = 'http://i7b210.p.ssafy.io/api';

const accessToken = localStorage.getItem('accessToken');

export const DefaultAxios: AxiosInstance = axios.create({
  baseURL: `${SERVER_ADDRESS}`,
  headers: {
    access_token: accessToken || '',
  },
});

DefaultAxios.defaults.withCredentials = true;

// 토큰 있는지 체크
// export const checkToken = async (config: AxiosRequestConfig) => {
//   if (!accessToken) {
//     alert('토큰이 만료되었습니다. 다시 로그인 해주세요');
//     // eslint-disable-next-line no-restricted-globals
//     location.href = '/login';
//   } else {
//     return config;
//   }
// };

// DefaultAxios.interceptors.request.use(checkToken);
