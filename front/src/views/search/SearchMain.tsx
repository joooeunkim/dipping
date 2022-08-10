import { SearchNavBar } from '../../components/floatingbar/SearchNavBar';
import { useEffect, useState } from 'react';
import { Box, Image, VisuallyHidden } from '@chakra-ui/react';
import { useSelector, useDispatch } from 'react-redux';
import {
  setProgress,
  setPlayState,
  setPlayerInited,
  PlayerState,
} from '../../reducers/iframeReducer';
import { ProgressBar } from '../../components/ProgressBar';

export const SearchMain = () => {
  console.log('SearchMain');
  const dispatch = useDispatch();
  const playstate = useSelector((state: any) => state.iframeReducer.playstate);

  //test
  const [_progress, setProgress] = useState(0.2);

  // useEffect(() => {
  //   console.log('useEffect test');

  //   (window as any).onYouTubeIframeAPIReady = function () {
  //     console.log('iframe checked');
  //     (window as any).YT.ready(function () {
  //       console.log('YT checked');
  //       setPlaying((window as any).player?.getPlayerState() === 1 ? true : false);
  //       editProgress();
  //     });
  //   };
  // }, []);

  // useEffect(() => {
  //   console.log('set init');
  // }, []);

  // useEffect(() => {
  //   console.log('useeffectinterval');
  //   const intervalId = setInterval(() => editProgress(), 1000);
  //   return () => clearInterval(intervalId);
  // }, []);

  const editProgress = () => {
    setProgress((window as any).player.getCurrentTime() / (window as any).player.getDuration());
  };

  return (
    <Box>
      <SearchNavBar leftDisplay="none" rightDisplay="none" />
      SearchMain
      {/* progress bar */}
      <Box position="relative" h="22px" w="full" bg="">
        <Box position="absolute" left="4vw" h="6px" w="92%" borderRadius="2px" bg="gray.400" />
        <Box
          position="absolute"
          left="4vw"
          h="6px"
          w={92 * _progress + '%'}
          borderRadius="2px"
          bgGradient="linear(to-r, blue.400, cyan.200)"
        />
      </Box>
      <Box
        id="pause"
        w="100px"
        border="1px"
        onClick={() => {
          if (playstate == PlayerState.PLAYING) {
            (window as any).player.pauseVideo();
            dispatch(setPlayState(PlayerState.PAUSED));
            editProgress();
          } else {
            (window as any).player.playVideo();
            dispatch(setPlayState(PlayerState.PLAYING));
            editProgress();
          }
        }}
      >
        {playstate == PlayerState.PLAYING ? 'Pause' : 'Play'}
      </Box>
      <Box
        w="100px"
        border="1px"
        onClick={() => {
          (window as any).player.loadVideoById('qvlGmA1J478');
        }}
      >
        Dead!
      </Box>
      <Box
        w="140px"
        border="1px"
        onClick={() => {
          (window as any).player.cuePlaylist(['r5MR7_INQwg', 'qvlGmA1J478']);
        }}
      >
        Playlist
      </Box>
      <ProgressBar />
    </Box>
  );
};
