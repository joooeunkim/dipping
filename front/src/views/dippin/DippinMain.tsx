import { Box, Image } from '@chakra-ui/react';
import { SearchNavBar } from '../../components/floatingbar/SearchNavBar';
import { useEffect, useState } from 'react';

export const DippinMain = () => {
  const props = {
    leftDisplay: 'none',
    rightDisplay: 'none',
  };

  const [player, setPlayer] = useState();

  useEffect(() => {
    const tag = document.createElement('script');
    tag.src = 'https://www.youtube.com/iframe_api';
    tag.setAttribute('onload', 'onYouTubeIframeReady()');
    const firstScriptTag = document.getElementsByTagName('script')[0];
    firstScriptTag.parentNode?.insertBefore(tag, firstScriptTag);

    (window as any).onYouTubeIframeReady = function () {
      setPlayer(
        new (window as any).YT.Player('player', {
          videoId: 'Gc4sY98Jn9I',
          autoplay: true,

          events: {
            onReady: onPlayerReady,
          },
        }),
      );
    };

    function onPlayerReady(event: any) {
      event.target.playVideo();
    }
  }, []);

  return (
    <Box>
      <SearchNavBar {...props} />
      DippinMain
      <div id="player" />
      <object>
        <param name="movie" value="http://www.youtube.com/v/Gc4sY98Jn9I" />
        <embed src="http://www.youtube.com/v/Gc4sY98Jn9I" type="application/x-shockwave-flash" />
      </object>
    </Box>
  );
};
