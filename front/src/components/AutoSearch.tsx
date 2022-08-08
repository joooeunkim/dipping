import { Box, InputGroup, Select } from '@chakra-ui/react';
import React, { useState } from 'react';

const options = [
  { value: 'chocolate', label: 'Chocolate' },
  { value: 'strawberry', label: 'Strawberry' },
  { value: 'vanilla', label: 'Vanilla' },
];

export const AutoSearch = () => {
  const [selectedOption, setSelectedOption] = useState(null);

  return (
    <Box width="100%">
      <InputGroup>
        <Select defaultValue={selectedOption} onChange={setSelectedOption} options={options} />
      </InputGroup>
    </Box>
  );
};
