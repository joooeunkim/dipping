import { createSlice } from '@reduxjs/toolkit';

export enum PlayerState {
  UNSTARTED = -1,
  ENDED = 0,
  PLAYING = 1,
  PAUSED = 2,
  BUFFERING = 3,
  CUED = 5,
}

const iframeReducer = createSlice({
  name: 'iframeReducer',
  initialState: {
    playstate: -1,
    progress: { time: 0, duration: 60 },
    playlist: [],
    playlistindex: -1,
    postid: -1,
    playerinited: false,
  },
  reducers: {
    setPlayState(state, { payload: playstate }) {
      return { ...state, playstate: playstate };
    },
    setProgress(state, { payload: progress }) {
      return { ...state, progress: progress };
    },
    setPlayList(state, { payload: playlist }) {
      return { ...state, playlist };
    },
    setPlayListIndex(state, { payload: playlistindex }) {
      return { ...state, playlistindex };
    },
    setPostID(state, { payload: postid }) {
      return { ...state, postid };
    },
    setPlayerInited(state, { payload: playerinited }) {
      return { ...state, playerinited };
    },
    nextPlayListIndex(state) {
      const index = state.playlistindex;
      if (index + 1 !== state.playlist.length)
        return { ...state, playlistindex: state.playlistindex + 1 };

      return { ...state, playlistindex: 0 };
    },
  },
});

export const {
  setPlayState,
  setProgress,
  setPlayList,
  setPlayListIndex,
  setPostID,
  setPlayerInited,
  nextPlayListIndex,
} = iframeReducer.actions;
export default iframeReducer.reducer;
