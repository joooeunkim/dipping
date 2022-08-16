import { Box, Flex, Image, Spacer, useColorModeValue } from '@chakra-ui/react';
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
import { PlayerSmallItem } from './PlayerSmallItem';

export const PlayerSmall = (props: any) => {
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
      {/* progress bar */}
      {postid === id ? (
        <ProgressBar />
      ) : (
        <Box h="18px">
          <hr />
        </Box>
      )}
      {/* album art */}
      <Flex position="relative" w="full">
        <Image
          borderRadius="10px"
          boxShadow="0 0 2px gray"
          boxSize="96px"
          objectFit="cover"
          src={playlist[currentitem].albumart}
        />
        <Box w="100%" paddingX="8px">
          {/* song info */}
          <Box h="16px" fontSize="12px" fontWeight="200" lineHeight="16px" color="gray.400">
            {playlist[currentitem].artist}
          </Box>
          <Box w="full" h="40px" fontSize="18px" lineHeight="20px" overflow="hidden">
            {playlist[currentitem].title}
          </Box>
          {/* control */}
          <Flex w="full" h="40px" fontSize="28px" lineHeight="28px" alignItems="flex-end">
            <Spacer />

            {postid === id && playstate === PlayerState.PLAYING ? (
              <Box className="fa-solid fa-pause" onClick={PlayPause} />
            ) : (
              <Box className="fa-solid fa-play" onClick={PlayPause} />
            )}
            <Box
              className={albumvisible ? 'fa-light fa-album' : 'fa-solid fa-album'}
              marginLeft="12px"
              onClick={onClickAlbum}
            />
          </Flex>
        </Box>
      </Flex>

      <Box h="8px"></Box>
      {/* playlist popdown */}
      <Box w="full" display={albumvisible ? '' : 'none'}>
        {/* playlist item */}
        {playlist.map((item: any, index: number) => (
          <div key={index}>
            <PlayerSmallItem
              {...item}
              onClickBody={() => {
                onClickItem(index);
              }}
              selected={currentitem == index ? true : false}
            />
          </div>
        ))}
      </Box>
    </>
  );
};
