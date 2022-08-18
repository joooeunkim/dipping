import { Box, Flex, Spacer } from '@chakra-ui/react';
import { useDispatch, useSelector } from 'react-redux';
import { setMode } from '../../reducers/searchReducer';

export const SearchMode = () => {
  const dispatch = useDispatch();
  const mode = useSelector((state: any) => state.searchReducer.mode);
  return (
    <Flex textAlign="center" marginY="16px">
      <Spacer />
      <Box
        marginX="4px"
        w="80px"
        borderRadius="20px"
        bg={mode === 'post' ? 'cyan.400' : 'cyan.200'}
        onClick={() => {
          dispatch(setMode('post'));
        }}
      >
        포스트
      </Box>
      <Box
        marginX="4px"
        w="80px"
        borderRadius="20px"
        bg={mode === 'user' ? 'cyan.400' : 'cyan.200'}
        onClick={() => {
          dispatch(setMode('user'));
        }}
      >
        사용자
      </Box>
      <Spacer />
    </Flex>
  );
};
