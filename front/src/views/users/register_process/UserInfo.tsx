import { Box, Button, Flex, Input, Text } from '@chakra-ui/react';
import { execFile } from 'child_process';
import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import { useDispatch } from 'react-redux';
import { defaultAxios } from '../../../api/common';
import {
  setDupEmail,
  setDupNickname,
  setEmail,
  setNickname,
  setPassword,
} from '../../../reducers/registerReducer';
import { register } from '../../../serviceWorker';
// 나중에 소셜이냐 아니냐로 필드 숨기는거 처리해야함.

export const UserInfo = () => {
  const [dupleEmail, setDupleEmail] = useState(false);
  const [dupleNickname, setDupleNickname] = useState(false);
  const [checkpassword, setCheckPassword] = useState(false);
  const dispatch = useDispatch();
  const registerState = useSelector((state: any) => state.registerReducer);
  // console.log(registerState);
  const duplicateHandler = (e: any) => {
    if (e.target.id == 'email') {
      defaultAxios.get('/check/email?email=' + registerState.email).then(res => {
        console.log(res.data);
        if (res.data) {
          // 아이디 중복된경우
          setDupleEmail(false);
          dispatch(setDupEmail(false));
        } else {
          setDupleEmail(true);
          dispatch(setDupEmail(true));
        }
      });
    } else {
      defaultAxios.get('/check/nickname?nickname=' + registerState.nickname).then(res => {
        console.log(res.data);
        if (res.data) {
          // 닉네임 중복된경우
          setDupleNickname(false);
          dispatch(setDupNickname(false));
        } else {
          dispatch(setDupNickname(true));
          setDupleNickname(true);
        }
      });
    }
  };

  const changeHandler = (e: any) => {
    const id = e.target.id;
    const value = e.target.value;
    // console.log(e.target.id, e.target.value);

    if (id == 'email') {
      setDupleEmail(false);
      dispatch(setEmail(value));
    } else if (id == 'nickname') {
      setDupleNickname(false);
      dispatch(setNickname(value));
    } else if (id == 'password') {
      dispatch(setPassword(value));
    }
  };

  const checkPasswordInput = (e: any) => {
    if (registerState.password !== '' && e.target.value === registerState.password) {
      setCheckPassword(true);
    } else {
      setCheckPassword(false);
    }
  };

  const dupleEmailMsg = '사용 가능한 이메일입니다!';
  const dupleNicknameMsg = '사용 가능한 닉네임입니다!';
  const checkPasswordMsg = '비밀번호 확인 완료!';

  return (
    <Box>
      <Box pr="1" pl="1" position="relative">
        <Flex>
          <Input
            id="email"
            onChange={changeHandler}
            variant="flushed"
            focusBorderColor="cyan.400"
            placeholder="이메일"
            type="email"
            size="lg"
            mt="8"
            value={registerState.email || ''}
          />
          <Button ml="2" mt="10" id="email" size="sm" onClick={duplicateHandler}>
            중복확인
          </Button>
        </Flex>
        <Text color="cyan.400">{dupleEmail ? dupleEmailMsg : ''}</Text>

        <Flex>
          <Input
            onChange={changeHandler}
            id="nickname"
            variant="flushed"
            focusBorderColor="cyan.400"
            placeholder="닉네임"
            size="lg"
            mt="8"
            value={registerState.nickname || ''}
          />
          <Button ml="2" mt="12" id="nickname" size="sm" onClick={duplicateHandler}>
            중복확인
          </Button>
        </Flex>

        <Text color="cyan.400">{dupleNickname ? dupleNicknameMsg : ''}</Text>
        <Input
          id="password"
          variant="flushed"
          focusBorderColor="cyan.400"
          placeholder="비밀번호"
          type="password"
          size="lg"
          mt="8"
          onChange={changeHandler}
          value={registerState.password || ''}
        />
        <Input
          variant="flushed"
          focusBorderColor="cyan.400"
          placeholder="비밀번호확인"
          type="password"
          size="lg"
          mt="8"
          onChange={checkPasswordInput}
        />
        <Text color="cyan.400">{checkpassword ? checkPasswordMsg : ''}</Text>
      </Box>
    </Box>
  );
};
