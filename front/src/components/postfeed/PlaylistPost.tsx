import { Box, useColorModeValue, Image, Avatar } from '@chakra-ui/react';
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { authAxios } from '../../api/common';
import { parseJwt } from '../../api/login/local';
import { FeedPost, User } from '../../types/HomeFeedData';
import { PlayerLarge } from './PlayerLarge';
import { PostComment } from './PostComment';

export const PlaylistPost = (props: any) => {
  const bgColor = useColorModeValue('white', 'gray.800');
  const borderColor = useColorModeValue('gray.200', 'gray.600');

  const { postfeed, id, setCommentInfo, onOpen } = props;
  // 본문 더보기
  const [limit, setLimit] = useState(95);
  const toggleEllipsis = (str: string, limit: number) => {
    return {
      string: str.slice(0, limit),
      isShowMore: str.length > limit,
    };
  };
  const onClickMore = (str: string) => () => {
    setLimit(str.length);
  };

  const [mylike, setMyLike] = useState(postfeed.myLike);
  const [likecount, setLikeCount] = useState(postfeed.likes);

  const toggleLike = async () => {
    setLikeCount((likes: number) => (mylike ? likes - 1 : likes + 1));
    setMyLike((mylike: boolean) => !mylike);

    authAxios
      .post('/board/like', {
        postLike: {
          boardId: postfeed.id,
        },
      })
      .then(res => console.log(res))
      .catch(err => console.log(err));
  };

  const handleCopyClipBoard = async (text: string) => {
    navigator.clipboard
      .writeText(text)
      .then(() => {
        alert('클립보드에 복사되었습니다.');
      })
      .catch(() => {
        alert('복사를 다시 시도해주세요.');
      });
  };

  return (
    <Box position="relative" h="" w="full" borderBottom="1px" borderColor={borderColor} bg="">
      {/* top bar */}
      <Box marginY="4px" position="relative" h="44px" w="full" bg="">
        <Box
          position="absolute"
          left="4vw"
          top="6px"
          fontSize="16px"
          fontWeight="300"
          lineHeight="32px"
          bg=""
        >
          <Link to={'/profile/?nickname=' + postfeed.user.name}>
            <Avatar
              marginRight="1vw"
              boxSize="32px"
              name={postfeed.user.name}
              src={postfeed.user.profile_image}
            />
            {postfeed.user.name}
          </Link>
        </Box>
        {postfeed.user.name === parseJwt(localStorage.getItem('accessToken')).nickname && (
          <Box
            className="fa-light fa-eraser"
            position="absolute"
            right="4vw"
            top="6px"
            fontSize="24px"
            lineHeight="32px"
            onClick={() => {
              if (window.confirm('정말 삭제하시겠습니까?')) {
                console.log('삭제');
                authAxios.delete('/board?boardId=' + postfeed.id);
                window.history.back();
              }
            }}
          />
        )}
      </Box>

      {/* music player */}
      {/* <PlayerLarge /> */}
      <PlayerLarge playlist={postfeed.playlist} id={id} />

      {/* article set */}
      <Box position="relative" h="" w="full" bg="" marginBottom="16px">
        <Box position="relative" marginX="4vw" fontWeight="400" fontSize="14px" lineHeight="18px">
          {toggleEllipsis(postfeed.article, limit).string}
          {toggleEllipsis(postfeed.article, limit).isShowMore && '...'}
          {toggleEllipsis(postfeed.article, limit).isShowMore && (
            <Box
              position="absolute"
              bottom="0"
              right="0"
              color="gray.400"
              onClick={onClickMore(postfeed.article)}
            >
              더보기
            </Box>
          )}
        </Box>
        <Box
          position="relative"
          marginX="4vw"
          marginY="8px"
          fontWeight="400"
          fontSize="14px"
          lineHeight="18px"
          color="gray.500"
        >
          {postfeed.tags}
        </Box>
      </Box>

      {/* icon set */}
      <Box position="relative" h="30px" w="full" bg="" marginBottom="16px">
        {mylike ? (
          <Box
            position="absolute"
            left="4vw"
            className="fa-solid fa-heart"
            fontSize="24px"
            lineHeight="30px"
            color="cyan.400"
            onClick={toggleLike}
          />
        ) : (
          <Box
            position="absolute"
            left="4vw"
            className="fa-regular fa-heart"
            fontSize="24px"
            lineHeight="30px"
            onClick={toggleLike}
          />
        )}

        <Box position="absolute" left="12vw" fontSize="24px" lineHeight="30px">
          {likecount}
        </Box>
        {/* {postfeed.user.name === parseJwt(localStorage.getItem('accessToken')).nickname && (
          <Box
            position="absolute"
            right="4vw"
            className="fa-regular fa-share-nodes"
            fontSize="24px"
            lineHeight="30px"
          />
        )} */}
        {postfeed.openComment && (
          <Box
            position="absolute"
            right="12vw"
            className="fa-regular fa-comment"
            fontSize="24px"
            lineHeight="30px"
            onClick={() => {
              onOpen();
              const info = {
                id: (postfeed as FeedPost).id,
                name: postfeed.user.name,
                profile_image: postfeed.user.profile_image,
                article: postfeed.article,
                last_modified: postfeed.last_modified,
              };
              setCommentInfo(info);
            }}
          />
        )}
        <Box
          position="absolute"
          right="4vw"
          className="fa-regular fa-share-nodes"
          fontSize="24px"
          lineHeight="30px"
          onClick={() =>
            handleCopyClipBoard('http://i7b210.p.ssafy.io/post/' + (postfeed as FeedPost).id)
          }
        />
        {/* <Box
          position="absolute"
          right="4vw"
          className="fa-solid fa-bookmark"
          fontSize="24px"
          lineHeight="30px"
          color="cyan.400"
        /> */}
      </Box>
    </Box>
  );
};
