import { Box, Flex, Spacer } from '@chakra-ui/react';

export const DippinMode = ({ mode, setMode }: any) => {
  return (
    <Flex textAlign="center" marginY="16px">
      <Spacer />
      <Box
        marginX="4px"
        w="80px"
        borderRadius="20px"
        bg={mode === 'recent' ? 'cyan.400' : 'cyan.200'}
        onClick={() => {
          setMode('recent');
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
          setMode('trend');
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
          setMode('following');
        }}
      >
        팔로잉
      </Box>
      <Spacer />
    </Flex>
  );
};
