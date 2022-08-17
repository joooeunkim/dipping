import { Box, Input, Image, Flex, useDisclosure, Center, Spacer, Spinner } from '@chakra-ui/react';
import { useEffect, useRef, useState } from 'react';
import { DippinMainItem } from '../../components/dippin/DippinMainItem';
import { SearchNavBar } from '../../components/floatingbar/SearchNavBar';
import axios from 'axios';
import { FeedPost, HomeFeedData, Music } from '../../types/HomeFeedData';
import { DippinMode } from '../../components/dippin/DippinMode';
import { DippinDetail } from './DippinDetail';
import { authAxios } from '../../api/common';
import { iteratorSymbol } from 'immer/dist/internal';
import { useDispatch, useSelector } from 'react-redux';
import {
  addPage,
  pushDippinList,
  setDippinList,
  setInput,
  setPage,
} from '../../reducers/dippinReducer';
import { Link } from 'react-router-dom';

export const DippinMain = () => {
  const dispatch = useDispatch();

  // 요청에 쓰일 파라미터들
  const input = useSelector((state: any) => state.dippinReducer.input);
  const mode = useSelector((state: any) => state.dippinReducer.mode);
  const page = useSelector((state: any) => state.dippinReducer.page);

  // 화면에 표시할 리스트
  const dippinlist: FeedPost[] = useSelector((state: any) => state.dippinReducer.dippinlist);

  // 검색 창 입력
  const onKeyInput = (e: any) => {
    if (e.key === 'Enter') dispatch(setInput(e.target.value));
  };

  // 검색어나 모드가 바뀌면 페이지 초기화
  useEffect(() => {
    console.log('DippinMain: input or mode changed');
    window.scrollTo(0, 0);
    endRef.current = false;
    dispatch(setPage(0));
    getDippinPage(input, mode, 0);
  }, [dispatch, input, mode]);

  // 새 요청 받아서 리스트에 추가
  useEffect(() => {
    if (page === 0) {
      return;
    }
    getDippinPage(input, mode, page);
  }, [page]);

  // 백엔드에 요청
  const getDippinPage = async (query: string, mode: string, page: number) => {
    setLoad(true); //로딩 시작
    console.log('DippinMain: call getDippinPage: ' + query + '/' + mode + '/' + page);
    const res: any = await authAxios.get('/dipping', {
      params: {
        sort: mode,
        pageNum: page + 1,
        search: query,
      },
    });
    console.log(res);

    if (res.data) {
      if (res.data.code === 201) {
        console.log('없음');
        endRef.current = true; //마지막 페이지일 경우
        if (page === 0) dispatch(setDippinList([]));
      } else {
        const posts = res.data.data.posts;
        const list: FeedPost[] = posts.map((e: any) => {
          return {
            id: e.item.dippingId,
            title: e.item.dippingTitle,
            likes: e.item.likeCount,
            article: e.item.dippingContent,
            last_modified: e.item.updatedAt,
            user: {
              name: e.item.nickname,
              profile_image: e.item.userId,
            },
            playlist: e.music.map((el: any) => {
              return {
                title: el.songTitle,
                artist: el.songSinger,
                albumart: el.songImgUrl,
                id: el.songUrl,
              };
            }),
            commentCount: e.item.childCount,
          };
        });

        if (page === 0) dispatch(setDippinList(list));
        else dispatch(pushDippinList(list));
      }
      preventRef.current = true;
    }
    console.log('요청 완료');
    setLoad(false); //로딩 종료
  };

  // 무한 스크롤로 업데이트 하기 위한 코드 start
  const obsRef = useRef(null); // 옵저버
  const [load, setLoad] = useState(false); //로딩 스피너
  const preventRef = useRef(true); //옵저버 중복 실행 방지
  const endRef = useRef(false); //모든 글 로드 확인

  useEffect(() => {
    // 옵저버 생성
    console.log('create observer');
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
      console.log('DippinMain: call setPage');
      dispatch(addPage());
    }
  };
  // 무한 스크롤로 업데이트 하기 위한 코드 end

  // const { isOpen, onOpen, onClose } = useDisclosure();

  // const [dippinid, setDippinId] = useState(0);
  // useEffect(() => {
  //   console.log('DippinMain: set dippinid ' + dippinid);
  //   if (dippinid > 0) {
  //     onOpen();
  //   }
  // }, [dippinid]);

  return (
    <Box>
      <SearchNavBar leftDisplay="none" rightDisplay="none" onKeyInput={onKeyInput} />
      <DippinMode />
      {dippinlist.length > 0 ? (
        dippinlist.map((item, index) => (
          <Link key={index} to={'/dippin/' + item.id}>
            <DippinMainItem dippin={item} />
            <hr />
          </Link>
        ))
      ) : (
        <Box textAlign="center">{!load && '컨텐츠가 없습니다.'}</Box>
      )}
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
