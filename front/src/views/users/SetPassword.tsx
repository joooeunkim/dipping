// 새로운 비밀번호 설정하는 페이지
import { Box, Button, Center, Container, Image, Input, Text } from '@chakra-ui/react';
import { Link } from 'react-router-dom';

export const SetPassword = () => {
  return (
    <Container>
      <Center>
        <Image src="/findPassword.png" alt="logo" w="30" mt="14" />
      </Center>
      <Box>
        <Text align="center" fontSize="2xl" fontWeight="bold" mt="12">
          비밀번호 재설정
        </Text>
        <Text align="center" mt="2" color="gray.500">
          새로운 비밀번호를 입력해주세요.
        </Text>
      </Box>
      <Box mt="4">
        <Input
          variant="flushed"
          focusBorderColor="cyan.400"
          placeholder="비밀번호"
          type="password"
          size="lg"
          mt="4"
        />
        <Input
          variant="flushed"
          focusBorderColor="cyan.400"
          placeholder="비밀번호 확인"
          type="password"
          size="lg"
          mt="4"
        />
        <Button
          w="100%"
          bg="cyan.400"
          mt="4"
          color="white"
          _hover={{
            bg: 'cyan.500',
          }}
          _active={{
            bg: 'cyan.500',
          }}
        >
          비밀번호 변경
        </Button>
      </Box>
    </Container>
  );
};
