import { Avatar, Box, Flex, Image } from '@chakra-ui/react';
import { FeedPost } from '../../types/HomeFeedData';

export const DippinItem = (props: any) => {
  const { dippin }: { dippin: FeedPost } = props;

  return (
    <Box position="relative" w="full" bg="" padding="8px">
      <Flex>
        <Avatar position="relative" boxSize="40px" name="mocha_oca" src="" />
        <Box position="relative" marginX="8px" w="full">
          <Flex lineHeight="18px" h="18px">
            <Box fontSize="15px" fontWeight="600" overflow="hidden" h="18px" w="auto">
              {dippin.title}
            </Box>
            &nbsp;
            <Box fontSize="14px" fontWeight="400" color="gray.400">
              {dippin.user.name}
            </Box>
          </Flex>
          <Box
            marginY="4px"
            h="54px"
            lineHeight="18px"
            fontSize="14px"
            fontWeight="400"
            overflow="hidden"
          >
            {dippin.article.length > 82 ? dippin.article.slice(0, 82) + ' ...' : dippin.article}
          </Box>
          <Flex position="absolute" bottom="0px" w="full">
            <Box w="20px" className="fa-regular fa-heart" fontSize="14px" lineHeight="16px" />
            <Box w="64px" fontSize="14px" lineHeight="16px">
              {dippin.likes}
            </Box>
            <Box w="20px" className="fa-regular fa-comment" fontSize="14px" lineHeight="16px" />
            <Box w="64px" fontSize="14px" lineHeight="16px">
              {dippin.comments.length}
            </Box>
          </Flex>
        </Box>
        {dippin.playlist[0] && (
          <Image
            borderRadius="10px"
            boxShadow="0 0 2px gray"
            boxSize="104px"
            objectFit="cover"
            src={dippin.playlist[0].albumart}
            alt="image"
          />
        )}
      </Flex>
      {/* icon set */}
    </Box>
  );
};
