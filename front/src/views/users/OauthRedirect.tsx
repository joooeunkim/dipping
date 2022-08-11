import { Box, Center, Container, Spinner, Text } from '@chakra-ui/react';
import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { useLocation, useNavigate } from 'react-router-dom';
import { parseJwt } from '../../api/login/local';
import { SET_TOKEN } from '../../reducers/Auth';

export const OauthRedirect = () => {
  let token = useLocation().search.split('=')[1];
  let role = parseJwt(token).roles[0].authority;
  let email = parseJwt(token).sub;
  console.log(parseJwt(token).sub);

  const navigate = useNavigate();
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(SET_TOKEN(token));

    navigate('/process', { state: email });

    // navigate(navigate_url);
  });

  return (
    <Container>
      <Center mt={40}>
        <Text></Text>
        <Spinner size="xl" />
      </Center>
    </Container>
  );
};
