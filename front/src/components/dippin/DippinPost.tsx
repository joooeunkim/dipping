import { Box, useColorModeValue, Image, Avatar } from '@chakra-ui/react';
import { useState } from 'react';
import { PlayerSmall } from './PlayerSmall';

export const DippinPost = (props: any) => {
  const bgColor = useColorModeValue('white', 'gray.800');
  const borderColor = useColorModeValue('gray.200', 'gray.600');

  const { dippin, id } = props;

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
          {dippin.title}
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
          {dippin.user.name}
          <Avatar
            marginLeft="1vw"
            boxSize="32px"
            name="mocha_oca"
            src={dippin.user.profile_image}
          />
        </Box>
      </Box>

      {/* music player */}
      <PlayerSmall playlist={dippin.playlist} id={id} />

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
          {dippin.likes}
        </Box>
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
          {dippin.article}
        </Box>
      </Box>
    </Box>
  );
};
