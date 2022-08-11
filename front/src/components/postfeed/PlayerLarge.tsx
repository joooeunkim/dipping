import { Box, Image, useColorModeValue } from '@chakra-ui/react';
import { useState } from 'react';
import { PlaylistItem } from './PlaylistItem';

export const PlayerLarge = (props: any) => {
  const borderColor = useColorModeValue('gray.200', 'gray.600');

  const { playlists } = props;

  // 앨범 목록 표시
  const [albumvisible, toggleAlbumVisible] = useState(0);
  const onClickAlbum = () => {
    toggleAlbumVisible(albumvisible ^ 1);
  };

  // 재생-정지
  const [playing, togglePlaying] = useState(0);
  const onClickPlaying = () => {
    togglePlaying(playing ^ 1);
  };

  // 곡 선택
  const [currentitem, setCurrentItem] = useState(0);
  const onClickItem = (index: number) => () => {
    setCurrentItem(index);
  };

  return (
    <>
      {/* album art */}
      <Box position="relative" boxSize="92vw" marginX="4vw" borderRadius="20px" bg="">
        <Image
          borderRadius="20px"
          boxShadow="0 0 2px gray"
          boxSize="92vw"
          src={playlists[currentitem].albumart}
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
                {playlists.map((item: any, index: number) => (
                  <div key={index} onClick={onClickItem(index)}>
                    <PlaylistItem {...item} selected={currentitem == index ? true : false} />
                    {index != playlists.length - 1 && <Box position="relative" w="full" h="3vw" />}
                  </div>
                ))}
              </Box>
            </Box>
          </Box>
        </Box>
      </Box>

      {/* song info */}
      <Box position="relative" h="48px" w="full" bg="" textAlign="center" marginY="4px">
        <Box
          position="relative"
          left="0vw"
          top="8px"
          fontSize="12px"
          fontWeight="400"
          lineHeight="14px"
          bg=""
          color="gray.400"
        >
          {playlists[currentitem].artist}
        </Box>
        <Box
          position="relative"
          left="0vw"
          top="4px"
          fontSize="18px"
          fontWeight="500"
          lineHeight="24px"
          bg=""
        >
          {playlists[currentitem].title}
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

        <Box
          position="absolute"
          right="4vw"
          top="8px"
          className={playing ? 'fa-solid fa-pause' : 'fa-solid fa-play'}
          fontSize={playing ? '30px' : '28px'}
          lineHeight="30px"
          onClick={onClickPlaying}
        />
      </Box>

      {/* progress bar */}
      <Box position="relative" h="22px" w="full" bg="">
        <Box position="absolute" left="4vw" h="6px" w="92vw" borderRadius="2px" bg={borderColor} />
        <Box
          position="absolute"
          left="4vw"
          h="6px"
          w="42vw"
          borderRadius="2px"
          bgGradient="linear(to-r, blue.400, cyan.200)"
        />
      </Box>

      {/* icon set */}
      <Box
        position="relative"
        h="30px"
        w="full"
        bg=""
        marginBottom="16px"
        textAlign="center"
        display="none"
      >
        <Box
          position="relative"
          right="6vw"
          className="fa-solid fa-backward-step"
          fontSize="28px"
          lineHeight="30px"
        />
        <Box
          position="relative"
          w="30px"
          className={playing ? 'fa-solid fa-pause' : 'fa-solid fa-play'}
          fontSize={playing ? '30px' : '30px'}
          lineHeight="30px"
          onClick={onClickPlaying}
        />
        <Box
          position="relative"
          left="6vw"
          className="fa-solid fa-forward-step"
          fontSize="28px"
          lineHeight="30px"
        />
      </Box>
    </>
  );
};
