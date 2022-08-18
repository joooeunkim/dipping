import { Box, Button, Drawer, DrawerBody, DrawerContent, useDisclosure } from '@chakra-ui/react';
import { useEffect, useState } from 'react';
import { ModalNavBar } from '../../components/floatingbar/ModalNavBar';
import { FeedPost, HomeFeedData, Music } from '../../types/HomeFeedData';
import { useDispatch, useSelector } from 'react-redux';
import { setDefault } from '../../reducers/iframeReducer';
import { authAxios } from '../../api/common';
import { useNavigate, useParams } from 'react-router-dom';
import { PlaylistPost } from '../../components/postfeed/PlaylistPost';
import { PostComment } from '../../components/postfeed/PostComment';

export const HomeDetail = () => {
  const postid: number = parseInt(useParams().postid as string);
  const dispatch = useDispatch();

  // 화면에 표시할 게시물
  const [post, setPost] = useState<FeedPost>();
  const [postlist, setPostList] = useState<Array<FeedPost>>([]);

  // 닫힐 때 실행되는 것들
  useEffect(() => {
    return () => {
      console.log('clear musicplay');
      dispatch(setDefault());
      (window as any).player.pauseVideo();
    };
  }, []);

  const navigate = useNavigate();
  const onCloseDrawer = () => {
    // 음악 정지
    console.log('clear musicplay');
    dispatch(setDefault());
    (window as any).player.stopVideo();
    // 이전 페이지로
    navigate(-1);
  };

  // 첫 렌더링 시 서버에 요청
  useEffect(() => {
    if (postid === 0) return;
    dispatch(setDefault());
    console.log('HomeDetail: load id ' + postid);
    getPostDetail(postid);
  }, [postid]);

  // 백엔드에 요청
  const getPostDetail = async (id: number) => {
    const res: any = await authAxios.get('/board/board', {
      params: {
        boardId: id,
      },
    });

    const data = res.data.data.post;
    const main = {
      id: data.item.boardId,
      likes: data.item.likeCount,
      article: data.item.content,
      tags: '#' + data.tag.join(' #'),
      last_modified: data.item.updatedAt,
      user: {
        name: data.item.nickname,
        profile_image: data.item.userId,
      },
      playlist: data.music.map((el: any) => {
        return {
          title: el.songTitle,
          artist: el.songSinger,
          albumart: el.songImgUrl,
          id: el.songUrl,
        };
      }),
      myLike: data.item.myLike,
      openComment: data.item.openComment,
    };

    console.log(main);
    setPost(main as any);
  };

  // 댓글 drawer 제어
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [commentinfo, setCommentInfo] = useState<any>();

  return (
    <Box>
      <ModalNavBar
        title="포스트"
        leftElement={
          <Box
            className="fa-light fa-angle-left"
            fontSize="28px"
            lineHeight="36px"
            bg=""
            onClick={onCloseDrawer}
          />
        }
      />
      <Box padding="0">
        {post && (
          <>
            <PostComment commentinfo={commentinfo} isOpen={isOpen} onClose={onClose} />
            <PlaylistPost postfeed={post} id={1} setCommentInfo={setCommentInfo} onOpen={onOpen} />
          </>
        )}
      </Box>
    </Box>
  );
};
