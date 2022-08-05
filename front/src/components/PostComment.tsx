import { Box, useDisclosure, Modal, ModalContent, ModalBody, Avatar, Flex } from '@chakra-ui/react';
import { ModalNavBar } from './ModalNavBar';
import { PostCommentItem } from './PostCommentItem';

export const PostComment = (props: any) => {
  const { isOpen, onOpen, onClose } = useDisclosure();

  const { user, article, last_modified, comments } = props;

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

      <Modal scrollBehavior="inside" onClose={onClose} size="full" isOpen={isOpen}>
        <ModalContent position="fixed" top="0px">
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
          <ModalBody padding="0">
            <Box h="48px" w="full" />

            <Box right="0vw" w="full" marginY="8px" bg="">
              <Box marginLeft="4vw" marginRight="6vw" fontSize="14px" lineHeight="18px" bg="">
                <Flex>
                  <Avatar
                    boxSize="32px"
                    marginRight="2vw"
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
            <Box h="48px" w="full" />
          </ModalBody>
        </ModalContent>
      </Modal>
    </>
  );
};
