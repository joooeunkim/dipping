import { Box } from '@chakra-ui/react';
import { DippinItem } from '../../components/dippin/DippinItem';
import { SearchNavBar } from '../../components/floatingbar/SearchNavBar';

export const DippinMain = () => {
  const props = {
    leftDisplay: 'none',
    rightDisplay: 'none',
  };

  return (
    <Box>
      <SearchNavBar {...props} />
      <DippinItem />
      <hr />
      <DippinItem />
      <hr />
      <DippinItem />
      <hr />
      <DippinItem />
      <hr />
      <DippinItem />
      <hr />
      <DippinItem />
      <hr />
      <DippinItem />
      <hr />
      <DippinItem />
    </Box>
  );
};
