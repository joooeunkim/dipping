import { Box, Input } from '@chakra-ui/react';
import { SetStateAction, useState } from 'react';
import { Tag } from '../../../components/Tag';

export const InterestTag = () => {
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

  const [tags, setTags] = useState<SetStateAction<any>>([]);

  return (
    <Box>
      <Box>
        <Input
          variant="flushed"
          placeholder="관심태그입력"
          maxLength={15}
          onKeyDown={e => createTag(e)}
        />
      </Box>
      <Box>
        {tags
          ? tags.map((text: any, index: any) => (
              <Tag key={index} text={text} deleteTag={deleteTag} />
            ))
          : ''}
      </Box>
    </Box>
  );
};
