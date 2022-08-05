import { Box, Button, ChakraProvider, Input } from '@chakra-ui/react';
import React from 'react';
import { useOutletContext } from 'react-router-dom';
// 나중에 소셜이냐 아니냐로 필드 숨기는거 처리해야함.

export const UserInfo = (props: any) => {
  return (
    <Box>
      <Box pr="1" pl="1">
        <Input
          onChange={event => props.eventEmail(event.target.value)}
          variant="flushed"
          focusBorderColor="cyan.400"
          placeholder="이메일"
          type="email"
          size="lg"
          mt="8"
        />
        <Input
          variant="flushed"
          focusBorderColor="cyan.400"
          placeholder="닉네임"
          size="lg"
          mt="8"
        />
        <Input
          variant="flushed"
          focusBorderColor="cyan.400"
          placeholder="비밀번호"
          type="password"
          size="lg"
          mt="8"
        />
        <Input
          variant="flushed"
          focusBorderColor="cyan.400"
          placeholder="비밀번호확인"
          type="password"
          size="lg"
          mt="8"
        />
      </Box>
    </Box>
  );
};
