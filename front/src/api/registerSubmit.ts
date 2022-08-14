import axios from 'axios';
import { defaultAxios } from './common';

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
