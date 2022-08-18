import { Box, useColorModeValue, Image, Avatar, Center, Flex, Spacer } from '@chakra-ui/react';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import { authAxios } from '../../api/common';
import { PlayerSmall } from './PlayerSmall';

export const DippinPostSmall = (props: any) => {
  const bgColor = useColorModeValue('white', 'gray.800');
  const borderColor = useColorModeValue('gray.200', 'gray.600');

  const { dippin, id } = props;
  const [mylike, setMyLike] = useState(dippin.myLike);
  const [likecount, setLikeCount] = useState(dippin.likes);

  const toggleLike = async () => {
    setLikeCount((likes: number) => (mylike ? likes - 1 : likes + 1));
    setMyLike((mylike: boolean) => !mylike);

    authAxios
      .post('/dipping/like', {
        dippingLike: {
          dippingId: dippin.id,
        },
      })
      .then(res => console.log(res))
      .catch(err => console.log(err));
  };

  return (
    <Box position="relative" w="full" paddingX="24px" borderBottom="1px" borderColor={borderColor}>
      {/* top bar */}
      <Flex position="relative" h="36px" w="auto" marginTop="8px">
        <Center
          h="36px"
          w="full"
          lineHeight="16px"
          fontSize="16px"
          fontWeight="600"
          overflow="hidden"
        >
          <Box w="full">{dippin.title}</Box>
        </Center>
        <Box
          marginX="4px"
          h="36px"
          w="auto"
          lineHeight="36px"
          fontSize="12px"
          fontWeight="300"
          whiteSpace="nowrap"
        >
          {dippin.user.name}
        </Box>
        <Link to={'/profile/' + dippin.user.name}>
          <Avatar boxSize="36px" name="mocha_oca" src={dippin.user.profile_image} />
        </Link>
      </Flex>

      {/* article set */}
      <Box position="relative" marginY="8px" fontWeight="400" fontSize="14px">
        {dippin.article}
      </Box>

      {/* icon set */}
      <Flex position="relative" h="24px" w="full" bg="" marginY="8px" fontSize="24px">
        {mylike ? (
          <Box
            className="fa-solid fa-heart"
            fontSize="24px"
            color="cyan.400"
            onClick={toggleLike}
          />
        ) : (
          <Box className="fa-regular fa-heart" fontSize="24px" onClick={toggleLike} />
        )}
        <Box lineHeight="24px" marginLeft="8px">
          {likecount}
        </Box>
        <Spacer />
        <Box fontSize="20px" className="fa-light fa-eraser" marginLeft="8px" />
      </Flex>

      {/* music player */}
      {dippin.playlist.length > 0 && <PlayerSmall playlist={dippin.playlist} id={id} />}

      <Box h="16px" />
    </Box>
  );
};
