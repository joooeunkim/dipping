import { Box, Avatar, Flex, Spacer } from '@chakra-ui/react';
import { useEffect, useState } from 'react';
import { ModalNavBar } from '../floatingbar/ModalNavBar';

export const PostCommentItem = (props: any) => {
  const { user, comment } = props;

  // console.log('comment!');

  // 좋아요 토글
  const [like, toggleLike] = useState(comment.userlike);
  const onClickLike = () => {
    toggleLike(like ^ 1);
  };

  // useEffect(() => {});

  return (
    <Box w="full" marginY="8px" bg="">
      <Box
        marginLeft={comment.parent ? '48px' : '16px'}
        marginRight="16px"
        fontSize="14px"
        lineHeight="18px"
        bg=""
      >
        <Flex>
          <Avatar boxSize="32px" marginRight="8px" name={user.name} src={user.profile_image} />

          <Box w="full" bg="">
            <Box display="inline" fontWeight="600">
              {user.name}
            </Box>
            <Box display="inline">&nbsp; {comment.content}</Box>
            <Box fontSize="12px" color="gray.500" marginY="8px">
              {comment.likes > 0 ? (
                <Box display="inline">좋아요 {comment.likes} 개 &nbsp;&nbsp;</Box>
              ) : (
                <Box />
              )}
              <Box display="inline">{comment.last_modified}</Box>
              &nbsp;&nbsp;
              <Box display="inline" fontWeight="600">
                답글
              </Box>
            </Box>
          </Box>
          <Box w="32px" h="32px" marginLeft="8px" textAlign="center" bg="" onClick={onClickLike}>
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
