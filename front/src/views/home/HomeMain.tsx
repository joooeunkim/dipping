import { Box, Spinner } from '@chakra-ui/react';
import { useRef, useState, useEffect } from 'react';
import { Outlet } from 'react-router-dom';
import { MainNavBar } from '../../components/MainNavBar';
import { PlaylistPost } from '../../components/PlaylistPost';

type Post = {
  title: string;
  likes: string;
  article: string;
  tags: string;
  user: {
    name: string;
    profile_image: string;
  };
  playlists: {
    title: string;
    artist: string;
    albumart: string;
  }[];
};

export const HomeMain = () => {
  // const posts = [<PlaylistPost />, <PlaylistPost />];
  const [posts, setPosts] = useState<Post[]>([]);

  // ref
  const observerRef = useRef<IntersectionObserver>();
  const boxRef = useRef<HTMLDivElement>(null);

  // // useEffect
  // useEffect(() => {
  //   // 렌더링마다 새로 데이터 가져오기
  //   getInfo();
  // }, []);
  console.log('렌더링!');
  useEffect(() => {
    observerRef.current = new IntersectionObserver(intersectionObserver); // IntersectionObserver
    boxRef.current && observerRef.current.observe(boxRef.current);
  }, [posts]);

  // function
  const getInfo = async () => {
    // const res = await axios.get('http://localhost:8080/rest/getInfo'); // 서버에서 데이터 가져오기
    // setPosts(curPosts => [...curPosts, ...res.data]); // state에 추가

    // 더미 데이터
    const newpost: { data: Array<Post> } = {
      // data: [
      //   {
      //     article: '',
      //     likes: '',
      //     tags: '',
      //     title: '',
      //     playlists: [
      //       {
      //         title: '',
      //         albumart: '',
      //         artist: '',
      //       },
      //     ],
      //     user: { name: '', profile_image: '' },
      //   },
      // ],
      data: new Array(2),
    };
    setPosts(curPosts => [...curPosts, ...newpost.data]); // state에 추가
    console.log('추가!');
  };

  // IntersectionObserver 설정
  const intersectionObserver = (entries: IntersectionObserverEntry[], io: IntersectionObserver) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        // 관찰하고 있는 entry가 화면에 보여지는 경우
        io.unobserve(entry.target); // entry 관찰 해제
        getInfo(); // 데이터 가져오기
      }
    });
  };
  return (
    <Box>
      <MainNavBar />
      {posts.map((item, index) => (
        <PlaylistPost key={index} />
      ))}
      <Box position="relative" w="full" h="300px">
        <Spinner
          h="20px"
          position="absolute"
          top="0"
          bottom="0"
          left="0"
          right="0"
          margin="auto"
          ref={boxRef}
        />
      </Box>
    </Box>
  );
};
