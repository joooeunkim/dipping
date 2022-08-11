import { Box, Image, useColorModeValue } from '@chakra-ui/react';
import { useState } from 'react';

export const PlayerLargeItem = (props: any) => {
  const borderColor = useColorModeValue('gray.200', 'gray.600');
  const { artist, title, albumart, id, selected } = props;
  return (
    <Box position="relative" left="1vw" w="86vw" h="18vw">
      {selected && (
        <Box position="relative" w="86vw" h="18vw" bg="blue.300" borderRadius="2vw" opacity="0.5" />
      )}

      <Image
        position="absolute"
        left="1vw"
        top="1vw"
        boxSize="16vw"
        marginX="0vw"
        borderRadius="2vw"
        objectFit="cover"
        src={'https://i.ytimg.com/vi/' + id + '/maxresdefault.jpg'}
        fallbackSrc={'https://i.ytimg.com/vi/' + id + '/hqdefault.jpg'}
        alt="why"
      />
      <Box
        position="absolute"
        left="19vw"
        top="4vw"
        fontSize="14px"
        fontWeight="400"
        lineHeight="15px"
        bg=""
        color="gray.600"
      >
        {artist}
      </Box>
      <Box
        position="absolute"
        left="19vw"
        top="8vw"
        h="25px"
        fontSize="18px"
        fontWeight="400"
        lineHeight="25px"
        bg=""
        overflow="hidden"
        color={selected ? 'white' : 'black'}
      >
        {title}
      </Box>
    </Box>
  );
};
