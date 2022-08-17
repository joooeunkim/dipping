import { createSlice } from '@reduxjs/toolkit';
import { Music } from '../types/HomeFeedData';

const dippinReducer = createSlice({
  name: 'dippinReducer',
  initialState: {
    input: '',
    mode: 'recent',
    page: 0,
    dippinlist: new Array<Music>(),
    customlist: new Array<Music>(),
  },
  reducers: {
    setInput(state, { payload: input }) {
      return { ...state, input: input };
    },
    setMode(state, { payload: mode }) {
      return { ...state, mode: mode };
    },
    setPage(state, { payload: page }) {
      return { ...state, page: page };
    },
    addPage(state) {
      return { ...state, page: state.page + 1 };
    },
    pushDippinList(state, { payload: newpage }) {
      return { ...state, dippinlist: [...state.dippinlist, ...newpage] };
    },
    setDippinList(state, { payload: newlist }) {
      return { ...state, dippinlist: [...newlist] };
    },
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

export const {
  setInput,
  setMode,
  setPage,
  addPage,
  pushDippinList,
  setDippinList,
  pushCustomList,
  popCustomList,
  setCustomList,
} = dippinReducer.actions;
export default dippinReducer.reducer;
