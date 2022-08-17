import {
  Box,
  Button,
  Circle,
  Drawer,
  DrawerBody,
  DrawerCloseButton,
  DrawerContent,
  DrawerHeader,
  DrawerOverlay,
  Popover,
  PopoverBody,
  PopoverContent,
  PopoverTrigger,
  Portal,
  useBoolean,
  useColorModeValue,
  useDisclosure,
} from '@chakra-ui/react';
import React, { useState } from 'react';
import { CyanButton } from './CyanButton';
import { DippinForm } from './DippinForm';
import { ModalNavBar } from './floatingbar/ModalNavBar';
import { PostFeedForm } from './PostFeedForm';

export const WritePost = () => {
  const popOverColor = useColorModeValue('cyan.400', 'cyan.500');

  const { isOpen, onOpen, onClose } = useDisclosure();
  const [isEditing, setIsEditing] = useBoolean(); // popover hook
  const btnRef = React.useRef(null);
  const [titleState, setTitleState] = useState<string>('');

  const size = 48;
  const bgColor = useColorModeValue('white', 'gray.800');
  const icons_light = [
    'fa-light fa-house',
    'fa-light fa-music',
    'fa-regular fa-plus',
    'fa-light fa-compact-disc',
    'fa-light fa-user',
  ];
  return (
    <Popover
      isOpen={isEditing}
      onOpen={setIsEditing.on}
      onClose={setIsEditing.off}
      closeOnBlur={false}
    >
      <PopoverTrigger>
        <Circle
          size={size * 0.7 + 'px'}
          position="relative"
          bottom={size * 0.1 + 'px'}
          bgGradient="linear(to-br, blue.400, cyan.200)"
        >
          <Box fontSize={size * 0.5 + 'px'} className={icons_light[2]} color={bgColor} />
        </Circle>
      </PopoverTrigger>
      <Portal>
        <PopoverContent w="140px" bg="none" border="0">
          <PopoverBody>
            <Button
              bg={popOverColor}
              color="white"
              _hover={{
                bg: { popOverColor },
              }}
              _active={{
                bg: { popOverColor },
              }}
              w="100%"
              mb="1"
              ref={btnRef}
              onClick={() => {
                onOpen();
                setTitleState('포스트');
              }}
            >
              <i className="fa-light fa-pen-to-square"></i>&nbsp;포스트
            </Button>
            <Button
              bg={popOverColor}
              color="white"
              _hover={{
                bg: { popOverColor },
              }}
              _active={{
                bg: { popOverColor },
              }}
              w="100%"
              ref={btnRef}
              onClick={() => {
                onOpen();
                setTitleState('디핑');
              }}
            >
              <i className="fa-light fa-compact-disc"> </i>&nbsp;디핑
            </Button>
          </PopoverBody>
        </PopoverContent>
        <Drawer isOpen={isOpen} placement="right" onClose={onClose} size="full">
          <DrawerOverlay />
          <DrawerContent>
            <ModalNavBar
              title={titleState}
              leftElement={
                <Box
                  className="fa-light fa-angle-left"
                  fontSize="28px"
                  lineHeight="36px"
                  bg=""
                  onClick={onClose}
                />
              }
              // rightElement={'완료'}
            />
            <DrawerBody>
              <Box h="48px" w="full" />
              {titleState === '포스트' ? <PostFeedForm /> : <DippinForm />}
            </DrawerBody>
          </DrawerContent>
        </Drawer>
      </Portal>
    </Popover>
  );
};
