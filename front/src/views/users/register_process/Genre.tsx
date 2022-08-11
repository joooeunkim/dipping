import { Box, Center, Flex, Spacer } from '@chakra-ui/react';
import { useState } from 'react';
import { useOutletContext } from 'react-router-dom';

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
export const Genre = () => {
  const [genre, setGenre] = useState<GenreList>({
    data: genreList,
  });

  const clickGenre = (e: any) => {
    // console.log(e.target.id);
    genreList[e.target.id] = genreList[e.target.id] ? false : true;

    setGenre({
      data: genreList,
    });
  };

  // console.log(genre.data);

  return (
    <Flex flexWrap="wrap" justifyContent="center" mt="8">
      <Flex width="100%" justifyContent="space-between" mb="calc(10vw)">
        <Center
          id="ballad"
          onClick={clickGenre}
          border="1px"
          borderRadius="full"
          w="100px"
          h="100px"
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
          w="100px"
          h="100px"
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
          w="100px"
          h="100px"
          borderColor={genreList.rap ? 'cyan.400' : 'gray.500'}
          color={genreList.rap ? 'cyan.400' : 'gray.500'}
        >
          랩/힙합
        </Center>
      </Flex>
      <Flex width="100%" justifyContent="space-between" mb="calc(10vw)">
        <Center
          id="RBSoul"
          onClick={clickGenre}
          border="1px"
          borderRadius="full"
          w="100px"
          h="100px"
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
          w="100px"
          h="100px"
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
          w="100px"
          h="100px"
          borderColor={genreList.rock ? 'cyan.400' : 'gray.500'}
          color={genreList.rock ? 'cyan.400' : 'gray.500'}
        >
          록/메탈
        </Center>
      </Flex>
      <Flex width="100%" justifyContent="space-between" mb="calc(10vw)">
        <Center
          id="pop"
          onClick={clickGenre}
          border="1px"
          borderRadius="full"
          w="100px"
          h="100px"
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
          w="100px"
          h="100px"
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
          w="100px"
          h="100px"
          borderColor={genreList.jazz ? 'cyan.400' : 'gray.500'}
          color={genreList.jazz ? 'cyan.400' : 'gray.500'}
        >
          재즈
        </Center>
      </Flex>
      <Flex width="100%" justifyContent="space-between" mb="calc(10vw)">
        <Center
          id="folk"
          onClick={clickGenre}
          border="1px"
          borderRadius="full"
          w="100px"
          h="100px"
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
          w="100px"
          h="100px"
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
          w="100px"
          h="100px"
          borderColor={genreList.newage ? 'cyan.400' : 'gray.500'}
          color={genreList.newage ? 'cyan.400' : 'gray.500'}
        >
          뉴에이지
        </Center>
      </Flex>
    </Flex>
  );
};
