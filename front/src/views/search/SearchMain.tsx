import { SearchNavBar } from '../../components/floatingbar/SearchNavBar';
import { useEffect, useRef, useState } from 'react';
import { Box, Avatar, Grid, Image, GridItem } from '@chakra-ui/react';
import { HomeFeed } from '../../components/FeedUserShort';
import { useDispatch, useSelector } from 'react-redux';
import {
  addPage,
  setInput,
  setMode,
  setPage,
  setPostList,
  setUserList,
} from '../../reducers/searchReducer';
import { FeedPost, User } from '../../types/HomeFeedData';
import { authAxios } from '../../api/common';
import { SearchMode } from '../../components/search/SearchMode';
import { Link } from 'react-router-dom';

export const SearchMain = () => {
  const dispatch = useDispatch();
  // 요청에 쓰일 파라미터들
  const input = useSelector((state: any) => state.searchReducer.input);
  const mode = useSelector((state: any) => state.searchReducer.mode);
  const page = useSelector((state: any) => state.searchReducer.page);

  // 화면에 표시할 리스트
  const userlist: User[] = useSelector((state: any) => state.searchReducer.userlist);
  const postlist: [] = useSelector((state: any) => state.searchReducer.postlist);
  const dippinlist: FeedPost[] = useSelector((state: any) => state.searchReducer.dippinlist);

  // 검색 창 입력
  const onKeyInput = (e: any) => {
    if (e.key === 'Enter') dispatch(setInput(e.target.value));
  };

  // 검색어나 모드가 바뀌면 페이지 초기화
  useEffect(() => {
    console.log('SearchMain: input or mode changed');
    window.scrollTo(0, 0);
    endRef.current = false;
    dispatch(setPage(0));

    if (mode === 'user') getUserList(input, page);
    if (mode === 'post') getPostList(input, page);
  }, [dispatch, input, mode]);

  // 새 요청 받아서 리스트에 추가
  useEffect(() => {
    if (page === 0) {
      return;
    }
    // get list by mode
  }, [page]);

  // 무한 스크롤로 업데이트 하기 위한 코드 start
  const obsRef = useRef(null); // 옵저버
  const [load, setLoad] = useState(false); //로딩 스피너
  const preventRef = useRef(true); //옵저버 중복 실행 방지
  const endRef = useRef(false); //모든 글 로드 확인

  // 백엔드에 요청
  const getPostList = async (query: string, page: number) => {
    setLoad(true); //로딩 시작
    console.log('SearchMain: call getPostList: ' + query + '/' + page);
    const res: any = await authAxios.get('/search/post', {
      params: {
        keyword: query,
      },
    });
    setLoad(false); //로딩 종료
    const posts = res.data.data.posts
      .map((e: any) => {
        return {
          id: e.id,
          albumart: e.songImgUrl,
        };
      })
      .sort((a: any, b: any) => b.id - a.id);
    dispatch(setPostList(posts));
  };
  const getUserList = async (query: string, page: number) => {
    setLoad(true); //로딩 시작
    console.log('SearchMain: call getUserList: ' + query + '/' + page);
    const res: any = await authAxios.get('/search/user', {
      params: {
        keyword: query,
      },
    });
    setLoad(false); //로딩 종료
    const users = res.data.data.users.map((e: any) => {
      return {
        id: e.id,
        name: e.nickname,
        profile_image: e.profileImgUrl,
      };
    });
    dispatch(setUserList(users));
  };

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

  return (
    <Box>
      <SearchNavBar leftDisplay="none" rightDisplay="none" onKeyInput={onKeyInput} />
      <SearchMode />
      <Box marginTop="16px">
        {mode === 'user' ? (
          userlist.map((item, index) => (
            <Link key={index} to={'/profile/?nickname=' + item.name}>
              <Box
                position="relative"
                h="64px"
                w="full"
                fontSize="16px"
                fontWeight="300"
                lineHeight="40px"
                paddingX="24px"
                paddingY="8px"
              >
                <Avatar
                  marginRight="16px"
                  boxSize="40px"
                  name={item.name}
                  src={item.profile_image}
                />
                {item.name}
              </Box>
            </Link>
          ))
        ) : (
          <Grid templateColumns="repeat(3, 1fr)" gap={0.5}>
            {postlist.map((item, index) => (
              <Link key={index} to={'/post/' + (item as any).id}>
                <Image boxSize="33vw" src={(item as any).albumart} objectFit="cover" />
              </Link>
            ))}
          </Grid>
        )}
      </Box>
    </Box>
  );
};
