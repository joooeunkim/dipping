import { Box, Drawer, DrawerBody, DrawerContent } from '@chakra-ui/react';
import { useEffect, useState } from 'react';
import { DippinItem } from './DippinItem';
import { ModalNavBar } from '../floatingbar/ModalNavBar';
import { FeedPost, HomeFeedData } from '../../types/HomeFeedData';
import { DippinPost } from './DippinPost';
import { useDispatch } from 'react-redux';
import { setDefault } from '../../reducers/iframeReducer';
import { DippinPostSmall } from './DippinPostSmall';

export const DippinDetail = ({
  isOpen,
  onClose,
  dippinid,
  setDippinId,
}: {
  isOpen: boolean;
  onClose: () => void;
  dippinid: number;
  setDippinId: any;
}) => {
  const dispatch = useDispatch();

  // 화면에 표시할 게시물
  const [dippin, setDippin] = useState<FeedPost>();
  const [dippinlist, setDippinList] = useState<Array<FeedPost>>([]);
  const postfeeds = HomeFeedData; // 더미 데이터

  // 닫힐 때 실행되는 것들
  const onCloseDrawer = () => {
    // 음악 정지
    console.log('clear musicplay');
    dispatch(setDefault());
    (window as any).player.stopVideo();
    // 초기화
    setDippinId(0);
  };

  // 첫 렌더링 시 서버에 요청
  useEffect(() => {
    if (dippinid === 0) return;
    console.log('DippinDetail: load id ' + dippinid);
    getDippinDetail(dippinid);
  }, [dippinid]);

  // 백엔드에 요청
  const getDippinDetail = async (id: number) => {
    // test code start
    const res = {
      data: {
        main: postfeeds[id - 1],
        comments: [
          postfeeds[id % 4],
          postfeeds[(id + 1) % 4],
          postfeeds[(id + 2) % 4],
          postfeeds[(id + 3) % 4],
          postfeeds[(id + 4) % 4],
          postfeeds[(id + 5) % 4],
          postfeeds[(id + 6) % 4],
        ],
      },
    };
    // test code end

    // const res = await axios.get('url', {
    //   params: {
    //     id:id
    //   },
    // });

    setDippin(res.data.main);
    setDippinList(res.data.comments);
  };

  return (
    <Drawer isOpen={isOpen} onClose={onClose} size="md" onCloseComplete={onCloseDrawer}>
      <DrawerContent>
        <ModalNavBar
          title={'디핑' + dippinid}
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
          <DippinPost dippin={dippin} id={0} />
          {dippinlist.map((item, index) => (
            <div key={index}>
              <DippinPostSmall dippin={item} id={index + 1} />
              <hr />
            </div>
          ))}
        </DrawerBody>
      </DrawerContent>
    </Drawer>
  );
};
