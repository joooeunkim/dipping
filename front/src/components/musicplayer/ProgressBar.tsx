/* eslint-disable no-console */
import { Box, useColorModeValue } from '@chakra-ui/react';
import React, { useEffect, useRef, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import {
  setProgress,
  setPlayState,
  setPlayerInited,
  PlayerState,
} from '../../reducers/iframeReducer';

export const ProgressBar = () => {
  const dispatch = useDispatch();
  const progress = useSelector((state: any) => state.iframeReducer.progress);
  const borderColor = useColorModeValue('gray.200', 'gray.600');

  const progressClickHandler = (event: React.MouseEvent) => {
    const width = (ref as any).current.offsetWidth;
    console.log(event.nativeEvent.offsetX);
    const time = (event.nativeEvent.offsetX / width) * progress.duration;
    (window as any).player?.seekTo(time);
  };
  useEffect(() => {
    const mytimer = setInterval(function () {
      const player = (window as any).player;
      if (
        player?.getPlayerState() === PlayerState.PLAYING ||
        player?.getPlayerState() === PlayerState.PAUSED
      ) {
        dispatch(setProgress({ time: player.getCurrentTime(), duration: player.getDuration() }));
      }
    }, 1000);
    return () => {
      clearInterval(mytimer);
    };
  }, []);

  // 너비 가져오기
  const ref = useRef(null);
  // const [width, setWidth] = useState(0);

  // useEffect(() => {
  //   setWidth((ref as any).current.offsetWidth);
  // }, []);

  return (
    <Box position="relative" h="18px" w="100%" bg="" ref={ref}>
      <Box position="absolute" h="6px" w="full" borderRadius="2px" bg={borderColor} />
      <Box
        position="absolute"
        h="6px"
        w={(progress.time / progress.duration) * 100 + '%'}
        borderRadius="2px"
        bgGradient="linear(to-r, blue.400, cyan.200)"
      />
      <Box position="absolute" h="6px" w="full" bg="" onClick={progressClickHandler} />
    </Box>
  );
};
