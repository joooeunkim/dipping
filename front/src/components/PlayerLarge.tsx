import { Box, Image, useColorModeValue } from '@chakra-ui/react';
import { useState } from 'react';

export const PlayerLarge = () => {
  const borderColor = useColorModeValue('gray.200', 'gray.600');

  return (
    <>
      {/* album art */}
      <Box position="relative" w="full" bg="">
        <Image boxSize="100vw" marginX="0vw" borderRadius="0px" src="https://bit.ly/3PXNy1o" />

        <Box>
          <Box
            position="absolute"
            boxSize="100vw"
            top="0px"
            bg="whiteAlpha.400"
            backdropFilter="auto"
            backdropBlur="10px"
            display="none"
          >
            <Image boxSize="20vw" marginX="0vw" borderRadius="0px" src="https://bit.ly/3PXNy1o" />
          </Box>
        </Box>

        <Box
          position="absolute"
          left="6vw"
          bottom="6vw"
          className="fa-solid fa-album"
          fontSize="32px"
          lineHeight="32px"
        />

        <Box
          position="absolute"
          right="6vw"
          bottom="6vw"
          className="fa-solid fa-play"
          fontSize="28px"
          lineHeight="32px"
        />
      </Box>

      {/* song info */}
      <Box position="relative" h="48px" w="full" bg="" textAlign="center">
        <Box
          position="absolute"
          left="4vw"
          top="8px"
          fontSize="12px"
          fontWeight="400"
          lineHeight="15px"
          bg=""
          color="gray.400"
        >
          My Chemical Romance
        </Box>
        <Box
          position="absolute"
          left="4vw"
          bottom="4px"
          fontSize="20px"
          fontWeight="500"
          lineHeight="25px"
          bg=""
        >
          Welcome To The Black Parade
        </Box>
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
    </>
  );
};
