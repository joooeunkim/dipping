import { Box, Container, Flex, Tabs, TabList, Tab, TabPanels, TabPanel } from '@chakra-ui/react';
import { HomeFeed, DippinFeed, UserShort } from '../../components/FeedUserShort';
import { SearchNavBar } from '../../components/floatingbar/SearchNavBar';

export const SearchResult = () => {
  const props = {
    leftDisplay: 'none',
    rightDisplay: 'none',
  };
  console.log('SearchMain');

  return (
    <Box height="100%">
      <SearchNavBar {...props} />
      <Container maxW="480px" w="100%" bg="" h="100vh" margin="0 auto">
        <Flex flexDirection="column" alignItems="center" marginTop="16px">
          <Tabs isFitted margin="0px" width="100%">
            <TabList marginBottom="16px">
              <Tab fontSize="16px">홈피드</Tab>
              <Tab fontSize="16px">사용자</Tab>
              <Tab fontSize="16px">디핑</Tab>
            </TabList>
            <TabPanels>
              <TabPanel padding="0px">
                <Flex marginBottom="8px">
                  <HomeFeed />
                </Flex>
              </TabPanel>
              <TabPanel padding="0px">
                <UserShort />
              </TabPanel>
              <TabPanel padding="0px">
                <DippinFeed />
              </TabPanel>
            </TabPanels>
          </Tabs>
        </Flex>
      </Container>
    </Box>
  );
};
