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
              <Box display="inline">{comment.last_modified.substr(0, 10)}</Box>
              &nbsp;&nbsp;
              <Box display="inline" fontWeight="600">
                답글
              </Box>
            </Box>
          </Box>
        </Flex>
      </Box>
    </Box>
  );
};
