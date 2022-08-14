import { useEffect, useState } from 'react';
import { FeedPost, HomeFeedData } from '../../types/HomeFeedData';

export const DippinDetail = (props: any) => {
  const { dippinid } = props.match.params;

  // 화면에 표시할 게시물
  const [dippin, setDippin] = useState<FeedPost>();
  const [dippinlist, setDippinList] = useState<Array<FeedPost>>([]);
  const postfeeds = HomeFeedData; // 더미 데이터

  // 첫 렌더링 시 서버에 요청
  useEffect(() => {
    getDippinDetail(dippinid);
  }, []);

  // 백엔드에 요청
  const getDippinDetail = async (id: string) => {
    // test code start
    const res0 = {
      data: postfeeds[parseInt(id)],
      end: false,
    };

    const res1 = {
      data: [
        postfeeds[0],
        postfeeds[1],
        postfeeds[2],
        postfeeds[3],
        postfeeds[2],
        postfeeds[1],
        postfeeds[0],
      ],
      end: false,
    };
    // test code end

    // const res0 = await axios.get('url', {
    //   params: {
    //     id:id
    //   },
    // });
    // const res1 = await axios.get('url', {
    //   params: {
    //     id:id
    //   },
    // });

    setDippin(res0.data);
    setDippinList(res1.data);
  };

  return <div />;
};
