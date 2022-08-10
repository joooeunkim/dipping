import { DefaultAxios } from '../DefaultAxios';

export const local = (email: string, password: string) => {
  console.log(email, password);
  DefaultAxios.post('/login', {
    email: email,
    password: password,
  })
    .then(res => {
      if (res.data.code == '200') {
        localStorage.setItem('accesstoken', res.data.data);
      } else {
        alert('로그인 실패');
      }
    })
    .catch(err => {
      alert('서버와 연결 실패');
    });
};
