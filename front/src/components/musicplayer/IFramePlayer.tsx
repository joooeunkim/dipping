/* eslint-disable no-console */
import { VisuallyHidden } from '@chakra-ui/react';
import { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import {
  setProgress,
  setPlayState,
  setPlayerInited,
  PlayerState,
  setPlayListIndex,
  nextPlayListIndex,
} from '../../reducers/iframeReducer';

export const IFramePlayer = () => {
  console.log('IFramePlayer');
  const dispatch = useDispatch();
  const playlistindex = useSelector((state: any) => state.iframeReducer.playlistindex);
  const playlist = useSelector((state: any) => state.iframeReducer.playlist);
  const playerinited = useSelector((state: any) => state.iframeReducer.playerinited);

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
          videoId: 'Gc4sY98Jn9I',
          events: {
            onStateChange: (event: any) => {
              if (event.data === (window as any).YT.PlayerState.ENDED) {
                console.log('ENDED');
                dispatch(setPlayState(PlayerState.PAUSED));
                dispatch(
                  setProgress({
                    time: (window as any).player.getDuration(),
                    duration: (window as any).player.getDuration(),
                  }),
                );
                dispatch(nextPlayListIndex());
              } else if (event.data === (window as any).YT.PlayerState.PLAYING) {
                console.log('PLAYING');
                (window as any).player.setVolume(100);
                dispatch(setPlayState(PlayerState.PLAYING));
                dispatch(
                  setProgress({
                    time: (window as any).player.getCurrentTime(),
                    duration: (window as any).player.getDuration(),
                  }),
                );
              } else if (event.data === (window as any).YT.PlayerState.PAUSED) {
                console.log('PAUSED');
                dispatch(setPlayState(PlayerState.PAUSED));
              }
            },
            onReady: () => {
              console.log('player ready');
              (window as any).player.setVolume(100);
              dispatch(setPlayerInited(true));
            },
          },
        });
      });
    };
  }, []);

  useEffect(() => {
    if (!playerinited) return;
    if (playlistindex >= 0) {
      console.log('change music:' + playlist[playlistindex].id);
      console.log(playlistindex + '/' + playlist.length);
      const player = (window as any).player;
      player.loadVideoById({ videoId: playlist[playlistindex].id });
    }
  }, [playlistindex, playlist, playerinited]);

  return (
    <VisuallyHidden>
      <div id="player" />
    </VisuallyHidden>
  );
};
