import { SearchNavBar } from '../../components/floatingbar/SearchNavBar';
import { useEffect, useState } from 'react';
import { Box, Image, VisuallyHidden } from '@chakra-ui/react';

export const SearchMain = () => {
  const props = {
    leftDisplay: 'none',
    rightDisplay: 'none',
  };
  console.log('SearchMain');

  //test
  const [playing, setPlaying] = useState(false);
  const [progress, setProgress] = useState(0.2);

  useEffect(() => {
    console.log('useEffect test');

    (window as any).onYouTubeIframeAPIReady = function () {
      console.log('iframe checked');
      (window as any).YT.ready(function () {
        console.log('YT checked');
        setPlaying((window as any).player?.getPlayerState() === 1 ? true : false);
        editProgress();
      });
    };
  }, []);

  useEffect(() => {
    console.log('set init');
  }, []);

  useEffect(() => {
    console.log('useeffectinterval');
    const intervalId = setInterval(() => editProgress(), 1000);
    return () => clearInterval(intervalId);
  }, []);

  const editProgress = () => {
    setProgress((window as any).player.getCurrentTime() / (window as any).player.getDuration());
  };

  return (
    <Box>
      <SearchNavBar {...props} />
      SearchMain
      {/* progress bar */}
      <Box position="relative" h="22px" w="full" bg="">
        <Box position="absolute" left="4vw" h="6px" w="92%" borderRadius="2px" bg="gray.400" />
        <Box
          position="absolute"
          left="4vw"
          h="6px"
          w={92 * progress + '%'}
          borderRadius="2px"
          bgGradient="linear(to-r, blue.400, cyan.200)"
        />
      </Box>
      <Box
        id="pause"
        w="100px"
        border="1px"
        onClick={() => {
          if (playing) {
            (window as any).player.pauseVideo();
            setPlaying(false);
            editProgress();
          } else {
            (window as any).player.playVideo();
            setPlaying(true);
            editProgress();
          }
        }}
      >
        {playing ? 'Pause' : 'Play'}
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
    </Box>
  );
};
