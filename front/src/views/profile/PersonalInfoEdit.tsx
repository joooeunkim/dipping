import { Container, Flex, Image, Box, Text, Input } from '@chakra-ui/react';
import { ModalNavBar } from '../../components/floatingbar/ModalNavBar';
import React from 'react';

export const PersonalInfoEdit = () => {
  const props = {
    title: '개인정보 수정',
    leftElement: (
      <Image src="/logo_icon.png" alt="logo" objectFit="contain" h="32px" margin="2px" />
    ),
    rightElement: <Box className="fa-light fa-bars" lineHeight="36px" fontSize="24px" bg="" />,
  };
  const [show] = React.useState(false);

  return (
    <Box>
      <ModalNavBar {...props} />
      <Container maxW="480px" w="100%" bg="" h="100vh" margin="0 auto">
        <Flex flexDirection="column" alignItems="center" marginTop="96px">
          <Flex width="100%" marginBottom="48px">
            <Box>
              <Text width="96px">현재</Text>
              <Text width="96px">비밀번호</Text>
            </Box>
            <Input
              width="100%"
              variant="flushed"
              placeholder="현재 비밀번호"
              type={show ? 'text' : 'password'}
            />
          </Flex>
          <Flex width="100%" marginBottom="48px">
            <Box>
              <Text width="96px">변경할</Text>
              <Text width="96px">비밀번호</Text>
            </Box>
            <Input
              width="100%"
              variant="flushed"
              placeholder="변경할 비밀번호"
              type={show ? 'text' : 'password'}
            />
          </Flex>
          <Flex width="100%">
            <Box>
              <Text width="96px">비밀번호</Text>
              <Text width="96px">확인</Text>
            </Box>
            <Input
              width="100%"
              variant="flushed"
              placeholder="비밀번호 확인"
              type={show ? 'text' : 'password'}
            />
          </Flex>
        </Flex>
      </Container>
    </Box>
  );
};
