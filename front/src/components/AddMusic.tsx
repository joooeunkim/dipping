import { useEffect, useState } from 'react';
import {
  Input,
  Image,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalFooter,
  ModalBody,
  ModalCloseButton,
  Box,
  Flex,
  InputLeftElement,
  InputGroup,
  Center,
} from '@chakra-ui/react';
import axios from 'axios';

export const AddMusic = ({
  isOpen,
  onClose,
  setData,
}: {
  isOpen: boolean;
  onClose: () => void;
  setData: any;
}) => {
  const [input, setInput] = useState('');
  const [youtube, setYoutube] = useState([]);

  const onCloseModal = () => {
    setYoutube([]);
    (window as any).player.stopVideo();
  };

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

  return (
    <Modal isOpen={isOpen} onClose={onClose} onCloseComplete={onCloseModal}>
      <ModalOverlay />
      <ModalContent borderRadius="10px">
        <InputGroup h="48px">
          <InputLeftElement
            h="48px"
            color="gray.300"
            fontSize="16px"
            children={<Box className="fa-regular fa-magnifying-glass" />}
          />
          <Input
            fontSize="16px"
            lineHeight="48px"
            placeholder="Basic usage"
            onKeyDown={onKeyInput}
            variant="unstyled"
          />
        </InputGroup>

        <ModalBody padding="0">
          {youtube.map((item: any, index: number) => (
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
                    src={'https://i.ytimg.com/vi/' + item.id.videoId + '/hqdefault.jpg'}
                    onClick={() => {
                      (window as any).player.loadVideoById({ videoId: item.id.videoId });
                    }}
                  />
                  <Center
                    h="64px"
                    w="auto"
                    lineHeight="18px"
                    fontSize="14px"
                    fontWeight="400"
                    marginRight="16px"
                    overflow="hidden"
                  >
                    {/* <Box>id: {item.id.videoId}</Box> */}
                    <Box w="full">{item.snippet.title.replaceAll('&quot;', '"')}</Box>
                  </Center>
                </Flex>
              </Box>
              {index === youtube.length - 1 && <Box h="8px" />}
            </Box>
          ))}
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};
