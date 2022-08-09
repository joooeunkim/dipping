import { createSlice } from '@reduxjs/toolkit';

const iframeReducer = createSlice({
  name: 'iframeReducer',
  initialState: {
    playstate: -1,
    progress: { time: 0, duration: 60 },
    music: '',
    playlist: [],
    playlistindex: -1,
    playerinited: false,
  },
  reducers: {
    setProgress(state, { payload: progress }) {
      return { ...state, progress: progress };
    },
    setPlayState(state, { payload: playstate }) {
      return { ...state, playstate: playstate };
    },
  },
});

export const { setProgress, setPlayState } = iframeReducer.actions;
export default iframeReducer.reducer;
