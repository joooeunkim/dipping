import {
  Container,
  Flex,
  Image,
  Box,
  Tabs,
  TabList,
  Tab,
  TabPanels,
  TabPanel,
  Text,
  Spacer,
} from '@chakra-ui/react';
import { ModalNavBar } from '../../components/ModalNavBar';
import { AutoSearch } from '../../components/AutoSearch';

export const FollowList = () => {
  const props = {
    title: '개인정보 수정',
    leftElement: (
      <Image src="/logo_icon.png" alt="logo" objectFit="contain" h="32px" margin="2px" />
    ),
    rightElement: <Box className="fa-light fa-bars" lineHeight="36px" fontSize="24px" bg="" />,
  };

  return (
    <Box>
      <ModalNavBar {...props} />
      <Container maxW="480px" w="100%" bg="" h="100vh" margin="0 auto">
        <Flex flexDirection="column" alignItems="center" marginTop="16px">
          <Tabs isFitted margin="0px" width="100%">
            <TabList marginBottom="16px">
              <Tab fontSize="16px">팔로워</Tab>
              <Tab fontSize="16px">팔로잉</Tab>
            </TabList>
            <AutoSearch />
            <TabPanels>
              <TabPanel>
                <Flex marginBottom="8px">
                  <Box alignItems="left" marginTop="8px" width="96px">
                    <Image
                      src="https://images.unsplash.com/photo-1596854407944-bf87f6fdd49e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80"
                      borderRadius="full"
                      htmlWidth="48px"
                      htmlHeight="48px"
                      alt="ProfileImg"
                    />
                  </Box>
                  <Spacer />
                  <Box width="100%" paddingTop="20px">
                    <Text>남이박</Text>
                  </Box>
                </Flex>
                <Flex marginBottom="8px">
                  <Box alignItems="left" marginTop="8px" width="96px">
                    <Image
                      src="https://bit.ly/3PXNy1o"
                      borderRadius="full"
                      htmlWidth="48px"
                      htmlHeight="48px"
                      alt="ProfileImg"
                    />
                  </Box>
                  <Spacer />
                  <Box width="100%" paddingTop="20px">
                    <Text>이박남</Text>
                  </Box>
                </Flex>
                <Flex marginBottom="8px">
                  <Box alignItems="left" marginTop="8px" width="96px">
                    <Image
                      src="https://bit.ly/3QdDcu6"
                      borderRadius="full"
                      htmlWidth="48px"
                      htmlHeight="48px"
                      alt="ProfileImg"
                    />
                  </Box>
                  <Spacer />
                  <Box width="100%" paddingTop="20px">
                    <Text>박남이</Text>
                  </Box>
                </Flex>
              </TabPanel>
              <TabPanel>
                <Text>dd</Text>
              </TabPanel>
            </TabPanels>
          </Tabs>
        </Flex>
      </Container>
    </Box>
  );
};
