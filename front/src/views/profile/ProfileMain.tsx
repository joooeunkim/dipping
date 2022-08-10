import {
  Container,
  Image,
  Flex,
  Text,
  Box,
  Tabs,
  TabList,
  TabPanels,
  Tab,
  TabPanel,
  Button,
  FormControl,
  FormLabel,
  Switch,
  Link,
  FormHelperText,
  Spacer,
} from '@chakra-ui/react';
import { ModalNavBar } from '../../components/floatingbar/ModalNavBar';
import { HomeFeed, DippinFeed, UserShort } from '../../components/FeedUserShort';

export const ProfileMain = () => {
  const props = {
    title: '프로필',
    leftElement: (
      <Image src="/logo_icon.png" alt="logo" objectFit="contain" h="32px" margin="2px" />
    ),
    rightElement: <Box className="fa-light fa-bars" lineHeight="36px" fontSize="24px" bg="" />,
  };

  return (
    <Box>
      <ModalNavBar {...props} />
      <Container maxW="480px" w="100%" bg="" h="100vh" margin="0 auto">
        <FormControl marginTop="16px" marginBottom="16px">
          <Flex marginRight="0px" paddingLeft="24px">
            <Box>
              <Image
                src="https://images.unsplash.com/photo-1596854407944-bf87f6fdd49e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80"
                borderRadius="full"
                htmlWidth="88px"
                htmlHeight="88px"
                alt="ProfileImg"
              />
              <Text textAlign="center" fontSize="16px">
                nick
              </Text>
            </Box>
            <Spacer />
            <Box w="200px">
              <Flex height="72px">
                <FormHelperText p="2" textAlign="center" fontSize="16px">
                  <Text>11</Text>
                  <Text>게시물</Text>
                </FormHelperText>
                <Link href="/Follow">
                  <FormHelperText p="2" textAlign="center" fontSize="16px">
                    <Text>12</Text>
                    <Text>팔로워</Text>
                  </FormHelperText>
                </Link>
                <Link href="/Follow">
                  <FormHelperText p="2" textAlign="center" fontSize="16px">
                    <Text>13</Text>
                    <Text>팔로잉</Text>
                  </FormHelperText>
                </Link>
              </Flex>
              <Button colorScheme="gray.200" variant="outline" height="24px" width="100%">
                <Link href="/profile/edit" fontSize="16px">
                  프로필 수정
                </Link>
              </Button>
            </Box>
          </Flex>
        </FormControl>
        <Flex marginLeft="24px" marginBottom="32px">
          <Text fontSize="16px" color="gray.500">
            #힙 #신나는 #코딩중
          </Text>
        </Flex>
        <FormControl display="flex" alignItems="center" justifyContent="right">
          <FormLabel htmlFor="email-alerts" mb="0" fontSize="8px">
            <i className="fa-thin fa-lock">게시글숨기기</i>
          </FormLabel>
          <Switch size="sm" id="PostHide" />
        </FormControl>
        <Box mt="2" marginBottom="8px">
          <Text textAlign="center">'User' 님의 추천 플레이리스트</Text>
        </Box>
        <Flex paddingBottom="56px" marginRight="0">
          <Image
            src="https://media.istockphoto.com/id/637093118/ko/%EC%82%AC%EC%A7%84/%EC%84%A0%EB%B0%98%EC%97%90-%EB%B9%84%EB%8B%90-%EB%A0%88%EC%BD%94%EB%93%9C.webp?s=612x612&w=is&k=20&c=oB37HTi3HV2u5SIveRMnYDH0ZAmjpKAsa5FIW74eHr8="
            htmlWidth="120px"
            htmlHeight="120px"
            alt="PostImg"
          />
          <Spacer />
          <Box marginTop="8px" width="200px">
            <Text>제목인듯</Text>
            <Text marginTop="8px" width="200px" fontSize="8px">
              게시물 내용 대충 어쩌구 적으면 글자수가 이렇게 채워지겠지 그러면 두줄이 넘어 세번째
            </Text>
            <Text marginTop="16px" fontSize="8px" color="gray.500" textAlign="right">
              댓글 00개
            </Text>
            <Flex fontSize="8px" marginRight="0">
              <i className="fa-solid fa-user" />
              <Text marginLeft="4px">00명이 좋아합니다</Text>
              <Spacer />
              <Flex width="40px">
                <Box fontSize="8px" rowGap="2" color="cyan.400">
                  <i className="fa-solid fa-heart" />
                </Box>
                <Spacer />
                <Box fontSize="8px" rowGap="2">
                  <i className="fa-light fa-share-nodes" />
                </Box>
                <Spacer />
                <Box fontSize="8px" rowGap="2" color="cyan.400">
                  <i className="fa-solid fa-bookmark" />
                </Box>
              </Flex>
            </Flex>
          </Box>
        </Flex>
        <Tabs isFitted margin="0px">
          <TabList>
            <Tab fontSize="8px">내 게시글</Tab>
            <Tab fontSize="8px">내 디핑 게시글</Tab>
            <Tab fontSize="8px">북마크 게시글</Tab>
          </TabList>

          <TabPanels>
            <TabPanel paddingLeft="0" paddingRight="0">
              <HomeFeed />
            </TabPanel>
            <TabPanel paddingLeft="0" paddingRight="0">
              <DippinFeed />
            </TabPanel>
            <TabPanel paddingLeft="0" paddingRight="0">
              <UserShort />
            </TabPanel>
          </TabPanels>
        </Tabs>
      </Container>
    </Box>
  );
};
