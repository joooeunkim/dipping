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
  Avatar,
} from '@chakra-ui/react';
import { ModalNavBar } from '../../components/floatingbar/ModalNavBar';
import { AutoSearch } from '../../components/AutoSearch';
import { useEffect, useState } from 'react';
import { authAxios } from '../../api/common';
import { useLocation, useNavigate } from 'react-router-dom';

export const FollowList = () => {
  const [followUsers, setFolowUsers] = useState<any>();
  const locataion = useLocation();
  const nickName = locataion.search.split('=')[1];
  const navigate = useNavigate();

  useEffect(() => {
    authAxios.get('follow?nickname=' + nickName).then(res => {
      setFolowUsers(res.data.data);
    });
  }, []);
  const props = {
    title: '팔로우 정보',
    leftElement: (
      <Box
        className="fa-light fa-angle-left"
        fontSize="28px"
        lineHeight="36px"
        bg=""
        onClick={() => navigate(-1)}
      />
    ),
  };
  console.log(followUsers);
  return (
    <Box>
      <ModalNavBar {...props} />
      <Container maxW="480px" w="100%" bg="" h="100vh" margin="0 auto">
        <Flex flexDirection="column" alignItems="center" marginTop="16px">
          <Tabs isFitted margin="0px" width="100%">
            <TabList mb="2">
              <Tab fontSize="16px">팔로워 ({followUsers?.followers.length})</Tab>
              <Tab fontSize="16px">팔로잉 ({followUsers?.followings.length})</Tab>
            </TabList>
            <TabPanels>
              <TabPanel>
                {followUsers?.followers.map((item: any, index: number) => (
                  <Flex
                    key={index}
                    marginBottom="8px"
                    onClick={() => navigate('/profile?nickname=' + item?.nickname)}
                  >
                    <Box alignItems="left" marginTop="8px" width="96px">
                      <Avatar src={item?.profileImgUrl} name={item?.nickname} />
                    </Box>
                    <Spacer />
                    <Box width="100%" paddingTop="20px">
                      <Text>{item.nickname}</Text>
                    </Box>
                  </Flex>
                ))}
              </TabPanel>
              <TabPanel>
                {followUsers?.followings.map((item: any, index: number) => (
                  <Flex
                    key={index}
                    marginBottom="8px"
                    onClick={() => navigate('/profile?nickname=' + item?.nickname)}
                  >
                    <Box alignItems="left" marginTop="8px" width="96px">
                      <Avatar src={item?.profileImgUrl} name={item?.nickname} />
                    </Box>
                    <Spacer />
                    <Box width="100%" paddingTop="20px">
                      <Text>{item.nickname}</Text>
                    </Box>
                  </Flex>
                ))}
              </TabPanel>
            </TabPanels>
          </Tabs>
        </Flex>
      </Container>
    </Box>
  );
};
