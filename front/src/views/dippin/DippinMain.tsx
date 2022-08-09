import { Box } from '@chakra-ui/react';
import { SearchNavBar } from '../../components/floatingbar/SearchNavBar';
// import { useEffect, useState } from 'react';
// import { useSelector, useDispatch } from 'react-redux';
// import { increseCount, decreaseCount } from '../../reducers/counter';

export const DippinMain = () => {
  // const dispatch = useDispatch();
  // const { count } = useSelector((state: any) => state.counter);
  // const increase = () => {
  //   // store에 있는 state 바꾸는 함수 실행
  //   dispatch(increseCount(count + 1));
  // };
  // const decrease = () => {
  //   // store에 있는 state 바꾸는 함수 실행
  //   dispatch(decreaseCount(count - 1));
  // };

  const props = {
    leftDisplay: 'none',
    rightDisplay: 'none',
  };

  // const [player, setPlayer] = useState();

  // useEffect(() => {
  //   const tag = document.createElement('script');
  //   tag.src = 'https://www.youtube.com/iframe_api';
  //   tag.setAttribute('onload', 'onYouTubeIframeReady()');
  //   const firstScriptTag = document.getElementsByTagName('script')[0];
  //   firstScriptTag.parentNode?.insertBefore(tag, firstScriptTag);

  //   (window as any).onYouTubeIframeReady = function () {
  //     setPlayer(
  //       new (window as any).YT.Player('player2', {
  //         videoId: 'Gc4sY98Jn9I',
  //         autoplay: false,

  //         events: {
  //           onReady: onPlayerReady,
  //         },
  //       }),
  //     );
  //   };

  //   function onPlayerReady(event: any) {
  //     event.target.playVideo();
  //   }
  // }, []);

  return (
    <Box>
      <SearchNavBar {...props} />
      DippinMain
      {/* <div id="player2" /> */}
      {/* <div>
        {count}
        <Box onClick={increase}>증가</Box>
        <Box onClick={decrease}>감소</Box>
      </div> */}
    </Box>
  );
};
