import { Box, Input, Image, Flex, useDisclosure } from '@chakra-ui/react';
import { useEffect, useState } from 'react';
import { DippinItem } from '../../components/dippin/DippinItem';
import { SearchNavBar } from '../../components/floatingbar/SearchNavBar';
import axios from 'axios';
import { AddMusic } from '../../components/AddMusic';

export const DippinMain = () => {
  const props = {
    leftDisplay: 'none',
    rightDisplay: 'none',
  };

  const [input, setInput] = useState('');
  const [youtube, setYoutube] = useState([]);
  const [musicbrainz, setMusicbrainz] = useState([]);

  const { isOpen, onOpen, onClose } = useDisclosure();

  const onKeyInput = (e: any) => {
    if (e.key === 'Enter') {
      setInput(e.target.value);
      const text = e.target.value;
      console.log(text);

      getMusicbrainz(text);
    }
  };

  const getMusicbrainz = (text: string) => {
    setMusicbrainz([]);
    const [artist, title] = text.split('-');

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
      <Flex>
        {musicbrainz.map((item: any, index: number) => (
          <Box key={index}>
            <Box position="relative" w="full" h="64px" bg="" padding="8px">
              <Image borderRadius="10px" boxSize="64px" objectFit="cover" src={item} />
            </Box>
          </Box>
        ))}
      </Flex>
      <Box border="1px" onClick={onOpen}>
        modal
      </Box>
      <AddMusic isOpen={isOpen} onClose={onClose} setData={setYoutube} />
    </Box>
  );
};
