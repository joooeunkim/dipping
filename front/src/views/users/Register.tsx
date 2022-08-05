import { Text, Box, Button, Center, ChakraProvider, Container, Image } from '@chakra-ui/react';
import { Link } from 'react-router-dom';

// 회원가입 방법 선택 컴포넌트
export const Register = () => {
  return (
    <ChakraProvider>
      <Container maxW="480px" w="95%" bg="" h="100vh" margin="0 auto">
        <Center mt="32px">
          <Image src="/logo_dippin.png" w="48" mt="12" />
        </Center>
        <Center mt="24" w="100%" flexWrap="wrap">
          <Box w="100%">
            <Link to="/process/step1">
              <Button
                w="100%"
                bg="cyan.400"
                color="white"
                _hover={{
                  bg: 'cyan.500',
                }}
                _active={{
                  bg: 'cyan.500',
                }}
              >
                디핑 회원가입
              </Button>
            </Link>
          </Box>
          <Box w="100%" mt="8px">
            <Button
              leftIcon={<Image src="/google_logo.png" />}
              w="100%"
              bg="white"
              color="black"
              _hover={{
                bg: 'gray.100',
              }}
              _active={{
                bg: 'gray.100',
              }}
            >
              구글 회원가입
            </Button>
          </Box>
          <Box w="100%" mt="8px">
            <Button
              leftIcon={<Image src="/kakao_logo.png" />}
              w="100%"
              bg="#FEE500"
              color="#392020"
              _hover={{
                bg: '#EFD800',
              }}
              _active={{
                bg: '#EFD800',
              }}
            >
              카카오 회원가입
            </Button>
          </Box>
          <Link to="/login">
            <Text mt="64px" color="cyan.400">
              로그인 화면으로 돌아가기
            </Text>
          </Link>
        </Center>
      </Container>
    </ChakraProvider>
  );
};
