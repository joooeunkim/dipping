import { useEffect, useState } from 'react';
import {
  Input,
  Image,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalFooter,
  ModalBody,
  ModalCloseButton,
  Box,
  Flex,
  InputLeftElement,
  InputGroup,
  Center,
} from '@chakra-ui/react';
import axios from 'axios';

export const AddMusic = ({
  isOpen,
  onClose,
  setData,
}: {
  isOpen: boolean;
  onClose: () => void;
  setData: any;
}) => {
  const [input, setInput] = useState('');
  const [youtube, setYoutube] = useState([]);
  const [timer, setTimer] = useState(0); // 디바운싱 타이머

  // 모달이 닫힐 때 실행되는 것들
  const onCloseModal = () => {
    // 목록 비우기(다음에 입력할때를 위해)
    setYoutube([]);
    // 음악 정지
    (window as any).player.stopVideo();
    //
  };

  const onChangeDebounce = async (e: any) => {
    if (timer) {
      // timer 초기화
      clearTimeout(timer);
    }

    const newtimer = setTimeout(() => {
      console.log('sending api request..');
      sendApi(e.target.value);
    }, 800);

    setTimer(newtimer as any);
  };

  // 유튜브 api요청
  const sendApi = (text: string) => {
    console.log('with: ' + text);
    axios({
      method: 'get',
      url: 'https://www.googleapis.com/youtube/v3/search',
      params: {
        key: process.env.REACT_APP_YOUTUBE_API_KEY,
        part: 'snippet',
        max: 5,
        type: 'video',
        q: text + ' audio',
        fields: 'items(id(videoId),snippet(title,thumbnails(high(url)),channelTitle))',
      },
    }).then(res => {
      setYoutube(res.data.items);
    });
  };

  // 특수문자 변환
  const strProcess = (text: string) => {
    text = text.replaceAll('&quot;', '"');
    text = text.replaceAll('&amp;', '&');
    text = text.replaceAll('&#39;', "'");

    return text;
  };

  return (
    <Modal isOpen={isOpen} onClose={onClose} onCloseComplete={onCloseModal}>
      <ModalOverlay />
      <ModalContent borderRadius="10px">
        <InputGroup h="48px">
          <InputLeftElement
            h="48px"
            color="gray.300"
            fontSize="16px"
            children={<Box className="fa-regular fa-magnifying-glass" />}
          />
          <Input
            fontSize="16px"
            lineHeight="48px"
            placeholder="Basic usage"
            onChange={onChangeDebounce}
            variant="unstyled"
          />
        </InputGroup>

        <ModalBody padding="0">
          {youtube.map((item: any, index: number) => (
            <Box key={index}>
              {index === 0 && (
                <Box h="8px">
                  <hr />
                </Box>
              )}
              <Box position="relative" w="full" h="64px" bg="" marginY="16px">
                <Flex w="auto">
                  <Image
                    marginX="16px"
                    borderRadius="10px"
                    boxSize="64px"
                    objectFit="cover"
                    src={'https://i.ytimg.com/vi/' + item.id.videoId + '/maxresdefault.jpg'}
                    onClick={() => {
                      (window as any).player.loadVideoById({ videoId: item.id.videoId });
                    }}
                  />
                  <Center
                    h="64px"
                    w="auto"
                    lineHeight="18px"
                    fontSize="14px"
                    fontWeight="400"
                    marginRight="16px"
                    overflow="hidden"
                  >
                    {/* <Box>id: {item.id.videoId}</Box> */}
                    <Box w="full">{strProcess(item.snippet.title)}</Box>
                  </Center>
                </Flex>
              </Box>
              {index === youtube.length - 1 && <Box h="8px" />}
            </Box>
          ))}
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};
