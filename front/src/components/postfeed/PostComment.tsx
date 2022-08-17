import {
  Box,
  useDisclosure,
  Modal,
  ModalContent,
  ModalBody,
  Avatar,
  Flex,
  Input,
  InputGroup,
  useColorModeValue,
  InputRightElement,
  Drawer,
  DrawerBody,
  DrawerFooter,
  DrawerHeader,
  DrawerOverlay,
  DrawerContent,
  DrawerCloseButton,
} from '@chakra-ui/react';
import { useEffect, useState } from 'react';
import { authAxios } from '../../api/common';
import { Comment, FeedPost, User } from '../../types/HomeFeedData';
import { ModalNavBar } from '../floatingbar/ModalNavBar';
import { PostCommentItem } from './PostCommentItem';

export const PostComment = (props: any) => {
  const bg = useColorModeValue('white', 'gray.800');
  const color = useColorModeValue('gray.200', 'gray.600');
  const { isOpen, onOpen, onClose } = useDisclosure();

  const { user, article, id, last_modified } = props;
  const [comments, setComments] = useState<Array<Comment>>([]);

  useEffect(() => {
    console.log('PostComment: load id ' + id);
    getComments(id);
  }, [id]);

  // 백엔드에 요청
  const getComments = async (id: number) => {
    const res: any = await authAxios.get('/board/comment', {
      params: {
        boardId: id,
      },
    });
    console.log(res);

    const data = res.data.data.comment;
    const main: Comment[] = data.map((e: any) => {
      return {
        user: { name: 'name', profile_image: 'profile_image' } as User,
        comment: {
          content: e.content,
          last_modified: e.updatedAt,
          parent: e.parentId,
          id: e.commentId,
        },
      };
    });
    console.log(main);
    setComments(main);
  };

  return (
    <>
      <Box
        position="absolute"
        right="20vw"
        className="fa-regular fa-comment"
        fontSize="24px"
        lineHeight="30px"
        onClick={onOpen}
      />

      <Drawer trapFocus={false} placement="right" onClose={onClose} size="full" isOpen={isOpen}>
        <DrawerContent maxW="400px" h="full" padding="0">
          <ModalNavBar
            title="댓글"
            leftElement={
              <Box
                className="fa-light fa-angle-left"
                fontSize="28px"
                lineHeight="36px"
                bg=""
                onClick={onClose}
              />
            }
          />
          <DrawerBody padding="0">
            <Box h="48px" w="full" />

            <Box w="full" marginY="8px" bg="">
              <Box marginLeft="16px" marginRight="20px" fontSize="14px" lineHeight="18px" bg="">
                <Flex>
                  <Avatar
                    boxSize="32px"
                    marginRight="8px"
                    name={user.name}
                    src={user.profile_image}
                  />

                  <Box w="full" bg="">
                    <Box display="inline" fontWeight="600">
                      {user.name}
                    </Box>
                    <Box display="inline">&nbsp; {article}</Box>
                    <Box fontSize="12px" color="gray.500" marginY="8px">
                      <Box display="inline">{last_modified}</Box>
                    </Box>
                  </Box>
                </Flex>
              </Box>
            </Box>
            <hr />
            {comments.map((item: any, index: number) => (
              <PostCommentItem {...item} key={index} />
            ))}
          </DrawerBody>
          {/* input area */}
          <Box
            w="full"
            h="auto"
            borderTop="1px"
            borderColor={color}
            position="fixed"
            bottom="0px"
            zIndex="popover"
            bg={bg}
          >
            <Box fontSize="14px" marginX="16px" color="gray.500" display="none">
              <Box display="inline" fontWeight="600">
                {user.name}
              </Box>
              에게 답글 입력 중
            </Box>
            <Box position="relative" top="6px" h="auto" w="full" bg="" marginX="16px" marginY="4px">
              <Flex h="56px">
                <Avatar boxSize="32px" name={user.name} src={user.profile_image} />
                <Box flex="1" position="relative" top="2px" marginX="16px">
                  <InputGroup>
                    <InputRightElement
                      h="32px"
                      children={
                        <Box
                          fontSize="16px"
                          color="gray.400"
                          className="fa-regular fa-paper-plane-top"
                        />
                      }
                    />
                    <Input
                      h="32px"
                      variant="flushed"
                      type="text"
                      placeholder="검색어를 입력하세요."
                    />
                  </InputGroup>
                </Box>
                <Box w="8px" />
              </Flex>
            </Box>
          </Box>
        </DrawerContent>
      </Drawer>
    </>
  );
};
