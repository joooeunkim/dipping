import { Box, Flex, Image, useColorModeValue } from '@chakra-ui/react';
import { useState } from 'react';
import { popCustomList, pushCustomList } from '../../reducers/dippinReducer';
import { useDispatch, useSelector } from 'react-redux';
import { Music } from '../../types/HomeFeedData';

export const DippinDetailItem = (props: any) => {
  const borderColor = useColorModeValue('gray.200', 'gray.600');
  const { artist, title, albumart, id } = props;

  const dispatch = useDispatch();

  return (
    <Box position="relative" h="64px" w="100%">
      <Flex position="relative" h="64px">
        <Flex h="64px" w="full">
          <Image
            boxSize="56px"
            margin="4px"
            borderRadius="8px"
            objectFit="cover"
            src={albumart}
            alt="why"
          />
          <Box marginY="12px" marginLeft="4px" w="full">
            <Box h="20px" fontSize="12px" lineHeight="20px" color="gray.600">
              {artist}
            </Box>
            <Box h="20px" fontSize="14px" lineHeight="20px" overflow="hidden">
              {title}
            </Box>
          </Box>
        </Flex>
        <Box
          margin="20px 4px"
          boxSize="24px"
          fontSize="24px"
          className="fa-light fa-close"
          onClick={() => dispatch(popCustomList(id))}
          color="gray.500"
        />
      </Flex>
    </Box>
  );
};
