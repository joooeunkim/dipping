import { Box, useColorModeValue, Image, Avatar, Center, Flex, Spacer } from '@chakra-ui/react';
import { useState } from 'react';
import { PlayerSmall } from './PlayerSmall';

export const DippinPost = (props: any) => {
  const bgColor = useColorModeValue('white', 'gray.800');
  const borderColor = useColorModeValue('gray.200', 'gray.600');

  const { dippin, id } = props;

  return (
    <Box position="relative" w="full" paddingX="24px" borderBottom="1px" borderColor={borderColor}>
      {/* top bar */}
      <Flex position="relative" h="40px" w="auto" marginTop="16px">
        <Center
          h="40px"
          w="full"
          lineHeight="20px"
          fontSize="18px"
          fontWeight="600"
          overflow="hidden"
        >
          <Box w="full">{dippin.title}</Box>
        </Center>
        <Box h="40px" w="auto" lineHeight="40px" fontSize="14px" fontWeight="300">
          {dippin.user.name}
        </Box>
        &nbsp;
        <Avatar boxSize="40px" name="mocha_oca" src={dippin.user.profile_image} />
      </Flex>

      {/* article set */}
      <Box position="relative" marginY="8px" fontWeight="400" fontSize="14px">
        {dippin.article}
      </Box>

      {/* icon set */}
      <Flex position="relative" h="24px" w="full" bg="" marginY="8px" fontSize="24px">
        <Box className="fa-solid fa-heart" fontSize="24px" color="cyan.400" />
        <Box lineHeight="24px" marginLeft="8px">
          {dippin.likes}
        </Box>
        <Spacer />
        <Box className="fa-light fa-eraser" marginLeft="8px" />
        <Box className="fa-light fa-pencil" marginLeft="8px" />
        <Box className="fa-light fa-comment-plus" marginLeft="8px" />
        <Box className="fa-light fa-share-nodes" marginLeft="8px" />
      </Flex>

      {/* music player */}
      <PlayerSmall playlist={dippin.playlist} id={id} />
    </Box>
  );
};
