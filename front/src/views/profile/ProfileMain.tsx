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
  useColorModeValue,
  Spinner,
  Center,
} from '@chakra-ui/react';
import { ModalNavBar } from '../../components/floatingbar/ModalNavBar';
import { FeedAll } from '../../components/FeedUserShort';
import { useEffect, useState } from 'react';
import { authAxios } from '../../api/common';
import { useLocation, useNavigate } from 'react-router-dom';
import { CyanButton } from '../../components/CyanButton';
import { parseJwt } from '../../api/login/local';

export const ProfileMain = () => {
  const [profile, setProfile] = useState<any>();
  const [content, setContent] = useState<any>();
  const [isFollow, setIsFollow] = useState<any>();
  const [load, setLoad] = useState<boolean>(true);
  const location = useLocation();
  const navigate = useNavigate();
  const buttonColor = useColorModeValue('cyan.400', 'cyan.500');
  const search = location.search.split('=')[1];
  console.log(search);
  useEffect(() => {
    authAxios.get(search ? '/profiles/?userNickname=' + search : '/profiles').then((res: any) => {
      setProfile(res.data.data.user);
      setIsFollow(res.data.data.user.isFollowed);
      setContent({
        post: res.data.data.post,
        dipping: res.data.data.dipping,
        collection: res.data.data.collection,
      });
      console.log(res);
      setLoad(false);
    });
  }, []);

  const logout = () => {
    localStorage.removeItem('accessToken');
    window.location.href = '/';
  };

  const props = {
    title: '프로필',
    leftElement: (
      <Image src="/logo_icon.png" alt="logo" objectFit="contain" h="32px" margin="2px" />
    ),
    rightElement: (
      <Box
        onClick={() => {
          if (window.confirm('로그아웃 하시겠습니까?')) {
            logout();
          }
        }}
        className="fa-light fa-arrow-right-from-bracket"
        lineHeight="36px"
        fontSize="24px"
        bg=""
      />
    ),
  };

  useEffect(() => {
    console.log('여긴 뭔가 변하면 항상 실행?');
  });

  const changeFollow = (e: any) => {
    console.log(e.currentTarget.id);
    const receiver = e.currentTarget.id;
    const sender = parseJwt(localStorage.getItem('accessToken'))?.nickname;
    console.log(sender, receiver);
    authAxios
      .post('/follow', {
        senderNickname: sender,
        receiverNickname: receiver,
      })
      .then(res => {
        console.log(res);
        setIsFollow(!isFollow);
      });
  };

  return (
    <Box>
      <ModalNavBar {...props} />
      {load ? (
        <Center>
          <Spinner />
        </Center>
      ) : (
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
                    <Text>{profile?.boardCount + content?.dipping.length}</Text>
                    <Text>게시물</Text>
                  </FormHelperText>
                  <FormHelperText
                    p="2"
                    textAlign="center"
                    fontSize="16px"
                    onClick={() => navigate('/follow?nickname=' + profile?.nickname)}
                  >
                    <Text>{profile?.followerCount}</Text>
                    <Text>팔로워</Text>
                  </FormHelperText>
                  <FormHelperText
                    p="2"
                    textAlign="center"
                    fontSize="16px"
                    onClick={() => navigate('/follow?nickname=' + profile?.nickname)}
                  >
                    <Text>{profile?.followingCount}</Text>
                    <Text>팔로잉</Text>
                  </FormHelperText>
                </Flex>
                {!search ? (
                  <Button
                    colorScheme="gray.200"
                    variant="outline"
                    width="87%"
                    onClick={() => navigate('/profile/edit', { state: profile?.profileImgUrl })}
                  >
                    프로필 수정
                  </Button>
                ) : (
                  <Box>
                    {isFollow ? (
                      <Button
                        size="sm"
                        w="87%"
                        id={profile?.nickname}
                        borderColor={buttonColor}
                        variant="outline"
                        _hover={{
                          color: 'white',
                        }}
                        _active={{
                          borderColor: 'cyan.500',
                        }}
                        onClick={changeFollow}
                      >
                        언팔로우
                      </Button>
                    ) : (
                      <Button
                        size="sm"
                        w="87%"
                        id={profile?.nickname}
                        bg={buttonColor}
                        _hover={{
                          bg: 'cyan.500',
                        }}
                        _active={{
                          bg: 'cyan.500',
                        }}
                        onClick={changeFollow}
                      >
                        팔로우
                      </Button>
                    )}
                  </Box>
                )}
              </Box>
            </Flex>
          </FormControl>
          <Flex marginLeft="24px" marginBottom="32px">
            <Text fontSize="16px" color="gray.500">
              {profile?.musicTaste}
            </Text>
          </Flex>
          <Box marginTop="32px">
            <FeedAll content={content} />
          </Box>
        </Container>
      )}
    </Box>
  );
};
