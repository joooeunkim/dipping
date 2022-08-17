import {
  Box,
  Text,
  Flex,
  Image,
  Grid,
  GridItem,
  Spacer,
  AspectRatio,
  Tabs,
  TabList,
  Tab,
  TabPanels,
  TabPanel,
} from '@chakra-ui/react';

export const HomeFeed = () => {
  return (
    <Grid templateColumns="repeat(3, 1fr)" gap={0.5}>
      <Image
        src="https://images.unsplash.com/photo-1596854407944-bf87f6fdd49e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80"
        alt="PostImg"
      />
      <Image
        src="https://images.unsplash.com/photo-1596854407944-bf87f6fdd49e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80"
        alt="PostImg"
      />
      <Image
        src="https://images.unsplash.com/photo-1596854407944-bf87f6fdd49e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80"
        alt="PostImg"
      />
      <Image
        src="https://images.unsplash.com/photo-1596854407944-bf87f6fdd49e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80"
        alt="PostImg"
      />
      <Image
        src="https://images.unsplash.com/photo-1596854407944-bf87f6fdd49e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80"
        alt="PostImg"
      />
      <Image
        src="https://images.unsplash.com/photo-1596854407944-bf87f6fdd49e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80"
        alt="PostImg"
      />
    </Grid>
  );
};

export const DippinFeed = () => {
  return (
    <Box>
      <Flex marginBottom="8px">
        <Grid templateColumns="repeat(8, 1fr)" gap={0.5}>
          <GridItem colSpan={1}>
            <Image
              src="https://images.unsplash.com/photo-1596854407944-bf87f6fdd49e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80"
              borderRadius="full"
              boxSize="36px"
              alt="ProfileImg"
            />
          </GridItem>
          <GridItem colSpan={5}>
            <Box>
              <Text>제목인듯</Text>
              <Text fontSize="8px">
                게시물 내용 대충 어쩌구 적으면 글자수가 이렇게 채워지겠지 그러면 두줄이
              </Text>
              <Flex fontSize="8px" marginRight="0" width="40px">
                <Box fontSize="8px" rowGap="2">
                  <i className="fa-light fa-comment">6</i>
                </Box>
                <Spacer />
                <Box fontSize="8px" rowGap="2">
                  <i className="fa-light fa-heart">12</i>
                </Box>
              </Flex>
            </Box>
          </GridItem>
          <GridItem colSpan={2}>
            <AspectRatio maxW="77px" ratio={1 / 1}>
              <Image
                src="https://images.unsplash.com/photo-1420161900862-9a86fa1f5c79?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjB8fG11c2ljJTIwYmFja2dyb3VuZHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"
                borderRadius="10%"
                boxSize="90%"
                alt="DippinImg"
              />
            </AspectRatio>
          </GridItem>
        </Grid>
      </Flex>
      <Flex marginBottom="8px">
        <Grid templateColumns="repeat(8, 1fr)" gap={0.5}>
          <GridItem colSpan={1}>
            <Image
              src="https://images.unsplash.com/photo-1596854407944-bf87f6fdd49e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80"
              borderRadius="full"
              boxSize="36px"
              alt="ProfileImg"
            />
          </GridItem>
          <GridItem colSpan={5}>
            <Box>
              <Text>제목인듯</Text>
              <Text fontSize="8px">
                게시물 내용 대충 어쩌구 적으면 글자수가 이렇게 채워지겠지 그러면 두줄이
              </Text>
              <Flex fontSize="8px" marginRight="0" width="40px">
                <Box fontSize="8px" rowGap="2">
                  <i className="fa-light fa-comment">6</i>
                </Box>
                <Spacer />
                <Box fontSize="8px" rowGap="2">
                  <i className="fa-light fa-heart">12</i>
                </Box>
              </Flex>
            </Box>
          </GridItem>
          <GridItem colSpan={2}>
            <AspectRatio maxW="77px" ratio={1 / 1}>
              <Image
                src="https://images.unsplash.com/photo-1420161900862-9a86fa1f5c79?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjB8fG11c2ljJTIwYmFja2dyb3VuZHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"
                borderRadius="10%"
                boxSize="90%"
                alt="DippinImg"
              />
            </AspectRatio>
          </GridItem>
        </Grid>
      </Flex>
      <Flex marginBottom="8px">
        <Grid templateColumns="repeat(8, 1fr)" gap={0.5}>
          <GridItem colSpan={1}>
            <Image
              src="https://images.unsplash.com/photo-1596854407944-bf87f6fdd49e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80"
              borderRadius="full"
              boxSize="36px"
              alt="ProfileImg"
            />
          </GridItem>
          <GridItem colSpan={5}>
            <Box>
              <Text>제목인듯</Text>
              <Text fontSize="8px">
                게시물 내용 대충 어쩌구 적으면 글자수가 이렇게 채워지겠지 그러면 두줄이
              </Text>
              <Flex fontSize="8px" marginRight="0" width="40px">
                <Box fontSize="8px" rowGap="2">
                  <i className="fa-light fa-comment">6</i>
                </Box>
                <Spacer />
                <Box fontSize="8px" rowGap="2">
                  <i className="fa-light fa-heart">12</i>
                </Box>
              </Flex>
            </Box>
          </GridItem>
          <GridItem colSpan={2}>
            <AspectRatio maxW="77px" ratio={1 / 1}>
              <Image
                src="https://images.unsplash.com/photo-1420161900862-9a86fa1f5c79?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjB8fG11c2ljJTIwYmFja2dyb3VuZHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"
                borderRadius="10%"
                boxSize="70%"
                alt="DippinImg"
              />
            </AspectRatio>
          </GridItem>
        </Grid>
      </Flex>
    </Box>
  );
};

export const UserShort = () => {
  return (
    <Box>
      <Flex marginBottom="16px" align="center">
        <Box alignItems="left" width="96px">
          <Image
            src="https://images.unsplash.com/photo-1596854407944-bf87f6fdd49e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80"
            borderRadius="full"
            htmlWidth="48px"
            htmlHeight="48px"
            alt="ProfileImg"
          />
        </Box>
        <Spacer />
        <Box width="100%">
          <Text>남이박</Text>
        </Box>
      </Flex>
      <Flex marginBottom="16px" align="center">
        <Box alignItems="left" width="96px">
          <Image
            src="https://bit.ly/3PXNy1o"
            borderRadius="full"
            htmlWidth="48px"
            htmlHeight="48px"
            alt="ProfileImg"
          />
        </Box>
        <Spacer />
        <Box width="100%">
          <Text>이박남</Text>
        </Box>
      </Flex>
      <Flex marginBottom="16px" align="center">
        <Box alignItems="left" width="96px">
          <Image
            src="https://bit.ly/3QdDcu6"
            borderRadius="full"
            htmlWidth="48px"
            htmlHeight="48px"
            alt="ProfileImg"
          />
        </Box>
        <Spacer />
        <Box width="100%">
          <Text>박남이</Text>
        </Box>
      </Flex>
    </Box>
  );
};

export const FeedAll = () => {
  return (
    <Tabs isFitted margin="0px">
      <TabList>
        <Tab padding="0">내 게시글</Tab>
        <Tab padding="0">내 디핑 게시글</Tab>
        <Tab padding="0">북마크 게시글</Tab>
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
  );
};
