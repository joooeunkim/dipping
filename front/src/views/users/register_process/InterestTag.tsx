import { Box, Input } from '@chakra-ui/react';
import { SetStateAction, useState } from 'react';
import { useSelector } from 'react-redux';
import { useDispatch } from 'react-redux';
import { Tag } from '../../../components/Tag';
import { setMusicTaste } from '../../../reducers/registerReducer';

export const InterestTag = () => {
  const [tags, setTags] = useState<SetStateAction<any>>([]);
  const dispatch = useDispatch();
  const registerState = useSelector((state: any) => {
    return state.registerReducer;
  });
  const createTag = (e: any) => {
    let tagName: string = e.target.value;
    let code = e.code;
    const temp: any[] = [...tags, tagName];
    if (code === 'Enter') {
      if (e.nativeEvent.isComposing === false) {
        dispatch(setMusicTaste('#' + temp.join('#')));
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
    dispatch(setMusicTaste('#' + tags.join('#')));
  };

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
