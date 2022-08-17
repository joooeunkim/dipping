import { Box, Spinner } from '@chakra-ui/react';
import { useRef, useState, useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { Outlet } from 'react-router-dom';
import { authAxios } from '../../api/common';
import { MainNavBar } from '../../components/floatingbar/MainNavBar';
import { PlaylistPost } from '../../components/postfeed/PlaylistPost';
import { setDefault } from '../../reducers/iframeReducer';
import { HomeFeedData, FeedPost } from '../../types/HomeFeedData';

export const HomeMain = () => {
  const dispatch = useDispatch();

  // 요청에 쓰일 파라미터
  const [page, setPage] = useState<number>(0);
  const [mode, setMode] = useState<string>('user');
  // 화면에 표시할 리스트
  const [followposts, setFollowPosts] = useState<FeedPost[]>([]);
  const [recommendposts, setRecommendPosts] = useState<FeedPost[]>([]);

  useEffect(() => {
    return () => {
      console.log('clear musicplay');
      dispatch(setDefault());
      (window as any).player.pauseVideo();
    };
  }, []);

  // 새 요청 받아서 리스트에 추가
  useEffect(() => {
    if (page === 0) {
      return;
    }
    getMainPage(mode, page);
  }, [page]);

  // function
  const getMainPage = async (mode: string, page: number) => {
    setLoad(true); //로딩 시작
    console.log('HomeMain: call getMainPage: ' + mode + '/' + page);
    const res: any = await authAxios.get('/board/' + mode, {
      params: {
        pageNum: page,
      },
    });
    console.log(res);

    if (res.data) {
      if (res.data.code === 201) {
        console.log('없음');
        if (mode === 'user') {
          setMode('recommend');
          setPage(0);
        } else if (mode === 'recommend') {
          endRef.current = true;
        }
      } else {
        const posts = res.data.data.posts;
        const list: FeedPost[] = posts.map((e: any) => {
          return {
            id: e.item.boardId,
            likes: e.item.likeCount,
            article: e.item.content,
            // 태그어딨어~~~
            // last_modified: e.item.updatedAt,
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
            commentCount: e.item.commentCount,
            myLike: e.item.myLike,
            albumArt: e.item.albumArt,
            openComment: e.item.openComment,
          };
        });

        if (mode === 'user') {
          setFollowPosts(data => [...data, ...list]);
        } else {
          setRecommendPosts(data => [...data, ...list]);
        }
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
      console.log('HomeMain: call setPage');
      setPage(num => num + 1);
    }
  };
  // 무한 스크롤로 업데이트 하기 위한 코드 end

  return (
    <Box>
      <MainNavBar />
      {followposts.length > 0 ? (
        followposts.map((item, index) => <PlaylistPost postfeed={item} id={index} key={index} />)
      ) : (
        <Box textAlign="center">{!load && '팔로잉 포스트가 없습니다.'}</Box>
      )}
      {mode === 'recommend' && (
        <Box
          paddingTop="40px"
          paddingBottom="8px"
          paddingX="24px"
          fontSize="24px"
          color="cyan.400"
          bg="rgba(222,222,222,0.1)"
          borderBottom="1px"
          borderColor="inherit"
        >
          추천 포스트
        </Box>
      )}
      {recommendposts.map((item, index) => (
        <PlaylistPost postfeed={item} id={index} key={index} />
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
