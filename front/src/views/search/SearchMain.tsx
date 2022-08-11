import { SearchNavBar } from '../../components/floatingbar/SearchNavBar';
import { useEffect, useState } from 'react';
import { Box, Grid, Link, Text, Button, Image } from '@chakra-ui/react';
import { HomeFeed } from '../../components/FeedUserShort';

export const SearchMain = () => {
  const props = {
    leftDisplay: 'none',
    rightDisplay: 'none',
  };
  console.log('SearchMain');

  return (
    <Box>
      <SearchNavBar {...props} />
      <Link href="search/result">임시</Link>
      <Text marginLeft="8px" marginBottom="8px">
        추천 사용자
      </Text>
      <Grid templateColumns="repeat(5, 1fr)" gap={0.5} paddingLeft="8px">
        <Box textAlign="center" width="64px">
          <Image
            src="https://images.unsplash.com/photo-1596854407944-bf87f6fdd49e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80"
            borderRadius="full"
            boxSize="64px"
            alt="ProfileImg"
          />
          <Text fontSize="16px">nick1</Text>
          <Button bgColor="blue.300" color="white" width="64px" height="32px">
            팔로우
          </Button>
        </Box>
        <Box textAlign="center" width="64px">
          <Image src="https://bit.ly/3A2BqqJ" borderRadius="full" boxSize="64px" alt="ProfileImg" />
          <Text fontSize="16px">nick2</Text>
          <Button bgColor="blue.300" color="white" width="64px" height="32px">
            팔로우
          </Button>
        </Box>
        <Box textAlign="center" width="64px">
          <Image src="https://bit.ly/3PXNy1o" borderRadius="full" boxSize="64px" alt="ProfileImg" />
          <Text fontSize="16px">nick3</Text>
          <Button bgColor="blue.300" color="white" width="64px" height="32px">
            팔로우
          </Button>
        </Box>
        <Box textAlign="center" width="64px">
          <Image src="https://bit.ly/3QdDcu6" borderRadius="full" boxSize="64px" alt="ProfileImg" />
          <Text fontSize="16px">nick4</Text>
          <Button bgColor="blue.300" color="white" width="64px" height="32px">
            팔로우
          </Button>
        </Box>
        <Box textAlign="center" width="64px">
          <Image src="https://bit.ly/3bwSzPF" borderRadius="full" boxSize="64px" alt="ProfileImg" />
          <Text fontSize="16px">nick5</Text>
          <Button bgColor="blue.300" color="white" width="64px" height="32px">
            팔로우
          </Button>
        </Box>
      </Grid>
      <Box marginTop="32px">
        <Text marginLeft="8px" marginBottom="8px">
          추천 게시물
        </Text>
        <HomeFeed />
      </Box>
    </Box>
  );
};
