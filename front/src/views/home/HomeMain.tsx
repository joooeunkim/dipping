import { Box, Spinner, useDisclosure } from '@chakra-ui/react';
import { useRef, useState, useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { Outlet } from 'react-router-dom';
import { authAxios } from '../../api/common';
import { MainNavBar } from '../../components/floatingbar/MainNavBar';
import { PlaylistPost } from '../../components/postfeed/PlaylistPost';
import { PostComment } from '../../components/postfeed/PostComment';
import { setDefault } from '../../reducers/iframeReducer';
import { HomeFeedData, FeedPost } from '../../types/HomeFeedData';

export const HomeMain = () => {
  const dispatch = useDispatch();

  // 요청에 쓰일 파라미터
  const [pagemode, setPageMode] = useState<any>([0, 'user']);
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
    getMainPage(pagemode[0], pagemode[1]);
  }, [pagemode]);

  // function
  const getMainPage = async (page: number, mode: string) => {
    if (page === 0) return;
    setLoad(true); //로딩 시작
    console.log('HomeMain: call getMainPage: ' + mode + '/' + page);
    const res: any = await authAxios.get('/board/' + mode, {
      params: {
        pageNum: page,
      },
    });
    if (res.data) {
      if (res.data.code === 201) {
        console.log('없음');
        if (mode === 'user') {
          console.log('setmode recommend ' + mode + '/' + page);
          setPageMode([1, 'recommend']);
          //setPage(1);
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
            tags: '#' + e.tag.join(' #'),
            last_modified: e.item.updatedAt,
            user: {
              name: e.item.nickname,
              profile_image: e.item.profileImgUrl,
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
      setPageMode((data: any) => [data[0] + 1, data[1]]);
    }
  };
  // 무한 스크롤로 업데이트 하기 위한 코드 end

  // 댓글 drawer 제어
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [commentinfo, setCommentInfo] = useState<any>();

  return (
    <Box>
      <MainNavBar />
      <PostComment commentinfo={commentinfo} isOpen={isOpen} onClose={onClose} />
      {followposts.length > 0 ? (
        followposts.map((item, index) => (
          <PlaylistPost
            postfeed={item}
            id={index}
            key={index}
            setCommentInfo={setCommentInfo}
            onOpen={onOpen}
          />
        ))
      ) : (
        <Box paddingY="24px" textAlign="center" bg="rgba(222,222,222,0.1)">
          {!load && '팔로잉 포스트가 없습니다.'}
        </Box>
      )}
      {pagemode[1] === 'recommend' && (
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
        <PlaylistPost
          key={index}
          postfeed={item}
          id={index + followposts.length}
          setCommentInfo={setCommentInfo}
          onOpen={onOpen}
        />
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
