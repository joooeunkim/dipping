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
  useDisclosure,
  DrawerOverlay,
  DrawerContent,
  DrawerCloseButton,
  DrawerBody,
  Drawer,
} from '@chakra-ui/react';
import { useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ModalNavBar } from './floatingbar/ModalNavBar';
import { PlaylistPost } from './postfeed/PlaylistPost';

export const HomeFeed = (props: any) => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [postContent, setPostContent] = useState<any>();

  const navigate = useNavigate();

  console.log(props.post);
  return (
    <Grid templateColumns="repeat(3, 1fr)" gap={0.5}>
      {props.post
        ? props.post.map((e: any, index: number) => (
            <Box
              id={e.id}
              key={index}
              onClick={e => {
                navigate(`/post/${e.currentTarget.id}`);
              }}
            >
              <Image
                id={e.id}
                alt={e.id}
                src={e.songImgUrl}
                w="30vw"
                h="30vw"
                maxH="148px"
                objectFit="cover"
              />
            </Box>
          ))
        : ''}
      <Drawer isOpen={isOpen} placement="right" onClose={onClose}>
        <DrawerOverlay />
        <DrawerContent maxW="100vw" h="full">
          <ModalNavBar
            title="포스트"
            leftElement={
              <Box
                className="fa-light fa-angle-left"
                fontSize="28px"
                lineHeight="36px"
                bg=""
                onClick={onClose}
              />
            }
          />
          <DrawerBody>
            <Box h="48px" w="full" />
          </DrawerBody>
        </DrawerContent>
      </Drawer>
    </Grid>
  );
};

export const DippinFeed = (props: any) => {
  // console.log('profile dipping', props.dipping);
  const dipping = props.dipping;
  const navigate = useNavigate();

  return (
    <Box>
      {dipping
        ? dipping.map((e: any, index: number) => (
            <Flex
              id={e.id}
              key={index}
              ml="4"
              mr="4"
              mb="2"
              onClick={() => {
                navigate('/dippin/' + e.id);
              }}
            >
              <Box minW="75px" minH="75px" w="20vw" h="20vw" maxW="148px" maxH="148px" mr="2">
                <Image
                  src={e.songImgUrl}
                  borderRadius="10%"
                  alt="DippinImg"
                  w="100%"
                  h="100%"
                  objectFit="cover"
                />
              </Box>

              <Box>
                <Text lineHeight="8" fontSize="lg" maxW="220px">
                  {e.title}
                </Text>
              </Box>
            </Flex>
          ))
        : ''}
    </Box>
  );
};

export const UserShort = (props: any) => {
  const collection = props.collection;
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

export const FeedAll = (props: any) => {
  console.log('feedAll content', props.content);
  return (
    <Tabs isFitted margin="0px">
      <TabList>
        <Tab padding="0">내 게시글</Tab>
        <Tab padding="0">내 디핑 게시글</Tab>
      </TabList>
      <TabPanels>
        <TabPanel paddingLeft="0" paddingRight="0">
          <HomeFeed post={props.content?.post} />
        </TabPanel>
        <TabPanel paddingLeft="0" paddingRight="0">
          <DippinFeed dipping={props.content?.dipping} />
        </TabPanel>
      </TabPanels>
    </Tabs>
  );
};
