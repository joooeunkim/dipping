import {
  Box,
  Button,
  Center,
  Flex,
  Image,
  Input,
  List,
  ListItem,
  Modal,
  ModalBody,
  ModalContent,
  ModalOverlay,
  useDisclosure,
  Text,
  Textarea,
  Switch,
  Stack,
} from '@chakra-ui/react';
import { SetStateAction, useState } from 'react';
import { CyanButton } from './CyanButton';

//글 작성 폼에서 보여주는 음악 리스트 변수
let selectedMusicList: any[] = [];

// 검색어 입력시 하단에 나오는 검색결과 목록 아이템 컴포넌트
export const MusicItem = (props: any) => {
  // 게시물에 포함할 음악 아이템 추가 이벤트 핸들러
  const itemClickEventHandler = (setMusicState: any) => {
    selectedMusicList.push(props);
    setMusicState(selectedMusicList);
    console.log('click', selectedMusicList);
  };

  const onClose = props.onClose; // modal close hook
  const setMusicState = props.setMusicState;

  return (
    <ListItem
      onClick={() => {
        itemClickEventHandler(setMusicState);
        onClose();
      }}
    >
      <Flex pt="2" pb="2">
        <Image src="/logo192.png" w="12" h="12" mr="1" />
        <Box>
          <Text>{props.title}</Text>
          <Text>{props.artist}</Text>
        </Box>
      </Flex>
    </ListItem>
  );
};

// 입력 폼 컴포넌트
export const PostFeedForm = () => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [displayState, setDisplayState] = useState<string>('none');
  const [musicState, setMusicState] = useState<any[]>(selectedMusicList);

  // youtube 검색 api로 불러온 데이터 저장할 변수
  const musicList = [
    {
      imgUrl: 'imgUrl',
      title: '야생화',
      artist: '박효신',
    },
    {
      imgUrl: 'imgUrl',
      title: '추억은 사랑을 닮아',
      artist: '박효신',
    },
    {
      imgUrl: 'imgUrl',
      title: '1991년 찬바람이 불던 밤',
      artist: '박효신',
    },
  ];

  const inputChangeEventHandler = (e: any) => {
    if (e.target.value.length < 2) {
      setDisplayState('none');
    } else {
      setDisplayState('block');
    }
  };

  return (
    <Box>
      {/* form 메인부분 */}
      <Box overflowY="scroll" h="40">
        {/* 선택한 음악 리스트 표시하는 부분 */}
        {selectedMusicList.length != 0 ? (
          selectedMusicList.map((data, index) => (
            <Flex key={index}>
              <Image src="/logo192.png" w="12" h="12" />
              <Box>
                {data.title}
                {data.artist}
              </Box>
            </Flex>
          ))
        ) : (
          <Center mt="10" color="gray.500">
            등록된 음악이 없습니다.
          </Center>
        )}
      </Box>

      <Center>
        <Button
          onClick={onOpen}
          w="100%"
          variant="link"
          borderBottom="1px"
          borderColor="gray.200"
          borderRadius="none"
          p="2"
        >
          <i className="fa-regular fa-plus" />
        </Button>
      </Center>

      <Textarea rows={10} variant="unstyled" placeholder="본문 내용" />
      <Input mb="2" variant="flushed" placeholder="태그" />
      <Input mb="2" variant="flushed" placeholder="사용자 태그" />
      <Box w="70%" m="32px auto">
        <Flex justifyContent="space-between" mb="4">
          팔로워에게만 공개
          <Switch size="md" />
        </Flex>
        <Flex justifyContent="space-between" mb="4">
          댓글 잠금
          <Switch />
        </Flex>
        <Flex justifyContent="space-between">
          앨범아트 믹스
          <Switch />
        </Flex>
      </Box>

      <CyanButton title="작성" />

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalBody>
            <Flex>
              <Text fontSize="xl" pt="2" mr="1" color="gray.500">
                <i className="fa-regular fa-search"></i>
              </Text>

              <Input
                p="2"
                size="lg"
                variant="unstyled"
                placeholder="검색어 입력"
                onChange={inputChangeEventHandler}
              />
            </Flex>

            <Box>
              <List spacing={3} display={displayState} borderTop="1px" borderColor="gray.300">
                {musicList.map((data, index) => (
                  <MusicItem
                    key={index}
                    title={data.title}
                    imgUrl={data.imgUrl}
                    artist={data.artist}
                    onClose={onClose}
                    setMusicState={setMusicState}
                  />
                ))}
              </List>
            </Box>
          </ModalBody>
        </ModalContent>
      </Modal>
    </Box>
  );
};
