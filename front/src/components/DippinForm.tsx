import {
  Box,
  Button,
  Center,
  Flex,
  Image,
  Input,
  Modal,
  ModalBody,
  ModalContent,
  ModalOverlay,
  useDisclosure,
  Text,
  Textarea,
  Switch,
  CloseButton,
} from '@chakra-ui/react';
import { useState } from 'react';
import { writeDipping } from '../api/write';
import { AddMusic } from './AddMusic';
import { CyanButton } from './CyanButton';

//글 작성 폼에서 보여주는 음악 리스트 변수
let selectedMusicList: any[] = [];

// 입력 폼 컴포넌트
export const DippinForm = (props: any) => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [musicList, setMusicList] = useState<any[]>([]);

  const [openPost, setOpenPost] = useState(false);

  const parent = props.parent;
  console.log(parent);

  const setData = (data: any) => {
    console.log('hi', data);
    selectedMusicList = [...musicList, refactorData(data)];
    setMusicList(selectedMusicList);
    console.log('musiclist', musicList);
  };

  const refactorData = (data: any) => {
    return {
      songTitle: data.title,
      songSinger: data.artist,
      songUrl: data.id,
      songImgUrl: data.albumart,
    };
  };

  const newDippin = (e: any) => {
    let title = document.getElementById('title') as HTMLInputElement | null;
    let content = document.getElementById('content') as HTMLInputElement | null;
    let data = {
      dipping: parent
        ? {
            dippingTitle: title?.value,
            dippingContent: content?.value,
            parentId: parent,
            openDipping: true,
          }
        : {
            dippingTitle: title?.value,
            dippingContent: content?.value,
            openDipping: true,
          },
      playlist: musicList,
    };
    console.log(data);
    writeDipping(data);
  };

  return (
    <Box>
      {/* form 메인부분 */}
      <Box overflowY="scroll" h="40">
        {/* 선택한 음악 리스트 표시하는 부분 */}
        {musicList.length != 0 ? (
          musicList.map((music, index) => {
            console.log(music);
            return (
              <Flex key={index} mb="2">
                <Image src={music.songImgUrl} w="12" h="12" borderRadius="lg" />
                <Box pl="2" pt="1" w="100%">
                  <Text fontSize="sm" color="gray.500">
                    {music.songSinger}
                  </Text>
                  <Text lineHeight="4">{music.songTitle}</Text>
                </Box>
                <CloseButton mt="2" />
              </Flex>
            );
          })
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
      <Input id="title" mt="4" variant="flushed" placeholder="제목" />
      <Textarea id="content" rows={14} variant="unstyled" placeholder="본문 내용" />
      <Box w="70%" m="32px auto">
        <Flex justifyContent="space-between" mb="4">
          팔로워에게만 공개
          <Switch onChange={() => setOpenPost(!openPost)} />
        </Flex>
      </Box>
      <Box onClick={newDippin}>
        <CyanButton title="작성" />
      </Box>

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalBody>
            <AddMusic isOpen={isOpen} onClose={onClose} setData={setData} />
            {/* <Flex>
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
            </Box> */}
          </ModalBody>
        </ModalContent>
      </Modal>
    </Box>
  );
};
