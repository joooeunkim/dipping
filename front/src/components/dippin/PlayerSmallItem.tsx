import { Box, Image, useColorModeValue } from '@chakra-ui/react';
import { useState } from 'react';

export const PlayerSmallItem = (props: any) => {
  const borderColor = useColorModeValue('gray.200', 'gray.600');
  const { artist, title, albumart, id, selected } = props;
  return (
    <Box position="relative" h="64px">
      {selected && (
        <Box position="relative" h="64px" bg="blue.300" borderRadius="2vw" opacity="0.5" />
      )}

      <Image
        position="absolute"
        left="4px"
        top="4px"
        boxSize="56px"
        marginX="0vw"
        borderRadius="2vw"
        objectFit="cover"
        src={albumart}
        alt="why"
      />
      <Box
        position="absolute"
        left="19vw"
        top="4vw"
        fontSize="12px"
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
        fontSize="14px"
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
