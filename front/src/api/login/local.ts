import { DefaultAxios } from '../DefaultAxios';
import { SET_TOKEN } from '../../reducers/Auth';
import { useDispatch } from 'react-redux';

// 로그인 작업
export const local = (email: string, password: string, dispatch: any, navigate: any) => {
  DefaultAxios.post('/login', {
    email: email,
    password: password,
  })
    .then(res => {
      if (res.data.code == 200) {
        // console.log('parse', parseJwt(res.data.result));
        dispatch(SET_TOKEN(res.data.result));
        // eslint-disable-next-line no-restricted-globals
        navigate('/');
      } else {
        alert('로그인 실패');
      }
    })
    .catch(err => {
      alert('서버와 연결 실패');
    });
};

// jwt 디코딩하는 함수
export const parseJwt = (token: any) => {
  let base64Url = token.split('.')[1];
  let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
  let jsonPayload = decodeURIComponent(
    window
      .atob(base64)
      .split('')
      .map(function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
      })
      .join(''),
  );

  return JSON.parse(jsonPayload);
};
