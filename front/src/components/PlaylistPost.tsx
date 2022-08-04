import { Box, useColorModeValue, Image, Avatar } from '@chakra-ui/react';
import { useState } from 'react';
import { PlayerLarge } from './PlayerLarge';
import { PlayerLarge2 } from './PlayerLarge2';

const postfeed = {
  title: 'Track_Emocore',
  user: {
    name: 'mocha_oca',
    profile_image: 'https://bit.ly/3A2BqqJ',
  },
  likes: '10.2k',
  playlists: [
    {
      title: 'Welcome To The Black Parade',
      artist: 'My Chemical Romance',
      albumart: 'https://bit.ly/3PXNy1o',
    },
    {
      title: 'LA Devotee',
      artist: 'Panic! At The Disco',
      albumart: 'https://bit.ly/3QdDcu6',
    },
    {
      title: '백색왜성',
      artist: '넬',
      albumart: 'https://bit.ly/3bwSzPF',
    },
    {
      title: 'Stressed Out',
      artist: '​twenty one pilots',
      albumart: 'https://bit.ly/3PcIrtn',
    },
    {
      title: 'Dead!',
      artist: 'My Chemical Romance',
      albumart: 'https://bit.ly/3PXNy1o',
    },
  ],
  article:
    'The Black Parade is the third studio album by American rock band My Chemical Romance. ' +
    'Released in Europe on October 20, 2006, through Reprise Records, it was produced by the band with ' +
    'Rob Cavallo, known for having produced multiple albums for the Goo Goo Dolls and Green Day. ' +
    "It is a rock opera centering on a dying character with cancer known as 'The Patient'. " +
    'The album tells the story of his apparent death, experiences in the afterlife, and subsequent ' +
    "reflections on his life. It is the band's only studio album to feature drummer Bob Bryar before his departure in 2010.",
  tags: '#MCR #emocore #black_parade',
};

export const PlaylistPost = (props: any) => {
  const bgColor = useColorModeValue('white', 'gray.800');
  const borderColor = useColorModeValue('gray.200', 'gray.600');

  // const { user, likes, playlists, article, tags } = props;

  // 본문 더보기
  const [limit, setLimit] = useState(95);
  const toggleEllipsis = (str: string, limit: number) => {
    return {
      string: str.slice(0, limit),
      isShowMore: str.length > limit,
    };
  };
  const onClickMore = (str: string) => () => {
    setLimit(str.length);
  };

  return (
    <Box position="relative" h="" w="full" borderBottom="1px" borderColor={borderColor} bg="">
      {/* top bar */}
      <Box position="relative" h="44px" w="full" bg="">
        <Box
          position="absolute"
          left="4vw"
          top="10px"
          fontSize="20px"
          fontWeight="600"
          lineHeight="24px"
          bg=""
        >
          {postfeed.title}
        </Box>
        <Box
          position="absolute"
          right="4vw"
          top="6px"
          fontSize="16px"
          fontWeight="300"
          lineHeight="32px"
          bg=""
        >
          {postfeed.user.name}
          <Avatar
            marginLeft="1vw"
            boxSize="32px"
            name="mocha_oca"
            src={postfeed.user.profile_image}
          />
        </Box>
      </Box>

      {/* music player */}
      {/* <PlayerLarge /> */}
      <PlayerLarge2 />

      {/* icon set */}
      <Box position="relative" h="30px" w="full" bg="" marginBottom="16px">
        <Box
          position="absolute"
          left="4vw"
          className="fa-solid fa-heart"
          fontSize="24px"
          lineHeight="30px"
          color="cyan.400"
        />
        <Box position="absolute" left="12vw" fontSize="24px" lineHeight="30px">
          {postfeed.likes}
        </Box>
        <Box
          position="absolute"
          right="20vw"
          className="fa-regular fa-comment"
          fontSize="24px"
          lineHeight="30px"
        />
        <Box
          position="absolute"
          right="12vw"
          className="fa-regular fa-share-nodes"
          fontSize="24px"
          lineHeight="30px"
        />
        <Box
          position="absolute"
          right="4vw"
          className="fa-solid fa-bookmark"
          fontSize="24px"
          lineHeight="30px"
          color="cyan.400"
        />
      </Box>

      {/* article set */}
      <Box position="relative" h="" w="full" bg="" marginBottom="16px">
        <Box position="relative" marginX="4vw" fontWeight="400" fontSize="14px" lineHeight="18px">
          {toggleEllipsis(postfeed.article, limit).string}
          {toggleEllipsis(postfeed.article, limit).isShowMore && '...'}
          {toggleEllipsis(postfeed.article, limit).isShowMore && (
            <Box
              position="absolute"
              bottom="0"
              right="0"
              color="gray.400"
              onClick={onClickMore(postfeed.article)}
            >
              더보기
            </Box>
          )}
        </Box>
        <Box
          position="relative"
          marginX="4vw"
          marginY="8px"
          fontWeight="400"
          fontSize="14px"
          lineHeight="18px"
          color="gray.500"
        >
          {postfeed.tags}
        </Box>
      </Box>
    </Box>
  );
};
