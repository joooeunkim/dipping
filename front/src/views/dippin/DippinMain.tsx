import { Box } from '@chakra-ui/react';
import { SearchNavBar } from '../../components/floatingbar/SearchNavBar';
import { useEffect, useState } from 'react';
import { setDefault } from '../../reducers/iframeReducer';
import { useSelector, useDispatch } from 'react-redux';

export const DippinMain = () => {
  const props = {
    leftDisplay: 'none',
    rightDisplay: 'none',
  };

  return (
    <Box>
      <SearchNavBar {...props} />
      DippinMain
    </Box>
  );
};
