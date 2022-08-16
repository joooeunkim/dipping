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
      const list = state.customlist.slice();
      list.splice(targetidx, 1);
      return { ...state, customlist: [...list] };
    },
    setCustomList(state, { payload: list }) {
      return { ...state, customlist: [...list] };
    },
  },
});

export const { pushCustomList, popCustomList, setCustomList } = dippinReducer.actions;
export default dippinReducer.reducer;
