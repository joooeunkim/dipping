import { Button, useColorModeValue } from '@chakra-ui/react';

export const CyanButton = (props: any) => {
  const buttonColor = useColorModeValue('cyan.400', 'cyan.500');
  return (
    <Button
      w="100%"
      bg={buttonColor}
      // size="lg"
      color="white"
      _hover={{
        bg: buttonColor,
      }}
      _active={{
        bg: buttonColor,
      }}
    >
      {props.title}
    </Button>
  );
};
