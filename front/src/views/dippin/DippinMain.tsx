import { Box, Input, Image, Flex } from '@chakra-ui/react';
import { useState } from 'react';
import { DippinItem } from '../../components/dippin/DippinItem';
import { SearchNavBar } from '../../components/floatingbar/SearchNavBar';
import axios from 'axios';

export const DippinMain = () => {
  const props = {
    leftDisplay: 'none',
    rightDisplay: 'none',
  };

  const [input, setInput] = useState('');
  const [youtube, setYoutube] = useState([]);
  const [musicbrainz, setMusicbrainz] = useState([]);

  const onKeyInput = (e: any) => {
    if (e.key === 'Enter') {
      setInput(e.target.value);
      const text = e.target.value;
      console.log(text);
      axios({
        method: 'get',
        url: 'https://www.googleapis.com/youtube/v3/search',
        params: {
          key: process.env.REACT_APP_YOUTUBE_API_KEY,
          part: 'snippet',
          max: 5,
          type: 'video',
          q: text + ' audio',
          fields: 'items(id(videoId),snippet(title,thumbnails(high(url)),channelTitle))',
        },
      }).then(res => {
        setYoutube(res.data.items);
      });
    }
  };

  const getMusicbrainz = () => {
    setMusicbrainz([]);
    const [artist, title] = input.split('-');

    const query = title + ' AND name:' + artist + ' AND type:album';
    console.log(query);

    axios({
      method: 'get',
      url: 'https://musicbrainz.org/ws/2/recording',
      params: {
        query: query,
        limit: 2,
        fmt: 'json',
      },
    }).then(res => {
      console.log(res.data);
      res.data.recordings.forEach((item: any, index: number) => {
        const sid = item.releases[0].id;
        console.log(sid);
        axios.get('https://coverartarchive.org/release/' + sid).then(res => {
          setMusicbrainz((curlist: never[]) => [...curlist, res.data.images[0].image] as never[]);
        });
      });
    });
  };

  return (
    <Box>
      <SearchNavBar {...props} />
      <DippinItem />
      <hr />
      <DippinItem />
      <hr />
      <Input placeholder="Basic usage" onKeyDown={onKeyInput} />
      <Box>
        {youtube.map((item: any, index: number) => (
          <Box key={index}>
            <Box position="relative" w="full" h="96px" bg="" padding="8px">
              <Flex>
                <Box h="54px" w="full" lineHeight="18px" fontSize="14px" fontWeight="400">
                  <Box>id: {item.id.videoId}</Box>
                  <Box>title: {item.snippet.title}</Box>
                </Box>
                <Image
                  borderRadius="10px"
                  boxSize="80px"
                  objectFit="cover"
                  src={'https://i.ytimg.com/vi/' + item.id.videoId + '/hqdefault.jpg'}
                  onClick={() => {
                    (window as any).player.loadVideoById({ videoId: item.id.videoId });
                  }}
                />
              </Flex>
            </Box>
            <hr />
          </Box>
        ))}
      </Box>
      <Box border="1px" onClick={getMusicbrainz}>
        musicbrainz
      </Box>
      <Flex>
        {musicbrainz.map((item: any, index: number) => (
          <Box key={index}>
            <Box position="relative" w="full" h="64px" bg="" padding="8px">
              <Image borderRadius="10px" boxSize="64px" objectFit="cover" src={item} />
            </Box>
          </Box>
        ))}
      </Flex>
    </Box>
  );
};
