import { createSlice } from '@reduxjs/toolkit';
import { FeedPost, Music, User } from '../types/HomeFeedData';

const searchReducer = createSlice({
  name: 'searchReducer',
  initialState: {
    input: '',
    mode: 'user',
    page: 0,
    userlist: new Array<User>(),
    postlist: new Array<FeedPost>(),
    dippinlist: new Array<FeedPost>(),
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
    pushUserList(state, { payload: newpage }) {
      return { ...state, userlist: [...state.userlist, ...newpage] };
    },
    setUserList(state, { payload: newlist }) {
      return { ...state, userlist: [...newlist] };
    },
    pushDippinList(state, { payload: newpage }) {
      return { ...state, dippinlist: [...state.dippinlist, ...newpage] };
    },
    setDippinList(state, { payload: newlist }) {
      return { ...state, dippinlist: [...newlist] };
    },
    pushPostList(state, { payload: newpage }) {
      return { ...state, postlist: [...state.postlist, ...newpage] };
    },
    setPostList(state, { payload: newlist }) {
      return { ...state, postlist: [...newlist] };
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
  pushUserList,
  pushPostList,
  setPostList,
} = searchReducer.actions;
export default searchReducer.reducer;
