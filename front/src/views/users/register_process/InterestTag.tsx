import { Box, Center, Input, Text } from '@chakra-ui/react';

export const InterestTag = () => {
  return (
    <Box>
      <Box>
        <Input variant="flushed" placeholder="관심태그입력" />
      </Box>
      <Box>
        {/* 컴포넌트화 필요 */}
        <Center
          p="2"
          m="2"
          ml="0"
          border="1px"
          width="fit-content"
          display="inline-block"
          borderRadius="full"
          borderColor="gray.500"
          color="gray.500"
        >
          태그
          <Box color="cyan.400" display="inline-block" ml="2">
            <i className="fa-regular fa-xmark"></i>
          </Box>
        </Center>
        <Center
          p="2"
          mr="2"
          border="1px"
          width="fit-content"
          display="inline-block"
          borderRadius="full"
          borderColor="gray.500"
          color="gray.500"
        >
          태그
          <Box color="cyan.400" display="inline-block" ml="2">
            <i className="fa-regular fa-xmark"></i>
          </Box>
        </Center>
        <Center
          p="2"
          mr="2"
          border="1px"
          width="fit-content"
          display="inline-block"
          borderRadius="full"
          borderColor="gray.500"
          color="gray.500"
        >
          태그태그태그22222
          <Box color="cyan.400" display="inline-block" ml="2">
            <i className="fa-regular fa-xmark"></i>
          </Box>
        </Center>
        <Center
          p="2"
          mr="2"
          border="1px"
          width="fit-content"
          display="inline-block"
          borderRadius="full"
          borderColor="gray.500"
          color="gray.500"
        >
          태그
          <Box color="cyan.400" display="inline-block" ml="2">
            <i className="fa-regular fa-xmark"></i>
          </Box>
        </Center>
      </Box>
    </Box>
  );
};
