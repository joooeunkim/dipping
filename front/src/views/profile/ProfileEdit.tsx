import { Flex, Image, Box, Text, Input } from '@chakra-ui/react';

export const ProfileEdit = () => {
  return (
    <Flex flexDirection="column" alignItems="center" paddingTop="24">
      <Box>
        <Image src="logo192.png" borderRadius="full" htmlWidth="70px" htmlHeight="70px" />
        <Text>사진 바꾸기</Text>
      </Box>
      <Box alignItems="left">
        <Text>이름</Text>
        <Input variant="flushed" placeholder="Flushed" />
      </Box>
    </Flex>
  );
};
