import { Box, Input, Image, Flex, useDisclosure, Center } from '@chakra-ui/react';
import { useEffect, useState } from 'react';
import { DippinItem } from '../../components/dippin/DippinItem';
import { SearchNavBar } from '../../components/floatingbar/SearchNavBar';
import axios from 'axios';
import { AddMusic } from '../../components/AddMusic';
import { Music } from '../../types/HomeFeedData';

export const DippinMain = () => {
  const [input, setInput] = useState('');
  const [youtube, setYoutube] = useState([]);
  const [musicbrainz, setMusicbrainz] = useState([]);

  const { isOpen, onOpen, onClose } = useDisclosure();

  const onKeyInput = (e: any) => {
    if (e.key === 'Enter') {
      setInput(e.target.value);
      const text = e.target.value;
      console.log(text);
    }
  };

  return (
    <Box>
      <SearchNavBar leftDisplay="none" rightDisplay="none" />
      <Box border="1px" onClick={onOpen}>
        modal
      </Box>
      <AddMusic isOpen={isOpen} onClose={onClose} setData={setYoutube} />
      {youtube.map((item: Music, index: number) => (
        <Box key={index}>
          {index === 0 && (
            <Box h="8px">
              <hr />
            </Box>
          )}
          <Box position="relative" w="full" h="64px" bg="" marginY="16px">
            <Flex w="auto">
              <Image
                marginX="16px"
                borderRadius="10px"
                boxSize="64px"
                objectFit="cover"
                src={item.albumart}
                onClick={() => {
                  (window as any).player.loadVideoById({ videoId: item.id });
                }}
              />
              <Box lineHeight="32px" fontSize="14px">
                <Box w="full">{item.artist}</Box>
                <Box w="full">{item.title}</Box>
              </Box>
            </Flex>
          </Box>
          {index === youtube.length - 1 && <Box h="8px" />}
        </Box>
      ))}
      <DippinItem />
      <hr />
      <DippinItem />
      <hr />
    </Box>
  );
};
