import {
  Container,
  Flex,
  Image,
  Box,
  Text,
  Input,
  Spacer,
  FormControl,
  FormLabel,
  Select,
  Link,
} from '@chakra-ui/react';
import { ModalNavBar } from '../../components/floatingbar/ModalNavBar';
import { SetStateAction, useState } from 'react';
import { Tag } from '../../components/Tag';

export const ProfileEdit = () => {
  const props = {
    title: '프로필 수정',
    leftElement: (
      <Image src="/logo_icon.png" alt="logo" objectFit="contain" h="32px" margin="2px" />
    ),
    rightElement: <Box className="fa-light fa-bars" lineHeight="36px" fontSize="24px" bg="" />,
  };
  const createTag = (e: any) => {
    let tagName: string = e.target.value;
    let code = e.code;
    const temp: any[] = [...tags, tagName];
    if (code === 'Enter') {
      if (e.nativeEvent.isComposing === false) {
        if (tags) {
          setTags(temp);
        } else {
          setTags([tagName]);
        }
        e.target.value = '';
      }
    }
  };

  const deleteTag = (value: string) => {
    let _tags = tags;

    _tags = _tags.filter((e: any) => {
      return e != value;
    });

    setTags(_tags);
  };

  const [tags, setTags] = useState<SetStateAction<any>>([]);

  return (
    <Box>
      <ModalNavBar {...props} />
      <Container maxW="480px" w="100%" bg="" h="100vh" margin="0 auto">
        <Flex flexDirection="column" alignItems="center" marginTop="40px" marginBottom="40px">
          <Box>
            <Image
              src="https://images.unsplash.com/photo-1596854407944-bf87f6fdd49e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80"
              borderRadius="full"
              htmlWidth="88px"
              htmlHeight="88px"
              alt="ProfileImg"
              marginBottom="16px"
            />
            <Text>사진 바꾸기</Text>
          </Box>
        </Flex>
        <Flex>
          <Box alignItems="left" marginTop="8px" width="96px">
            <Text>이름</Text>
          </Box>
          <Spacer />
          <Box width="100%">
            <Input variant="flushed" placeholder="기존이름" />
          </Box>
        </Flex>
        <Flex marginBottom="8px">
          <Box alignItems="left" marginTop="8px" width="96px">
            <Text>관심분야</Text>
          </Box>
          <Spacer />
          <Box width="100%">
            <Input variant="flushed" onKeyDown={e => createTag(e)} placeholder="#관심태그입력" />
          </Box>
        </Flex>
        <Box marginBottom="32px">
          {tags
            ? tags.map((text: any, index: any) => (
                <Tag key={index} text={text} deleteTag={deleteTag} />
              ))
            : ''}
        </Box>
        <Flex marginBottom="56px">
          <FormControl>
            <FormLabel marginBottom="8px">추천 리스트</FormLabel>
            <Select height="32px" placeholder="선택된 리스트">
              <option>1번 리스트</option>
              <option>2번 리스트</option>
            </Select>
          </FormControl>
        </Flex>
        <Flex>
          <Link href="edit/person">개인정보 변경</Link>
        </Flex>
      </Container>
    </Box>
  );
};
