/* eslint-disable no-console */
import { Box, useColorModeValue } from '@chakra-ui/react';
import { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { setProgress, setPlayState, setPlayerInited, PlayerState } from '../reducers/iframeReducer';

export const ProgressBar = () => {
  const dispatch = useDispatch();
  const progress = useSelector((state: any) => state.iframeReducer.progress);
  const borderColor = useColorModeValue('gray.200', 'gray.600');

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

  return (
    <Box position="relative" h="22px" w="92%" bg="">
      <Box position="absolute" left="4vw" h="6px" w="full" borderRadius="2px" bg={borderColor} />
      <Box
        position="absolute"
        left="4vw"
        h="6px"
        w={(progress.time / progress.duration) * 100 + '%'}
        borderRadius="2px"
        bgGradient="linear(to-r, blue.400, cyan.200)"
      />
    </Box>
  );
};
