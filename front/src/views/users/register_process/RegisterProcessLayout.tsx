import { Box, Button, Center, ChakraProvider, Container, Text, Flex } from '@chakra-ui/react';
import { useState } from 'react';
import { useSelector } from 'react-redux';
import { Genre } from './Genre';
import { InterestTag } from './InterestTag';
import { UserInfo } from './UserInfo';
import { registerSubmit, registerSubmitSocial } from '../../../api/registerSubmit';
import { useLocation } from 'react-router-dom';

export const RegisterProcessLayout = () => {
  const redirectState = useLocation();
  const [flag, setFlag] = useState<string>('step1');
  const [view, setView] = useState(<UserInfo token={redirectState.state} />);
  const [email, setEmail] = useState('');

  const registerState = useSelector((state: any) => {
    return state.registerReducer;
  });

  const eventEmail = (e: any) => {
    console.log(e.target.value);
    setEmail(e.target.value);
  };

  console.log(redirectState.state);

  const onClick = (step: string) => {
    setFlag(step);
    if (step == 'register') {
      // eslint-disable-next-line no-restricted-globals
      window.location.href = '/register';
    }
    if (step == 'submit') {
      // eslint-disable-next-line no-restricted-globals
      // location.href = '/login';
      if (redirectState.state == null) {
        registerSubmit(registerState);
      } else {
        // 회원가입 입력정보, jwt
        registerSubmitSocial(registerState, redirectState.state);
      }
    }
    if (step == 'step1') {
      setView(<UserInfo socialFlag={redirectState.state} />);
    } else if (step == 'step2') {
      if (registerState.dupEmail && registerState.dupNickname) {
        setView(<Genre />);
      } else {
        setFlag('step1');
        if (registerState.dupEmail == false) {
          alert('이메일 중복확인을 해주세요.');
        } else if (registerState.dupNickname == false) {
          alert('닉네임 중복확인을 해주세요.');
        }
        console.log(registerState);
      }
    } else if (step == 'step3') {
      setView(<InterestTag />);
    }
  };

  let title: string = '회원정보 입력';
  let next: string = '';
  let prev: string = '';
  if (flag == 'step1') {
    title = '회원정보 입력';
    next = 'step2';
    prev = 'register';
  } else if (flag == 'step2') {
    title = '관심음악 설정';
    next = 'step3';
    prev = 'step1';
  } else if (flag == 'step3') {
    title = '관심사 등록';
    next = 'submit';
    prev = 'step2';
  }

  return (
    <Container h="100vh" position="relative">
      <Center p="4">
        <Box left="5"></Box>

        <Text fontSize="2xl" fontWeight="bold">
          {title}
        </Text>
      </Center>
      {/* {email} */}
      {view}
      <Flex w="100%" mt="4" pr="4" pl="4" mb="10" left="0" justifyContent="space-between">
        <Button
          onClick={() => {
            onClick(prev);
          }}
          // display={prev == 'register' ? 'none' : 'block'}
        >
          이전
        </Button>
        <Button
          onClick={() => {
            onClick(next);
          }}
          bg="cyan.400"
          color="white"
          _hover={{
            bg: 'cyan.500',
          }}
          _active={{
            bg: 'cyan.500',
          }}
        >
          {next == 'submit' ? '완료' : '다음'}
        </Button>
      </Flex>
    </Container>
  );
};
