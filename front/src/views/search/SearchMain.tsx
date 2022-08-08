import { SearchNavBar } from '../../components/floatingbar/SearchNavBar';
import { useEffect, useState } from 'react';
import { Box, Image, VisuallyHidden } from '@chakra-ui/react';

export const SearchMain = () => {
  const props = {
    leftDisplay: 'none',
    rightDisplay: 'none',
  };

  //test
  const [playing, setPlaying] = useState(0);
  const [progress, setProgress] = useState(
    (window as any).player.getCurrentTime() / (window as any).player.getDuration(),
  );

  useEffect(() => {
    async function loadIFrame() {
      console.log('useEffect!');
      // If not, load the youtube ifram api script asynchronously
      const tag = document.createElement('script');
      tag.src = 'https://www.youtube.com/iframe_api';

      const firstScriptTag = document.getElementsByTagName('script')[0];
      firstScriptTag.parentNode?.insertBefore(tag, firstScriptTag);

      (window as any).onYouTubeIframeAPIReady = function () {
        (window as any).player = new (window as any).YT.Player('player', {
          width: 'auto',
          height: 'auto',
          playerVars: { playsinline: 1, autoplay: 1 },
          videoId: 'Gc4sY98Jn9I',
          events: {
            onStateChange: (event: any) => {
              if (event.data === (window as any).YT.PlayerState.ENDED) {
                // duration: (window as any).player.getDuration(),
                // currentTime: (window as any).player.getDuration(),
              } else if (event.data === (window as any).YT.PlayerState.PLAYING) {
                // const progress = {
                //   duration: (window as any).player.getDuration(),
                //   currentTime: (window as any).player.getCurrentTime(),
                // };
                // setProgress(progress);
              }
            },
            onReady: () => {
              console.log('iframe ready');
              (window as any).player.setVolume(100);
            },
          },
        });
      };
    }
    loadIFrame();
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
      {/* <VisuallyHidden>
        <div id="player" />
      </VisuallyHidden> */}
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
            setPlaying(0);
            editProgress();
          } else {
            (window as any).player.playVideo();
            setPlaying(1);
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
    </Box>
  );
};
