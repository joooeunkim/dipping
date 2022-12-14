import { combineReducers } from 'redux';
import tokenReducer from './Auth';
import iframeReducer from './iframeReducer';
import registerReducer from './registerReducer';
import dippinReducer from './dippinReducer';
import searchReducer from './searchReducer';
// 여러 reducer를 사용하는 경우 reducer를 하나로 묶어주는 메소드입니다.
// store에 저장되는 리듀서는 오직 1개입니다.
const rootReducer = combineReducers({
  iframeReducer,
  tokenReducer,
  registerReducer,
  dippinReducer,
  searchReducer,
});

export default rootReducer;
