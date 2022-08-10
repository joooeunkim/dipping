/* eslint-disable no-console */
import { VisuallyHidden } from '@chakra-ui/react';
import { useEffect } from 'react';

export const IFramePlayer = () => {
  console.log('IFramePlayer');

  useEffect(() => {
    console.log('useEffect for player init');

    const tag = document.createElement('script');
    tag.src = 'https://www.youtube.com/iframe_api';
    // tag.setAttribute('onload', 'onYouTubeIframeReady()');
    const firstScriptTag = document.getElementsByTagName('script')[0];
    firstScriptTag.parentNode?.insertBefore(tag, firstScriptTag);

    (window as any).onYouTubeIframeAPIReady = function () {
      console.log('iframe ready');
      (window as any).YT.ready(function () {
        console.log('YT ready');
        (window as any).player = new (window as any).YT.Player('player', {
          width: 'auto',
          height: 'auto',
          playerVars: { playsinline: 1, autoplay: 0 },
          videoId: 'r5MR7_INQwg',
          events: {
            onStateChange: (event: any) => {
              // pass
            },
            onReady: () => {
              console.log('player ready');
              (window as any).player.setVolume(100);
            },
          },
        });
      });
    };
  }, []);

  return (
    <VisuallyHidden>
      <div id="player" />
    </VisuallyHidden>
  );
};
