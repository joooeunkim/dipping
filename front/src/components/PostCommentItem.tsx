import { Box, Avatar, Flex, Spacer } from '@chakra-ui/react';
import { useState } from 'react';
import { ModalNavBar } from './ModalNavBar';

export const PostCommentItem = (props: any) => {
  // const { user, comment } = props;

  // console.log('comment!');

  const user = {
    name: 'mocha_oca',
    profile_image: 'https://bit.ly/3A2BqqJ',
  };

  const comment = {
    content:
      'The Black Parade is the third studio album by American rock band My Chemical Romance. ' +
      'Released in Europe on October 20, 2006, through Reprise Records, it was produced by the band with ' +
      'Rob Cavallo, known for having produced multiple albums for the Goo Goo Dolls and Green Day. ',
    parent: null,
    last_modified: '2시간',
  };

  // 좋아요 토글
  const [like, toggleLike] = useState(0);
  const onClickLike = () => {
    toggleLike(like ^ 1);
  };

  return (
    <Box right="0vw" w="full" marginY="8px" bg="">
      <Box
        marginLeft={comment.parent ? '8vw' : '4vw'}
        marginRight="4vw"
        fontSize="14px"
        lineHeight="18px"
        bg=""
      >
        <Flex>
          <Avatar boxSize="32px" marginRight="2vw" name={user.name} src={user.profile_image} />

          <Box w="full" bg="">
            <Box display="inline" fontWeight="600">
              {user.name}
            </Box>
            <Box display="inline">&nbsp; {comment.content}</Box>
            <Box fontSize="12px" color="gray.500" marginY="8px">
              <Box display="inline">{comment.last_modified}</Box>
              &nbsp;&nbsp;
              <Box display="inline" fontWeight="600">
                답글
              </Box>
            </Box>
          </Box>
          <Box w="32px" h="32px" marginLeft="2vw" textAlign="center" bg="" onClick={onClickLike}>
            {like ? (
              <Box
                className="fa-solid fa-heart"
                fontSize="16px"
                lineHeight="30px"
                color="cyan.400"
              />
            ) : (
              <Box className="fa-regular fa-heart" fontSize="16px" lineHeight="30px" color="" />
            )}
          </Box>
        </Flex>
      </Box>
    </Box>
  );
};
