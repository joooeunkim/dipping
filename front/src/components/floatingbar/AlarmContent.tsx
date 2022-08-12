import { Box, Text, Flex, Image, Button } from '@chakra-ui/react';

export const AlarmFollow = () => {
  return (
    <Box>
      <Flex marginTop="8px" marginBottom="32px" align="center">
        <Image
          src="https://media.istockphoto.com/id/637093118/ko/%EC%82%AC%EC%A7%84/%EC%84%A0%EB%B0%98%EC%97%90-%EB%B9%84%EB%8B%90-%EB%A0%88%EC%BD%94%EB%93%9C.webp?s=612x612&w=is&k=20&c=oB37HTi3HV2u5SIveRMnYDH0ZAmjpKAsa5FIW74eHr8="
          borderRadius="full"
          boxSize="32px"
          alt="PostImg"
        />
        <Text p="8px" fontSize="12px">
          "nick"님이 팔로우를 요청했습니다.
        </Text>
        <Button colorScheme="blue" width="48px" height="16px" fontSize="12px">
          수락
        </Button>
        <Button width="48px" height="16px" fontSize="12px" marginLeft="8px">
          거절
        </Button>
      </Flex>
    </Box>
  );
};

export const AlarmActive = () => {
  return (
    <Box>
      <Flex marginTop="8px" marginBottom="32px" align="center">
        <Image
          src="https://media.istockphoto.com/id/637093118/ko/%EC%82%AC%EC%A7%84/%EC%84%A0%EB%B0%98%EC%97%90-%EB%B9%84%EB%8B%90-%EB%A0%88%EC%BD%94%EB%93%9C.webp?s=612x612&w=is&k=20&c=oB37HTi3HV2u5SIveRMnYDH0ZAmjpKAsa5FIW74eHr8="
          borderRadius="full"
          boxSize="32px"
          alt="PostImg"
        />
        <Text p="8px" fontSize="12px">
          "nick"님이 회원님의 게시물에 댓글을 남겼습니다.
          <Text fontSize="8px" color="gray.500">
            00시간 전
          </Text>
        </Text>
      </Flex>
    </Box>
  );
};
