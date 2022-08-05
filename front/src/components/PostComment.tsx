import { Box, useDisclosure, Modal, ModalContent, ModalBody, Avatar, Flex } from '@chakra-ui/react';
import { ModalNavBar } from './ModalNavBar';
import { PostCommentItem } from './PostCommentItem';

// 더미 데이터
const post = {
  user: {
    name: 'mocha_oca',
    profile_image: 'https://bit.ly/3A2BqqJ',
  },
  article:
    'The Black Parade is the third studio album by American rock band My Chemical Romance. ' +
    'Released in Europe on October 20, 2006, through Reprise Records, it was produced by the band with ' +
    'Rob Cavallo, known for having produced multiple albums for the Goo Goo Dolls and Green Day. ' +
    "It is a rock opera centering on a dying character with cancer known as 'The Patient'. " +
    'The album tells the story of his apparent death, experiences in the afterlife, and subsequent ' +
    "reflections on his life. It is the band's only studio album to feature drummer Bob Bryar before his departure in 2010.",
  last_modified: '1일',
};

const comments = [
  {
    user: {
      name: 'mocha_oca',
      profile_image: 'https://bit.ly/3A2BqqJ',
    },
    comment: {
      content: '',
      parent: null,
    },
  },
  {
    user: {
      name: 'mocha_oca',
      profile_image: 'https://bit.ly/3A2BqqJ',
    },
    comment: {
      content: '',
      parent: null,
    },
  },
  {
    user: {
      name: 'mocha_oca',
      profile_image: 'https://bit.ly/3A2BqqJ',
    },
    comment: {
      content: '',
      parent: 'something',
    },
  },
  {
    user: {
      name: 'mocha_oca',
      profile_image: 'https://bit.ly/3A2BqqJ',
    },
    comment: {
      content: '',
      parent: null,
    },
  },
];

export const PostComment = (props: any) => {
  const { isOpen, onOpen, onClose } = useDisclosure();

  // const { post, comments } = props;

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
                    name={post.user.name}
                    src={post.user.profile_image}
                  />

                  <Box w="full" bg="">
                    <Box display="inline" fontWeight="600">
                      {post.user.name}
                    </Box>
                    <Box display="inline">&nbsp; {post.article}</Box>
                    <Box fontSize="12px" color="gray.500" marginY="8px">
                      <Box display="inline">{post.last_modified}</Box>
                    </Box>
                  </Box>
                </Flex>
              </Box>
            </Box>
            <hr />
            <PostCommentItem />
            <PostCommentItem />
            <PostCommentItem />
            <PostCommentItem />
            <PostCommentItem />
            <Box h="48px" w="full" />
          </ModalBody>
        </ModalContent>
      </Modal>
    </>
  );
};
