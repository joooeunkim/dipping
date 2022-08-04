import { Box, Image } from '@chakra-ui/react';
import { SearchNavBar } from '../../components/SearchNavBar';

export const DippinMain = () => {
  const props = {
    leftDisplay: 'none',
    rightDisplay: 'none',
  };

  return (
    <Box>
      <SearchNavBar {...props} />
      SearchMain
    </Box>
  );
};
