import { Avatar, Box, Flex, Image } from '@chakra-ui/react';

export const DippinItem = () => {
  const i = 1;

  return (
    <Box position="relative" w="full" h="120px" bg="" padding="8px">
      <Flex>
        <Avatar position="relative" boxSize="40px" name="mocha_oca" src="" />
        <Box position="relative" marginX="8px" w="full">
          <Box lineHeight="18px">
            <Box fontSize="16px" fontWeight="600" display="inline">
              Title
            </Box>
            <Box fontSize="14px" fontWeight="400" display="inline" color="gray.400">
              &nbsp; username
            </Box>
          </Box>
          <Box h="54px" lineHeight="18px" fontSize="14px" fontWeight="400">
            post body fits almost this size post body fits almost this size post body fits almost
            this
          </Box>
          <Flex position="absolute" bottom="0px" w="full">
            <Box w="10%" className="fa-regular fa-heart" fontSize="14px" lineHeight="16px" />
            <Box w="40%" fontSize="14px" lineHeight="16px">
              142
            </Box>
            <Box w="10%" className="fa-regular fa-comment" fontSize="14px" lineHeight="16px" />
            <Box w="40%" fontSize="14px" lineHeight="16px">
              142
            </Box>
          </Flex>
        </Box>
        <Image
          borderRadius="10px"
          boxShadow="0 0 2px gray"
          boxSize="104"
          objectFit="cover"
          src="https://i.ytimg.com/vi/Gc4sY98Jn9I/maxresdefault.jpg"
          alt="image"
        />
      </Flex>
      {/* icon set */}
    </Box>
  );
};
