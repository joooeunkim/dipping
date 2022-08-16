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
  useControllableProp,
  useControllableState,
} from '@chakra-ui/react';
import { ModalNavBar } from '../../components/floatingbar/ModalNavBar';
import { FeedAll } from '../../components/FeedUserShort';
import { useState } from 'react';

export const ProfileMain = () => {
  const props = {
    title: '프로필',
    leftElement: (
      <Image src="/logo_icon.png" alt="logo" objectFit="contain" h="32px" margin="2px" />
    ),
    rightElement: <Box className="fa-light fa-bars" lineHeight="36px" fontSize="24px" bg="" />,
  };
  const [show, setShow] = useState(false);
  const [internalShow, setInternalShow] = useControllableState({
    onChange: setShow,
  });
  const onClick = () => setInternalShow((currentShow: any) => !currentShow);

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
          <Button
            w="25%"
            height="24px"
            bg="cyan.400"
            // size="lg"
            color="white"
            _hover={{
              bg: 'cyan.500',
            }}
            _active={{
              bg: 'cyan.500',
            }}
            onClick={onClick}
          >
            <FormLabel htmlFor="email-alerts" mb="0" fontSize="10px" textAlign="end">
              <i className="fa-thin fa-lock"> 게시글숨기기</i>
            </FormLabel>
          </Button>
        </FormControl>
        <Box marginTop="32px">{show ? <FeedAll /> : null}</Box>
      </Container>
    </Box>
  );
};
