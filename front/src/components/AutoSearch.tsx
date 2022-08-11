import { Box, Input, InputGroup, InputLeftElement } from '@chakra-ui/react';

export const AutoSearch = () => {
  return (
    <Box width="100%">
      <InputGroup>
        <InputLeftElement
          h="32px"
          children={<Box fontSize="16px" className="fa-regular fa-magnifying-glass" />}
        />
        <Input
          h="32px"
          variant="filled"
          borderRadius="1.5vh"
          type="text"
          placeholder="검색어를 입력하세요."
        />
      </InputGroup>
    </Box>
  );
};
