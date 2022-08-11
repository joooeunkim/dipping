import { Box } from '@chakra-ui/react';
import { useRef, useState } from 'react';
import { MainNavBar } from '../../components/floatingbar/MainNavBar';
import { PlaylistPost } from '../../components/postfeed/PlaylistPost';
import { HomeFeedData, FeedPost } from '../../testdata/HomeFeedData';

export const SearchDetail = () => {
  const [posts, setPosts] = useState<FeedPost[]>([]);
  const postfeeds = HomeFeedData;

  const boxRef = useRef<HTMLDivElement>(null);

  const getInfo = async () => {
    const newpost: { data: Array<FeedPost> } = {
      data: new Array(2),
    };
    setPosts(curPosts => [...curPosts, ...newpost.data]);
    console.log('추가!');
  };

  return (
    <Box height="100%">
      <MainNavBar />
      <PlaylistPost postfeed={postfeeds[0]} />
    </Box>
  );
};
