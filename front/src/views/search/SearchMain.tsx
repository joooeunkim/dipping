import { SearchNavBar } from '../../components/floatingbar/SearchNavBar';
import { useEffect, useState } from 'react';
import { Box, Link, Text, Button } from '@chakra-ui/react';
import { HomeFeed } from '../../components/FeedUserShort';

export const SearchMain = () => {
  const props = {
    leftDisplay: 'none',
    rightDisplay: 'none',
  };
  console.log('SearchMain');

  return (
    <Box>
      <Box>
        <SearchNavBar {...props} />
        <Link href="search/result">ㅇㅇㅇ</Link>
        <Text>추천 사용자</Text>
        <Text>대충 그림</Text>
        <Button>팔로우</Button>
      </Box>
      <Box marginTop="16px">
        <Text>추천 게시물</Text>
        <HomeFeed />
      </Box>
    </Box>
  );
};
