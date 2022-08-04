import { Box, Button, Center, Container, Image, Input, Text } from '@chakra-ui/react';
import { Link } from 'react-router-dom';

export const FindPassword = () => {
  return (
    <Container>
      <Center>
        <Image src="/findPassword.png" alt="logo" w="30" mt="14" />
      </Center>
      <Box>
        <Text align="center" fontSize="2xl" fontWeight="bold" mt="12">
          비밀번호를 잊으셨나요?
        </Text>
        <Text align="center" mt="2" color="gray.500">
          등록하신 이메일 주소를 입력하시면 새로운 비밀번호를 보내드립니다.
        </Text>
      </Box>
      <Box mt="4">
        <Input variant="flushed" placeholder="이메일" />
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
          이메일 전송
        </Button>
        <Link to="/login">
          <Text mt="16px" color="cyan.400" align="center">
            로그인 화면으로 돌아가기
          </Text>
        </Link>
      </Box>
    </Container>
  );
};
