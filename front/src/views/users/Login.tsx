import {
  Box,
  Button,
  Center,
  Checkbox,
  Container,
  Flex,
  FormControl,
  Image,
  Input,
  Spacer,
  Text,
} from '@chakra-ui/react';
import { useState } from 'react';
import { useSelector } from 'react-redux';
import { useDispatch } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';

import { local } from '../../api/login/local';

// 로그인 이메일, 비밀번호 임력하는 페이지 컴포넌트
export const Login = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const emailChangeHandler = (e: any) => {
    setEmail(e.target.value);
  };

  const passwordChangeHandler = (e: any) => {
    setPassword(e.target.value);
  };

  const enterLogin = (e: any) => {
    if (e.code === 'Enter') {
      local(email, password, dispatch, navigate);
    }
  };

  // const token = useSelector((state: any) => state.tokenReducer.accessToken);
  // console.log(token);

  return (
    <Container>
      <Box>
        <Center width="100%">
          <Link to="/">
            <Image src="/logo_dippin.png" alt="logo" w="40" mt="14" />
          </Link>
        </Center>
        <Box width="100%" mt="20">
          <Input
            id="email"
            variant="flushed"
            focusBorderColor="cyan.400"
            placeholder="이메일"
            type="email"
            size="lg"
            fontSize="medium"
            onChange={emailChangeHandler}
          />
          <Input
            id="password"
            variant="flushed"
            focusBorderColor="cyan.400"
            placeholder="비밀번호"
            type="password"
            size="lg"
            fontSize="medium"
            mt="4"
            onChange={passwordChangeHandler}
            onKeyUp={enterLogin}
          />
        </Box>
        <Box mt="4">
          <Button
            w="100%"
            bg="cyan.400"
            // size="lg"
            color="white"
            _hover={{
              bg: 'cyan.500',
            }}
            _active={{
              bg: 'cyan.500',
            }}
            onClick={() => {
              local(email, password, dispatch, navigate);
            }}
          >
            로그인
          </Button>
        </Box>
        <Flex justifyContent="space-between" mt="2">
          <Checkbox colorScheme="cyan" defaultChecked>
            로그인 유지
          </Checkbox>
          <Link to="/findPassword">
            <Text color="cyan.400">비밀번호 찾기</Text>
          </Link>
        </Flex>
      </Box>
      <Center w="100%" flexWrap="wrap">
        <Box w="100%" mt="24px">
          <a href="http://i7b210.p.ssafy.io/oauth2/authorization/google">
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
              구글로 시작하기
            </Button>
          </a>
        </Box>

        <Box w="100%" mt="8px">
          <a href="http://i7b210.p.ssafy.io/oauth2/authorization/kakao">
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
              카카오로 시작하기
            </Button>
          </a>
        </Box>

        <Text mt="4">
          아직 아이디가 없으신가요?{' '}
          <Box display="inline-block" color="cyan.400">
            <Link to="/register">&nbsp;회원가입</Link>
          </Box>
        </Text>
      </Center>
    </Container>
  );
};
