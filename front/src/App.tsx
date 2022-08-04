import { ChakraProvider } from '@chakra-ui/react';
import { Route, Routes } from 'react-router-dom';
import { DippinMain } from './views/dippin/DippinMain';
import { HomeMain } from './views/home/HomeMain';
import { Layout } from './views/Layout';
import { ProfileMain } from './views/profile/ProfileMain';
import { SearchMain } from './views/search/SearchMain';
import { FindPassword } from './views/users/FindPassword';
import { Login } from './views/users/Login';
import { Register } from './views/users/Register';
import { Genre } from './views/users/register_process/Genre';
import { InterestTag } from './views/users/register_process/InterestTag';
import { RegisterProcessLayout } from './views/users/register_process/RegisterProcessLayout';
import { UserInfo } from './views/users/register_process/UserInfo';
import { SetPassword } from './views/users/SetPassword';

export const App = () => (
  <ChakraProvider>
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route index element={<HomeMain />} />
        <Route path="dippin" element={<DippinMain />} />
        <Route path="search" element={<SearchMain />} />
        <Route path="profile" element={<ProfileMain />} />
        <Route path="profile/:nickname" element={<ProfileMain />} />
      </Route>
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/findPassword" element={<FindPassword />} />
      <Route path="/setPassword" element={<SetPassword />} />
      <Route path="/process" element={<RegisterProcessLayout />}>
        <Route path="step1" element={<UserInfo />} />
        <Route path="step2" element={<Genre />} />
        <Route path="step3" element={<InterestTag />} />
      </Route>
    </Routes>
  </ChakraProvider>
);