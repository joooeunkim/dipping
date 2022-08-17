import { Box, Flex, Spacer } from '@chakra-ui/react';
import { useDispatch, useSelector } from 'react-redux';
import { setMode } from '../../reducers/dippinReducer';

export const DippinMode = () => {
  const dispatch = useDispatch();
  const mode = useSelector((state: any) => state.dippinReducer.mode);
  return (
    <Flex textAlign="center" marginY="16px">
      <Spacer />
      <Box
        marginX="4px"
        w="80px"
        borderRadius="20px"
        bg={mode === 'recent' ? 'cyan.400' : 'cyan.200'}
        onClick={() => {
          dispatch(setMode('recent'));
        }}
      >
        최신순
      </Box>
      <Box
        marginX="4px"
        w="80px"
        borderRadius="20px"
        bg={mode === 'trend' ? 'cyan.400' : 'cyan.200'}
        onClick={() => {
          dispatch(setMode('trend'));
        }}
      >
        트렌드
      </Box>
      <Box
        marginX="4px"
        w="80px"
        borderRadius="20px"
        bg={mode === 'following' ? 'cyan.400' : 'cyan.200'}
        onClick={() => {
          dispatch(setMode('following'));
        }}
      >
        팔로잉
      </Box>
      <Spacer />
    </Flex>
  );
};
