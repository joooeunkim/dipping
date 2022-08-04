import {
  Container,
  Flex,
  Image,
  Box,
  Text,
  Input,
  Spacer,
  FormControl,
  FormLabel,
  Select,
  Link,
} from '@chakra-ui/react';
import { ModalNavBar } from '../../components/ModalNavBar';

export const ProfileEdit = () => {
  const props = {
    title: '프로필 수정',
    leftElement: (
      <Image src="/logo_icon.png" alt="logo" objectFit="contain" h="32px" margin="2px" />
    ),
    rightElement: <Box className="fa-light fa-bars" lineHeight="36px" fontSize="24px" bg="" />,
  };

  return (
    <Box>
      <ModalNavBar {...props} />
      <Container maxW="480px" w="100%" bg="" h="100vh" margin="0 auto">
        <Flex flexDirection="column" alignItems="center" marginTop="40px" marginBottom="40px">
          <Box>
            <Image
              src="https://images.unsplash.com/photo-1596854407944-bf87f6fdd49e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80"
              borderRadius="full"
              htmlWidth="88px"
              htmlHeight="88px"
              alt="ProfileImg"
              marginBottom="16px"
            />
            <Text>사진 바꾸기</Text>
          </Box>
        </Flex>
        <Flex>
          <Box height="40px">
            <Text>이름</Text>
          </Box>
          <Spacer />
          <Box>
            <Input variant="flushed" placeholder="월래이름" />
          </Box>
        </Flex>
        <Flex marginBottom="8px">
          <Box alignItems="left" marginTop="8px">
            <Text>관심분야</Text>
          </Box>
          <Spacer />
          <Box>
            <Input variant="flushed" placeholder="#관심분야" />
          </Box>
        </Flex>
        <Flex>
          <Text>ㅇㅇ</Text>
        </Flex>
        <Flex marginBottom="160px">
          <FormControl>
            <FormLabel marginBottom="16px">추천 리스트</FormLabel>
            <Select height="32px" placeholder="선택된 리스트">
              <option>1번 리스트</option>
              <option>2번 리스트</option>
            </Select>
          </FormControl>
        </Flex>
        <Flex>
          <Link>개인정보 변경</Link>
        </Flex>
      </Container>
    </Box>
  );
};
