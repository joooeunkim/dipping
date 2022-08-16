import { Box, Flex, Image, useColorModeValue } from '@chakra-ui/react';
import { useState } from 'react';
import { popCustomList, pushCustomList } from '../../reducers/dippinReducer';
import { useDispatch, useSelector } from 'react-redux';
import { Music } from '../../types/HomeFeedData';

export const PlayerSmallItem = (props: any) => {
  const borderColor = useColorModeValue('gray.200', 'gray.600');
  const { artist, title, albumart, id, selected, onClickBody } = props;
  const [checked, setChecked] = useState(false);

  const dispatch = useDispatch();
  const toggleChecked = () => {
    if (checked) {
      dispatch(popCustomList(id));
    } else {
      dispatch(pushCustomList({ title, artist, albumart, id } as Music));
    }
    setChecked(s => !s);
  };
  return (
    <Box position="relative" h="64px" w="100%">
      {selected && (
        <Box position="absolute" h="64px" w="100%" bg="blue.300" borderRadius="2vw" opacity="0.5" />
      )}
      <Flex position="relative" h="64px">
        <Flex h="64px" w="full" onClick={onClickBody}>
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
            <Box
              h="20px"
              fontSize="14px"
              lineHeight="20px"
              overflow="hidden"
              color={selected ? 'white' : 'black'}
            >
              {title}
            </Box>
          </Box>
        </Flex>
        <Box
          margin="20px 4px"
          boxSize="24px"
          fontSize="24px"
          className={checked ? 'fa-light fa-minus' : 'fa-light fa-plus'}
          onClick={toggleChecked}
          color="gray.500"
        />
      </Flex>
    </Box>
  );
};
