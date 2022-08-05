import { Box, Center } from '@chakra-ui/react';

export const Tag = (props: any) => {
  const text: string = props.text;
  const deleteTag = props.deleteTag;

  return (
    <Center
      pr="3"
      pl="3"
      pt="0.5"
      pb="0.5"
      m="1.5"
      mb="0.5"
      ml="0"
      border="1px"
      width="fit-content"
      display="inline-block"
      borderRadius="full"
      borderColor="gray.500"
      color="gray.500"
    >
      {text}
      <Box color="cyan.400" display="inline-block" ml="2">
        <i className="fa-regular fa-xmark" onClick={e => deleteTag(text)} />
      </Box>
    </Center>
  );
};
