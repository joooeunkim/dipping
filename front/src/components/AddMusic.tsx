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
import { Music } from '../types/HomeFeedData';
import axios from 'axios';
import { useSelector } from 'react-redux';
import {
  PlayerState,
  setPlayList,
  setPlayListIndex,
  setPlayState,
  setPostID,
} from '../reducers/iframeReducer';
export const AddMusic = ({
  isOpen,
  onClose,
  setData,
}: {
  isOpen: boolean;
  onClose: () => void;
  setData: any;
}) => {
  const [youtube, setYoutube] = useState<Array<Music>>([]);
  const [lastfm, setLastFm] = useState([]);
  const [timer, setTimer] = useState(0); // 디바운싱 타이머
  const playstate = useSelector((state: any) => state.iframeReducer.playstate);

  // 모달이 닫힐 때 실행되는 것들
  const onCloseModal = () => {
    // 목록 비우기(다음에 입력할때를 위해)
    console.log('closemodal');
    setYoutube([]);
    setLastFm([]);
    // 음악 정지
    (window as any).player.stopVideo();
    //
  };

  // 곡 선택
  const chooseMusic = (index: number) => {
    console.log(index);
    console.log(youtube[index]);
    console.log((lastfm[0] as any).artist);
    console.log((lastfm[0] as any).name);

    setData({
      ...youtube[index],
      artist: (lastfm[0] as any).artist,
      title: (lastfm[0] as any).name,
    });

    // setData((data: any) => [
    //   ...data,
    //   {
    //     ...youtube[index],
    //     artist: (lastfm[0] as any).artist,
    //     title: (lastfm[0] as any).name,
    //   } as Music,
    // ]);
    onClose();
  };

  // 고화질 썸네일이 없을 경우 전처리
  const checkMaxRes = (id: string, image: any) => {
    let img = new Image();
    img.onload = () => {
      if (img.height < 100)
        setYoutube(data =>
          data.map(it =>
            it.id === id
              ? { ...it, albumart: 'https://i.ytimg.com/vi/' + id + '/hqdefault.jpg' }
              : it,
          ),
        );
    };
    img.src = image.src;
  };

  // 특수문자 변환
  const strProcess = (text: string) => {
    text = text.replaceAll('&quot;', '"').replaceAll('&amp;', '&').replaceAll('&#39;', "'");
    return text;
  };

  // 디바운스 함수
  const onChangeDebounce = async (e: any) => {
    if (timer) clearTimeout(timer); // timer 초기화

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
      const items = res.data.items.map((item: any) => {
        return {
          title: strProcess(item.snippet.title),
          albumart: 'https://i.ytimg.com/vi/' + item.id.videoId + '/maxresdefault.jpg',
          id: item.id.videoId,
        } as Music;
      });
      setYoutube(items);
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

  // 컴포넌트
  return (
    <Modal isOpen={isOpen} onClose={onClose} onCloseComplete={onCloseModal}>
      <ModalOverlay />
      <ModalContent borderRadius="10px">
        {/* 검색창 */}
        <InputGroup h="48px" fontSize="16px">
          <InputLeftElement
            h="48px"
            color="gray.300"
            children={<Box className="fa-regular fa-magnifying-glass" />}
          />
          <Input
            lineHeight="48px"
            placeholder="노래제목 검색"
            onChange={onChangeDebounce}
            variant="unstyled"
          />
        </InputGroup>

        {/* 음악 제목, 가수(lastfm) */}
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
                <Box marginLeft="24px" w="36px" fontWeight="600">
                  <Box w="full">Artist</Box>
                  <Box w="full">Title</Box>
                </Box>
                <Box marginLeft="12px">
                  <Box w="full">{item.artist}</Box>
                  <Box w="full">{item.name}</Box>
                </Box>
              </Flex>
            </Box>
          ))}

          {/* 유튜브 검색 결과 */}
          {youtube.map((item: Music, index: number) => (
            <Box key={index}>
              {index === 0 && (
                <Box h="8px">
                  <hr />
                </Box>
              )}
              <Box position="relative" w="full" h="64px" bg="" marginY="16px">
                <Flex w="auto">
                  {/* {playstate === PlayerState.PLAYING ? (
                    <Box
                      position="absolute"
                      left="38px"
                      top="4"
                      className="fa-solid fa-pause"
                      fontSize="30px"
                      color="cyan.400"
                      lineHeight="30px"
                      onClick={() => {
                        (window as any).player.pauseVideo();
                      }}
                    />
                  ) : (
                    <Box
                      position="absolute"
                      left="38px"
                      top="4"
                      className="fa-solid fa-play"
                      fontSize="30px"
                      color="cyan.400"
                      lineHeight="30px"
                      onClick={() => {
                        (window as any).player.loadVideoById({ videoId: item.id });
                      }}
                      onLoad={({ target }) => {
                        checkMaxRes(item.id, target);
                      }}
                    />
                  )} */}
                  <ChakraImage
                    marginX="16px"
                    borderRadius="10px"
                    boxSize="64px"
                    objectFit="cover"
                    src={item.albumart}
                    onClick={() => {
                      (window as any).player.loadVideoById({ videoId: item.id });
                    }}
                    onLoad={({ target }) => {
                      checkMaxRes(item.id, target);
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
                    onClick={() => {
                      chooseMusic(index);
                    }}
                  >
                    <Box w="full">{item.title}</Box>
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
