import { Box, Button, Center, ChakraProvider, Container, Text, Flex } from '@chakra-ui/react';
import { useState } from 'react';
import { Genre } from './Genre';
import { InterestTag } from './InterestTag';
import { UserInfo } from './UserInfo';

export const RegisterProcessLayout = () => {
  const [flag, setFlag] = useState<string>('step1');
  const [view, setView] = useState(<UserInfo />);
  const [email, setEmail] = useState('mon2210@naver.com');

  const eventEmail = (_email: any) => {
    setEmail(_email);
  };

  console.log(flag);

  const onClick = (step: string) => {
    setFlag(step);
    if (step == 'register') {
      // eslint-disable-next-line no-restricted-globals
      location.href = '/register';
    }
    if (step == 'submit') {
      // eslint-disable-next-line no-restricted-globals
      location.href = '/submit';
    }
    if (step == 'step1') {
      setView(<UserInfo eventEmail={eventEmail} />);
    } else if (step == 'step2') {
      setView(<Genre />);
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
        <Box position="absolute" left="5"></Box>

        <Text fontSize="2xl" fontWeight="bold">
          {title}
        </Text>
      </Center>
      {/* {email} */}
      {view}
      <Flex
        w="100%"
        pr="4"
        pl="4"
        mb="10"
        position="absolute"
        bottom="0"
        left="0"
        justifyContent="space-between"
      >
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
