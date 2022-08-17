import { Box, useColorModeValue, Image, Avatar } from '@chakra-ui/react';
import { useState } from 'react';
import { PlayerLarge } from './PlayerLarge';
import { PostComment } from './PostComment';

export const PlaylistPost = (props: any) => {
  const bgColor = useColorModeValue('white', 'gray.800');
  const borderColor = useColorModeValue('gray.200', 'gray.600');

  const { postfeed, id } = props;

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
      <Box marginY="4px" position="relative" h="44px" w="full" bg="">
        <Box
          position="absolute"
          left="4vw"
          top="6px"
          fontSize="16px"
          fontWeight="300"
          lineHeight="32px"
          bg=""
        >
          <Avatar
            marginRight="1vw"
            boxSize="32px"
            name="mocha_oca"
            src={postfeed.user.profile_image}
          />
          {postfeed.user.name}
        </Box>
      </Box>

      {/* music player */}
      {/* <PlayerLarge /> */}
      <PlayerLarge playlist={postfeed.playlist} id={id} />

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
        {/* <PostComment
          user={postfeed.user}
          article={postfeed.article}
          id={postfeed.id}
          last_modified={postfeed.last_modified}
        /> */}
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
    </Box>
  );
};
