import { Box, Center, Flex, Spacer } from '@chakra-ui/react';
import { useState } from 'react';
import { useSelector } from 'react-redux';
import { useDispatch } from 'react-redux';
import { useOutletContext } from 'react-router-dom';
import { setMusicGenre } from '../../../reducers/registerReducer';

type GenreList = {
  data: Object | null;
};

type genreType = {
  [key: string]: boolean;
};

let genreList: genreType = {
  ballad: false,
  dance: false,
  rap: false,
  RBSoul: false,
  indi: false,
  rock: false,
  pop: false,
  edm: false,
  jazz: false,
  folk: false,
  trot: false,
  newage: false,
};

let genres = [
  '발라드',
  '댄스',
  '랩/힙합',
  'R&B/Soul',
  '인디음악',
  '록/메탈',
  'POP',
  'EDM',
  '재즈',
  '포크/블루스',
  '트로트',
  '뉴에이지',
];

export const Genre = () => {
  const dispatch = useDispatch();
  const registerState = useSelector((state: any) => state.registerReducer);
  const [genre, setGenre] = useState<GenreList>({
    data: genreList,
  });
  console.log(registerState.musicGenre);

  const clickGenre = (e: any) => {
    // console.log(e.target.id);
    genreList[e.target.id] = genreList[e.target.id] ? false : true;
    setGenre({
      data: genreList,
    });

    let genreValues = Object.values(genreList);
    let result = '';
    for (let i = 0; i < genreValues.length; i++) {
      if (genreValues[i]) {
        result += '#' + genres[i];
      }
    }
    dispatch(setMusicGenre(result));
  };

  return (
    <Flex flexWrap="wrap" justifyContent="center" mt="8">
      <Flex width="90%" justifyContent="space-between" mb="calc(6vw)">
        <Center
          id="ballad"
          onClick={clickGenre}
          border="1px"
          borderRadius="full"
          w="24vw"
          h="24vw"
          borderColor={genreList.ballad ? 'cyan.400' : 'gray.500'}
          color={genreList.ballad ? 'cyan.400' : 'gray.500'}
        >
          발라드
        </Center>
        <Spacer />
        <Center
          id="dance"
          onClick={clickGenre}
          border="1px"
          borderRadius="full"
          w="24vw"
          h="24vw"
          borderColor={genreList.dance ? 'cyan.400' : 'gray.500'}
          color={genreList.dance ? 'cyan.400' : 'gray.500'}
        >
          댄스
        </Center>
        <Spacer />
        <Center
          id="rap"
          onClick={clickGenre}
          border="1px"
          borderRadius="full"
          w="24vw"
          h="24vw"
          borderColor={genreList.rap ? 'cyan.400' : 'gray.500'}
          color={genreList.rap ? 'cyan.400' : 'gray.500'}
        >
          랩/힙합
        </Center>
      </Flex>
      <Flex width="90%" justifyContent="space-between" mb="calc(6vw)">
        <Center
          id="RBSoul"
          onClick={clickGenre}
          border="1px"
          borderRadius="full"
          w="24vw"
          h="24vw"
          borderColor={genreList.RBSoul ? 'cyan.400' : 'gray.500'}
          color={genreList.RBSoul ? 'cyan.400' : 'gray.500'}
        >
          R&B/Soul
        </Center>
        <Spacer />
        <Center
          id="indi"
          onClick={clickGenre}
          border="1px"
          borderRadius="full"
          w="24vw"
          h="24vw"
          borderColor={genreList.indi ? 'cyan.400' : 'gray.500'}
          color={genreList.indi ? 'cyan.400' : 'gray.500'}
        >
          인디음악
        </Center>
        <Spacer />
        <Center
          id="rock"
          onClick={clickGenre}
          border="1px"
          borderRadius="full"
          w="24vw"
          h="24vw"
          borderColor={genreList.rock ? 'cyan.400' : 'gray.500'}
          color={genreList.rock ? 'cyan.400' : 'gray.500'}
        >
          록/메탈
        </Center>
      </Flex>
      <Flex width="90%" justifyContent="space-between" mb="calc(6vw)">
        <Center
          id="pop"
          onClick={clickGenre}
          border="1px"
          borderRadius="full"
          w="24vw"
          h="24vw"
          borderColor={genreList.pop ? 'cyan.400' : 'gray.500'}
          color={genreList.pop ? 'cyan.400' : 'gray.500'}
        >
          POP
        </Center>
        <Spacer />
        <Center
          id="edm"
          onClick={clickGenre}
          border="1px"
          borderRadius="full"
          w="24vw"
          h="24vw"
          borderColor={genreList.edm ? 'cyan.400' : 'gray.500'}
          color={genreList.edm ? 'cyan.400' : 'gray.500'}
        >
          EDM
        </Center>
        <Spacer />
        <Center
          id="jazz"
          onClick={clickGenre}
          border="1px"
          borderRadius="full"
          w="24vw"
          h="24vw"
          borderColor={genreList.jazz ? 'cyan.400' : 'gray.500'}
          color={genreList.jazz ? 'cyan.400' : 'gray.500'}
        >
          재즈
        </Center>
      </Flex>
      <Flex width="90%" justifyContent="space-between" mb="calc(6vw)">
        <Center
          id="folk"
          onClick={clickGenre}
          border="1px"
          borderRadius="full"
          w="24vw"
          h="24vw"
          borderColor={genreList.folk ? 'cyan.400' : 'gray.500'}
          color={genreList.folk ? 'cyan.400' : 'gray.500'}
        >
          포크/블루스
        </Center>
        <Spacer />
        <Center
          id="trot"
          onClick={clickGenre}
          border="1px"
          borderRadius="full"
          w="24vw"
          h="24vw"
          borderColor={genreList.trot ? 'cyan.400' : 'gray.500'}
          color={genreList.trot ? 'cyan.400' : 'gray.500'}
        >
          트로트
        </Center>
        <Spacer />
        <Center
          id="newage"
          onClick={clickGenre}
          border="1px"
          borderRadius="full"
          w="24vw"
          h="24vw"
          borderColor={genreList.newage ? 'cyan.400' : 'gray.500'}
          color={genreList.newage ? 'cyan.400' : 'gray.500'}
        >
          뉴에이지
        </Center>
      </Flex>
    </Flex>
  );
};
