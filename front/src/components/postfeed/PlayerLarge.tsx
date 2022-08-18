import { Box, Image, useColorModeValue } from '@chakra-ui/react';
import { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import {
  PlayerState,
  setPlayList,
  setPlayListIndex,
  setPlayState,
  setPostID,
} from '../../reducers/iframeReducer';
import { ProgressBar } from '../musicplayer/ProgressBar';
import { PlayerLargeItem } from './PlayerLargeItem';

export const PlayerLarge = (props: any) => {
  const { playlist, id } = props;
  const dispatch = useDispatch();
  const playstate = useSelector((state: any) => state.iframeReducer.playstate);
  const playlistindex = useSelector((state: any) => state.iframeReducer.playlistindex);
  const postid = useSelector((state: any) => state.iframeReducer.postid);

  // 앨범 목록 표시
  const [albumvisible, toggleAlbumVisible] = useState(0);
  const onClickAlbum = () => {
    toggleAlbumVisible(albumvisible ^ 1);
  };

  // 첫 재생시 store에 포스트 정보를 넘김
  const setIFrameState = (index: number) => {
    dispatch(setPostID(id));
    dispatch(setPlayList(playlist));
    dispatch(setPlayListIndex(index));
    setCurrentItem(index);
  };

  // 곡 선택
  const [currentitem, setCurrentItem] = useState(0);
  const onClickItem = (index: number) => {
    setIFrameState(index);
  };

  // 재생-정지
  const PlayPause = () => {
    console.log('playpause');
    if (postid !== id) {
      onClickItem(0);
    } else {
      if (playstate == PlayerState.PLAYING) {
        (window as any).player.pauseVideo();
      } else {
        (window as any).player.playVideo();
      }
    }
  };

  useEffect(() => {
    if (postid === id) setCurrentItem(playlistindex);
  }, [playlistindex]);

  return (
    <>
      {/* album art */}
      <Box position="relative" boxSize="92vw" marginX="4vw" borderRadius="20px" bg="">
        <Image
          borderRadius="20px"
          boxShadow="0 0 2px gray"
          boxSize="92vw"
          objectFit="cover"
          src={playlist[currentitem].albumart}
        />

        {/* playlist popover */}
        <Box>
          <Box
            borderRadius="20px"
            position="absolute"
            w="full"
            h="92vw"
            top="0px"
            bg="whiteAlpha.400"
            backdropFilter="auto"
            backdropBlur="10px"
            display={albumvisible ? '' : 'none'}
            overflow="hidden"
          >
            {/* playlist item */}
            <Box
              marginX="2vw"
              marginY="4vw"
              w="88vw"
              h="84vw"
              overflow="hidden"
              position="relative"
            >
              <Box w="90vw" h="100%" overflow="auto">
                {playlist.map((item: any, index: number) => (
                  <div
                    key={index}
                    onClick={() => {
                      onClickItem(index);
                    }}
                  >
                    <PlayerLargeItem {...item} selected={currentitem == index ? true : false} />
                    {index != playlist.length - 1 && <Box position="relative" w="full" h="3vw" />}
                  </div>
                ))}
              </Box>
            </Box>
          </Box>
        </Box>
      </Box>

      {/* song info */}
      <Box position="relative" h="48px" w="full" bg="" textAlign="center" marginY="8px">
        <Box
          position="relative"
          left="10%"
          top="8px"
          fontSize="12px"
          fontWeight="400"
          lineHeight="14px"
          bg=""
          w="80%"
          h="14px"
          overflow="hidden"
          color="gray.400"
        >
          {playlist[currentitem].artist}
        </Box>
        <Box
          position="relative"
          left="10%"
          top="4px"
          fontSize="16px"
          fontWeight="500"
          lineHeight="24px"
          w="80%"
          h="24px"
          overflow="hidden"
        >
          {playlist[currentitem].title}
        </Box>

        <Box
          position="absolute"
          left="4vw"
          top="8px"
          className={albumvisible ? 'fa-light fa-album' : 'fa-solid fa-album'}
          fontSize="32px"
          lineHeight="32px"
          onClick={onClickAlbum}
        />

        {/* play&pause */}
        {postid === id && playstate === PlayerState.PLAYING ? (
          <Box
            position="absolute"
            right="4vw"
            top="8px"
            className="fa-solid fa-pause"
            fontSize="30px"
            lineHeight="30px"
            onClick={PlayPause}
          />
        ) : (
          <Box
            position="absolute"
            right="4vw"
            top="8px"
            className="fa-solid fa-play"
            fontSize="28px"
            lineHeight="30px"
            onClick={PlayPause}
          />
        )}
      </Box>

      {/* progress bar */}
      {postid === id ? (
        <Box position="relative" left="4%" h="22px" w="92%" bg="">
          <ProgressBar />
        </Box>
      ) : (
        <Box position="relative" left="4%" h="22px" w="92%" bg="">
          <hr />
        </Box>
      )}
    </>
  );
};
