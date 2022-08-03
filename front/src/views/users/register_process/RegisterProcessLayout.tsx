import { Box, Button, Center, ChakraProvider, Container, Text } from '@chakra-ui/react';
import React from 'react';
import { useLocation, useOutletContext } from 'react-router-dom';
import { Link, Outlet } from 'react-router-dom';

type User = {
  email: String;
  nickname: String;
  password: String;
};

type ContextType = {
  user: User | null;
};

export function useUser() {
  return useOutletContext<ContextType>();
}

export const RegisterProcessLayout = () => {
  const location = useLocation();

  const [user, setUser] = React.useState<User | null>({
    nickname: 'sangham',
    email: 'email',
    password: 'pass',
  });

  const flag = location.pathname.split('/')[2];
  let title: String = '';
  let next: String = '';
  let prev: String = '';

  if (flag == 'step1') {
    title = '회원정보 입력';
    next = '/process/step2';
    prev = '/register';
  } else if (flag == 'step2') {
    title = '관심음악 설정';
    next = '/process/step3';
    prev = '/process/step1';
  } else if (flag == 'step3') {
    title = '관심사 등록';
    next = '/process/submit';
    prev = '/process/step2';
  }

  return (
    <Container h="100vh" position="relative">
      <Center p="4">
        <Box position="absolute" left="5">
          <Link to={{ pathname: `${prev}` }}>
            <Text fontSize="2xl" fontWeight="bold">
              <i className="fa-regular fa-angle-left"></i>
            </Text>
          </Link>
        </Box>

        <Text fontSize="2xl" fontWeight="bold">
          {title}
        </Text>
      </Center>
      <Outlet context={{ user }} />

      <Box w="100%" pr="4" pl="4" mb="10" position="absolute" bottom="0" left="0">
        <Link
          to={{
            pathname: `${next}`,
          }}
        >
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
            계속
          </Button>
        </Link>
      </Box>
    </Container>
  );
};
