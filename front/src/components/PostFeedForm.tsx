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
  CloseButton,
} from '@chakra-ui/react';
import { SetStateAction, useEffect, useState } from 'react';
import { writePostFeed } from '../api/write';
import dippinReducer from '../reducers/dippinReducer';
import { AddMusic } from './AddMusic';
import { CyanButton } from './CyanButton';

//글 작성 폼에서 보여주는 음악 리스트 변수
let selectedMusicList: any[] = [];

// 입력 폼 컴포넌트
export const PostFeedForm = (props: any) => {
  let dippingMusicList = props.musicList;

  const { isOpen, onOpen, onClose } = useDisclosure();
  const [musicList, setMusicList] = useState<any[]>([]);

  const [openPost, setOpenPost] = useState(false);
  const [openComment, setOpenComment] = useState(false);
  const [mixAlbumArt, setmixAlbumArt] = useState(false);
  useEffect(() => {
    console.log('넘어온 음악리스트', dippingMusicList);

    let tempList = [];

    if (dippingMusicList) {
      for (let i = 0; i < dippingMusicList.length; i++) {
        tempList.push(refactorData(dippingMusicList[i]));
      }
    }
    setMusicList(tempList);
  }, [dippingMusicList]);

  const setData = (data: any) => {
    console.log('hi', data);
    selectedMusicList = [...musicList, refactorData(data)];
    setMusicList(selectedMusicList);
  };

  const refactorData = (data: any) => {
    return {
      songTitle: data.title,
      songSinger: data.artist,
      songUrl: data.id,
      songImgUrl: data.albumart,
    };
  };

  const newPost = (e: any) => {
    let content = document.getElementById('content') as HTMLInputElement | null;
    let tag = document.getElementById('tag') as HTMLInputElement | null;
    let userTag = document.getElementById('user_tag') as HTMLInputElement | null;
    let data = {
      post: {
        content: content?.value,
        openPost: openPost,
        openComment: openComment,
        mixAlbumArt: mixAlbumArt,
      },
      post_tag: createTag(tag),
      user_tag: createTag(userTag),
      play_list: musicList,
    };

    console.log(data);
    writePostFeed(data);
  };

  const createTag = (tag: any) => {
    let tagArray = tag?.value.replaceAll(' ', '').split('#');
    let tagObjectArry = [];
    for (let i = 0; i < tagArray.length; ++i) {
      if (tagArray[i] != '' && tagArray[i]) {
        tagObjectArry.push({
          content: tagArray[i],
        });
      }
    }
    return tagObjectArry;
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

      <Textarea id="content" rows={10} variant="unstyled" placeholder="본문 내용" />
      <Input id="tag" mb="2" variant="flushed" placeholder="태그" />
      <Input hidden id="user_tag" mb="2" variant="flushed" placeholder="사용자 태그" />
      <Box w="70%" m="32px auto">
        <Flex justifyContent="space-between" mb="4">
          팔로워에게만 공개
          <Switch onChange={() => setOpenPost(!openPost)} />
        </Flex>
        <Flex justifyContent="space-between" mb="4">
          댓글 잠금
          <Switch onChange={() => setOpenComment(!openComment)} />
        </Flex>
        <Flex
          display="none"
          justifyContent="space-between"
          onChange={() => setmixAlbumArt(!mixAlbumArt)}
        >
          앨범아트 믹스
          <Switch />
        </Flex>
      </Box>
      <Box
        onClick={e => {
          newPost(e);
          window.location.href = '/';
        }}
      >
        <CyanButton title="작성" />
      </Box>

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalBody>
            <AddMusic isOpen={isOpen} onClose={onClose} setData={setData} />
          </ModalBody>
        </ModalContent>
      </Modal>
    </Box>
  );
};
