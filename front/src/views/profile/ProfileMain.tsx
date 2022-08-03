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
  Grid,
  GridItem,
} from '@chakra-ui/react';

export const ProfileMain = () => {
  return (
    <Container maxW="480px" w="100%" bg="" h="100vh" margin="0 auto">
      <FormControl marginTop="32px" marginBottom="16px">
        <Flex marginRight="0px" paddingLeft="24px">
          <Box>
            <Image
              src="logo192.png"
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
              <FormHelperText p="2" textAlign="center" fontSize="16px">
                <Text>12</Text>
                <Text>팔로워</Text>
              </FormHelperText>
              <FormHelperText p="2" textAlign="center" fontSize="16px">
                <Text>13</Text>
                <Text>팔로잉</Text>
              </FormHelperText>
            </Flex>
            <Button colorScheme="gray.200" variant="outline" height="24px" width="100%">
              <Link href="/profile/edit" fontSize="16px">
                프로필 수정
              </Link>
            </Button>
          </Box>
        </Flex>
      </FormControl>
      {/* 관심분야 */}
      <Flex marginLeft="24px" marginBottom="10px">
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
      <Box mt="2">
        <Text textAlign="center">'User' 님의 추천 플레이리스트</Text>
      </Box>
      <Flex paddingBottom="16px" marginRight="0">
        <Image
          src="logo192.png"
          borderRadius="full"
          htmlWidth="120px"
          htmlHeight="120px"
          alt="PostImg"
        />
        <Spacer />
        <Box marginTop="16px" width="200px">
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
            <Box fontSize="8px" rowGap="2">
              <i className="fa-solid fa-heart" />
              <i className="fa-light fa-share-nodes" />
              <i className="fa-solid fa-bookmark" />
            </Box>
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
          <TabPanel>
            <Grid templateColumns="repeat(3, 10fr)">
              <GridItem>
                <Image src="logo192.png" htmlWidth="103.66px" htmlHeight="103.66px" alt="Post" />
              </GridItem>
              <GridItem>
                <Image src="logo192.png" htmlWidth="103.66px" htmlHeight="103.66px" alt="Post" />
              </GridItem>
              <GridItem>
                <Image src="logo192.png" htmlWidth="103.66px" htmlHeight="103.66px" alt="Post" />
              </GridItem>
              <GridItem>
                <Image src="logo192.png" htmlWidth="103.66px" htmlHeight="103.66px" alt="Post" />
              </GridItem>
              <GridItem>
                <Image src="logo192.png" htmlWidth="103.66px" htmlHeight="103.66px" alt="Post" />
              </GridItem>
              <GridItem>
                <Image src="logo192.png" htmlWidth="103.66px" htmlHeight="103.66px" alt="Post" />
              </GridItem>
            </Grid>
          </TabPanel>
          <TabPanel>
            <p>two!</p>
          </TabPanel>
          <TabPanel>
            <Grid templateColumns="repeat(3, 10fr)">
              <GridItem>
                <Image src="logo192.png" htmlWidth="103.66px" htmlHeight="103.66px" alt="Post" />
              </GridItem>
            </Grid>
          </TabPanel>
        </TabPanels>
      </Tabs>
    </Container>
  );
};
