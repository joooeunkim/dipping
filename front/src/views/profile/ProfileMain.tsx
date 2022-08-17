import {
  Container,
  Image,
  Flex,
  Text,
  Box,
  Button,
  FormControl,
  FormLabel,
  Switch,
  Link,
  FormHelperText,
  Spacer,
  useControllableState,
  Avatar,
} from '@chakra-ui/react';
import { ModalNavBar } from '../../components/floatingbar/ModalNavBar';
import { FeedAll } from '../../components/FeedUserShort';
import { useEffect, useState } from 'react';
import { authAxios } from '../../api/common';
import { useLocation } from 'react-router-dom';

export const ProfileMain = () => {
  const [profile, setProfile] = useState<any>();
  const location = useLocation();
  const search = location.search.split('=')[1];
  console.log(search);
  useEffect(() => {
    authAxios.get(search ? '/profiles/?userNickname=' + search : '/profiles').then((res: any) => {
      setProfile(res.data.data.user);
      console.log(res);
    });
  }, []);

  const props = {
    title: '프로필',
    leftElement: (
      <Image src="/logo_icon.png" alt="logo" objectFit="contain" h="32px" margin="2px" />
    ),
    rightElement: (
      <Box
        className="fa-light fa-arrow-right-from-bracket"
        lineHeight="36px"
        fontSize="24px"
        bg=""
      />
    ),
  };

  return (
    <Box>
      <ModalNavBar {...props} />
      <Container maxW="480px" w="100%" bg="" h="100vh" margin="0 auto">
        <FormControl marginTop="16px" marginBottom="16px">
          <Flex marginRight="0px" paddingLeft="24px">
            <Box>
              <Avatar w="20" h="20" name={profile?.nickname} src={profile?.profileImgUrl} />
              <Text textAlign="center" fontSize="16px">
                {profile?.nickname}
              </Text>
            </Box>
            <Spacer />
            <Box w="200px">
              <Flex height="72px">
                <FormHelperText p="2" textAlign="center" fontSize="16px">
                  <Text>{profile?.boardCount}</Text>
                  <Text>게시물</Text>
                </FormHelperText>
                <Link href="/Follow">
                  <FormHelperText p="2" textAlign="center" fontSize="16px">
                    <Text>{profile?.followerCount}</Text>
                    <Text>팔로워</Text>
                  </FormHelperText>
                </Link>
                <Link href="/Follow">
                  <FormHelperText p="2" textAlign="center" fontSize="16px">
                    <Text>{profile?.followingCount}</Text>
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
            {profile?.musicTaste}
          </Text>
        </Flex>
        <Box marginTop="32px">
          <FeedAll />
        </Box>
      </Container>
    </Box>
  );
};
