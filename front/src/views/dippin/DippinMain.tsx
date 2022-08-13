import { Box, Input, Image, Flex, useDisclosure, Center, Spacer, Spinner } from '@chakra-ui/react';
import { useEffect, useRef, useState } from 'react';
import { DippinItem } from '../../components/dippin/DippinItem';
import { SearchNavBar } from '../../components/floatingbar/SearchNavBar';
import axios from 'axios';
import { AddMusic } from '../../components/AddMusic';
import { FeedPost, HomeFeedData, Music } from '../../types/HomeFeedData';
import { DippinMode } from '../../components/dippin/DippinMode';
import { time } from 'console';

export const DippinMain = () => {
  // 요청에 쓰일 파라미터들
  const [input, setInput] = useState('');
  const [mode, setMode] = useState('recent');
  const [page, setPage] = useState(0);

  // 화면에 표시할 리스트
  const [dippinlist, setDippinList] = useState<Array<FeedPost>>([]);
  const postfeeds = HomeFeedData; // 더미 데이터

  // 검색 창 입력
  const onKeyInput = (e: any) => {
    if (e.key === 'Enter') setInput(e.target.value);
  };

  // 검색어나 모드가 바뀌면 페이지 초기화
  useEffect(() => {
    console.log('input or mode changed');
    setPage(data => 0);
  }, [input, mode]);

  // 새 요청 받아서 리스트에 추가
  useEffect(() => {
    if (page === 0) {
      window.scrollTo(0, 0);
      setDippinList([]);
      return;
    }
    console.log('call getDippinPage: ' + input + '/' + mode + '/' + page);
    getDippinPage(input, mode, page);
  }, [page]);

  // 백엔드에 요청
  const getDippinPage = async (query: string, mode: string, page: number) => {
    setLoad(true); //로딩 시작

    // test code start
    const res = {
      data: [
        postfeeds[mode === 'recent' ? 0 : 1],
        postfeeds[mode === 'trend' ? 3 : 0],
        postfeeds[2],
        postfeeds[2],
        postfeeds[2],
        postfeeds[2],
        postfeeds[3],
        postfeeds[0],
      ],
      end: false,
    };
    // test code end

    // const res = await axios.get('url', {
    //   params: {
    //     page: page,
    //     mode: mode,
    //   },
    // });

    if (res.data) {
      if (res.end) endRef.current = true; //마지막 페이지일 경우
      setDippinList(data => [...data, ...res.data]);
      preventRef.current = true;
    }
    setLoad(false); //로딩 종료
  };

  // 무한 스크롤로 업데이트 하기 위한 코드 start
  const obsRef = useRef(null); // 옵저버
  const [load, setLoad] = useState(false); //로딩 스피너
  const preventRef = useRef(true); //옵저버 중복 실행 방지
  const endRef = useRef(false); //모든 글 로드 확인

  useEffect(() => {
    // 옵저버 생성
    const observer = new IntersectionObserver(obs, { threshold: 0.5 }); // IntersectionObserver
    if (obsRef.current) observer.observe(obsRef.current);
    return () => {
      observer.disconnect();
    };
  }, []);
  const obs = (entries: IntersectionObserverEntry[]) => {
    //옵저버 중복 실행 방지
    if (!endRef.current && entries[0].isIntersecting && preventRef.current) {
      preventRef.current = false; //옵저버 중복 실행 방지
      // 실행하고 싶은 것
      console.log('call setPage');
      setPage(data => data + 1);
    }
  };
  // 무한 스크롤로 업데이트 하기 위한 코드 end

  return (
    <Box>
      <SearchNavBar leftDisplay="none" rightDisplay="none" onKeyInput={onKeyInput} />
      <DippinMode mode={mode} setMode={setMode} />
      {dippinlist.map((item, index) => (
        <div key={index}>
          <DippinItem dippin={item} />
          <hr />
        </div>
      ))}
      {load && (
        <Box position="relative" w="full" h="100px">
          <Spinner
            h="20px"
            w="20px"
            position="absolute"
            top="20px"
            left="0"
            right="0"
            margin="auto"
          />
        </Box>
      )}
      <Box ref={obsRef} />
    </Box>
  );
};
