import { createSlice } from '@reduxjs/toolkit';
import { Music } from '../types/HomeFeedData';

const dippinReducer = createSlice({
  name: 'dippinReducer',
  initialState: {
    customlist: new Array<Music>(),
  },
  reducers: {
    pushCustomList(state, { payload: music }) {
      return { ...state, customlist: [...state.customlist, music] };
    },
    popCustomList(state, { payload: id }) {
      const targetidx = state.customlist.findIndex(e => e.id === id);

      return { ...state, customlist: [...state.customlist.splice(targetidx, 1)] };
    },
  },
});

export const { pushCustomList, popCustomList } = dippinReducer.actions;
export default dippinReducer.reducer;
