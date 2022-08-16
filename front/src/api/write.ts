import { authAxios } from './common';

export const writePostFeed = (data: any) => {
  authAxios.post('/board', data);
};

export const writeDipping = (data: any) => {
  authAxios.post('/dipping', data);
};
