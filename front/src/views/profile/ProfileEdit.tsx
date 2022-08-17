import { Container, Flex, Image, Box, Text, Input, Spacer, Link, Button } from '@chakra-ui/react';
import { ModalNavBar } from '../../components/floatingbar/ModalNavBar';
import React, { SetStateAction, useState } from 'react';
import { Tag } from '../../components/Tag';
import ImageUploading, { ImageListType } from 'react-images-uploading';
import { authAxios } from '../../api/common';
import axios from 'axios';

export const ProfileEdit = () => {
  const props = {
    title: '프로필 수정',
    leftElement: <Box className="fa-light fa-angle-left" lineHeight="36px" fontSize="24px" bg="" />,
    rightElement: <Box className="fa-light fa-bars" lineHeight="36px" fontSize="24px" bg="" />,
  };
  const createTag = (e: any) => {
    let tagName: string = e.target.value;
    let code = e.code;
    const temp: any[] = [...tags, tagName];
    if (code === 'Enter') {
      if (e.nativeEvent.isComposing === false) {
        if (tags) {
          setTags(temp);
        } else {
          setTags([tagName]);
        }
        e.target.value = '';
      }
    }
  };

  const deleteTag = (value: string) => {
    let _tags = tags;

    _tags = _tags.filter((e: any) => {
      return e != value;
    });

    setTags(_tags);
  };
  const [images, setImages] = React.useState([]);
  const maxNumber = 69;

  const onChange = async (imageList: ImageListType, addUpdateIndex: number[] | undefined) => {
    console.log(imageList, addUpdateIndex);
    setImages(imageList as never[]);
    const res: any = await authAxios.get('/profile/', {
      params: {
        profileImgUrl: addUpdateIndex,
      },
    });
    console.log(res);

    // axios({
    //   method: 'post',
    //   url: 'http://localhost:3000/api/profile/',
    //   data: addUpdateIndex,
    // }).then(({ data }) => {
    //   console.log('Succesfully uploaded: ', JSON.stringify(data));
    // });
  };

  const [tags, setTags] = useState<SetStateAction<any>>([]);

  return (
    <Box>
      <ModalNavBar {...props} />
      <Container maxW="480px" w="100%" bg="" h="100vh" margin="0 auto">
        <Flex flexDirection="column" alignItems="center" marginTop="40px" marginBottom="40px">
          <Box>
            <ImageUploading multiple value={images} onChange={onChange} maxNumber={maxNumber}>
              {({
                imageList,
                onImageUpload,
                onImageRemoveAll,
                onImageUpdate,
                onImageRemove,
                isDragging,
                dragProps,
              }) => (
                <Box className="upload__image-wrapper">
                  <button
                    style={isDragging ? { color: 'red' } : undefined}
                    onClick={onImageUpload}
                    {...dragProps}
                  >
                    프로필 이미지 등록
                  </button>
                  {imageList.map((image, index) => (
                    <Box key={index} className="image-item">
                      <Image
                        src={image.dataURL}
                        borderRadius="full"
                        boxSize="96px"
                        alt="ProfileImg"
                        marginBottom="16px"
                      />
                      <Box className="image-item__btn-wrapper">
                        <Button
                          width="96px"
                          height="32px"
                          bg="cyan.400"
                          // size="lg"
                          color="white"
                          _hover={{
                            bg: 'cyan.500',
                          }}
                          _active={{
                            bg: 'cyan.500',
                          }}
                          onClick={() => onImageUpdate(index)}
                        >
                          이미지 변경
                        </Button>
                      </Box>
                    </Box>
                  ))}
                </Box>
              )}
            </ImageUploading>
          </Box>
        </Flex>
        <Flex>
          <Box alignItems="left" marginTop="8px" width="96px">
            <Text>이름</Text>
          </Box>
          <Spacer />
          <Box width="100%">
            <Input variant="flushed" placeholder="기존이름" />
          </Box>
        </Flex>
        <Flex marginBottom="8px">
          <Box alignItems="left" marginTop="8px" width="96px">
            <Text>관심분야</Text>
          </Box>
          <Spacer />
          <Box width="100%">
            <Input variant="flushed" onKeyDown={e => createTag(e)} placeholder="#관심태그입력" />
          </Box>
        </Flex>
        <Box marginBottom="32px">
          {tags
            ? tags.map((text: any, index: any) => (
                <Tag key={index} text={text} deleteTag={deleteTag} />
              ))
            : ''}
        </Box>
        <Flex>
          <Link href="edit/person">개인정보 변경</Link>
        </Flex>
      </Container>
    </Box>
  );
};
