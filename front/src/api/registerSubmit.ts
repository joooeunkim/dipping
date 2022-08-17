import axios from 'axios';
import { defaultAxios } from './common';
import { parseJwt } from './login/local';

export const registerSubmit = (registerState: any) => {
  defaultAxios
    .post('/signUp', {
      email: registerState.email,
      password: registerState.password,
      nickname: registerState.nickname,
      profileImgUrl: '',
      musicTaste: registerState.musicTaste,
      provider: 'dipping',
      musicGenre: registerState.musicGenre,
    })
    .then((res: any) => {
      window.location.href = '/login';
    })
    .catch((err: any) => {
      console.log(err);
    });
};

export const registerSubmitSocial = (registerState: any, token: any) => {
  const headers = {
    Authorization: 'Bearer ' + token,
  };
  defaultAxios.post(
    '/signUp/info',
    {
      email: '',
      nickname: registerState.nickname,
      profileImgUrl: '',
      musicTaste: registerState.musicTaste,
      provider: parseJwt(token).provider,
      musicGenre: registerState.musicGenre,
    },
    { headers },
  );
};
