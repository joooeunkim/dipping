import { useEffect, useState } from 'react';
import {
  Input,
  Image as ChakraImage,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalBody,
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
  const [youtube, setYoutube] = useState([]);
  const [lastfm, setLastFm] = useState([]);
  const [timer, setTimer] = useState(0); // 디바운싱 타이머

  // 모달이 닫힐 때 실행되는 것들
  const onCloseModal = () => {
    // 목록 비우기(다음에 입력할때를 위해)
    setYoutube([]);
    setLastFm([]);
    // 음악 정지
    (window as any).player.stopVideo();
    //
  };

  // 고화질 썸네일이 없을 경우 후처리
  function getMeta(id: string, image: any) {
    let img = new Image();
    img.onload = ({ target }) => {
      console.log('onload:' + (target as any).width + ' ' + (target as any).height);
      if ((target as any).height < 100)
        image.src = 'https://i.ytimg.com/vi/' + id + '/hqdefault.jpg';
    };
    img.src = image.src;
  }

  // 디바운스 함수
  const onChangeDebounce = async (e: any) => {
    if (timer)
      // timer 초기화
      clearTimeout(timer);

    // 초기화가 없을 경우 800ms 후 api 요청 실행
    const newtimer = setTimeout(() => {
      if (e.target.value.length < 3) {
        setYoutube([]);
        setLastFm([]);
      } else sendApi(e.target.value);
    }, 800);

    setTimer(newtimer as any);
  };

  // 외부 api요청
  const sendApi = (text: string) => {
    console.log('input:' + text);
    // 유튜브
    axios({
      method: 'get',
      url: 'https://www.googleapis.com/youtube/v3/search',
      params: {
        key: process.env.REACT_APP_YOUTUBE_API_KEY,
        part: 'snippet',
        max: 5,
        q: text + ' audio',
        fields: 'items(id(videoId),snippet(title,thumbnails(high(url)),channelTitle))',
      },
    }).then(res => {
      setYoutube(res.data.items);
    });

    // Last.fm
    axios
      .get('https://ws.audioscrobbler.com/2.0/', {
        params: {
          format: 'json',
          api_key: process.env.REACT_APP_LASTFM_API_KEY,
          method: 'track.search',
          track: text,
          limit: 1,
        },
      })
      .then(res => {
        const track = res.data.results.trackmatches.track;
        setLastFm(track);
      });
  };

  // 특수문자 변환
  const strProcess = (text: string) => {
    text = text.replaceAll('&quot;', '"');
    text = text.replaceAll('&amp;', '&');
    text = text.replaceAll('&#39;', "'");

    return text;
  };

  // 컴포넌트
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
          {lastfm.map((item: any, index: number) => (
            <Box position="relative" w="full" key={index}>
              <hr />
              <Flex
                w="auto"
                h="64px"
                marginY="16px"
                lineHeight="32px"
                fontSize="14px"
                fontWeight="400"
              >
                <Box marginX="16px" boxSize="64px" fontWeight="600">
                  <Box w="full">Artist</Box>
                  <Box w="full">Title</Box>
                </Box>
                <Box>
                  <Box w="full">{item.artist}</Box>
                  <Box w="full">{item.name}</Box>
                </Box>
              </Flex>
            </Box>
          ))}
          {youtube.map((item: any, index: number) => (
            <Box key={index}>
              {index === 0 && (
                <Box h="8px">
                  <hr />
                </Box>
              )}
              <Box position="relative" w="full" h="64px" bg="" marginY="16px">
                <Flex w="auto">
                  <ChakraImage
                    marginX="16px"
                    borderRadius="10px"
                    boxSize="64px"
                    objectFit="cover"
                    src={'https://i.ytimg.com/vi/' + item.id.videoId + '/maxresdefault.jpg'}
                    onClick={() => {
                      (window as any).player.loadVideoById({ videoId: item.id.videoId });
                    }}
                    onLoad={({ target }) => {
                      getMeta(item.id.videoId, target);
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
