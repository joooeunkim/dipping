import { Box, ChakraProvider, Input } from '@chakra-ui/react';
import { useUser } from './RegisterProcessLayout';
// 나중에 소셜이냐 아니냐로 필드 숨기는거 처리해야함.

export const UserInfo = () => {
  const { user } = useUser();
  console.log(user);
  return (
    <ChakraProvider>
      <Box bg="white" pr="1" pl="1">
        {/* user : {user?.email} */}
        <Input
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
    </ChakraProvider>
  );
};
