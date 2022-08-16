import { Box, useColorModeValue, Image, Avatar, Center, Flex, Spacer } from '@chakra-ui/react';
import { useState } from 'react';
import { PlayerSmall } from './PlayerSmall';

export const DippinPostSmall = (props: any) => {
  const bgColor = useColorModeValue('white', 'gray.800');
  const borderColor = useColorModeValue('gray.200', 'gray.600');

  const { dippin, id } = props;

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
        <Box marginX="4px" h="36px" w="auto" lineHeight="36px" fontSize="12px" fontWeight="300">
          {dippin.user.name}
        </Box>
        <Avatar boxSize="36px" name="mocha_oca" src={dippin.user.profile_image} />
      </Flex>

      {/* article set */}
      <Box position="relative" marginY="8px" fontWeight="400" fontSize="14px">
        {dippin.article}
      </Box>

      {/* icon set */}
      <Flex position="relative" h="24px" w="full" bg="" marginY="8px" fontSize="24px">
        <Box className="fa-solid fa-heart" color="cyan.400" />
        <Box lineHeight="20px" fontSize="20px" marginLeft="8px">
          {dippin.likes}
        </Box>
        <Spacer />
        <Box fontSize="20px" className="fa-light fa-eraser" marginLeft="8px" />
        <Box fontSize="20px" className="fa-light fa-pencil" marginLeft="8px" />
      </Flex>

      {/* music player */}
      {dippin.playlist.length > 0 && <PlayerSmall playlist={dippin.playlist} id={id} />}

      <Box h="16px" />
    </Box>
  );
};
