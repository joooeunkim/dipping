import { Button } from '@chakra-ui/react';

export const CyanButton = (props: any) => {
  return (
    <Button
      w="100%"
      bg="cyan.400"
      // size="lg"
      color="white"
      _hover={{
        bg: 'cyan.500',
      }}
      _active={{
        bg: 'cyan.500',
      }}
    >
      {props.title}
    </Button>
  );
};
