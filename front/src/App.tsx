import { ChakraProvider } from '@chakra-ui/react';
import { Route, Routes } from 'react-router-dom';
import ProtectedRoute from './ProtectedRoute';
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
import { ProfileEdit } from './views/profile/ProfileEdit';
import { PersonalInfoEdit } from './views/profile/PersonalInfoEdit';

import { ProtectedRouteProps } from './ProtectedRoute';

export const App = () => (
  <ChakraProvider>
    <Routes>
      <Route
        path="/"
        element={
          <ProtectedRoute isAuthenticated={true} authenticationPath="/login" outlet={<Layout />} />
        }
      >
        <Route index element={<HomeMain />} />
        <Route path="dippin" element={<DippinMain />} />
        <Route path="search" element={<SearchMain />} />
        <Route path="profile" element={<ProfileMain />} />
        <Route path="profile/edit" element={<ProfileEdit />} />
        <Route path="profile/edit/person" element={<PersonalInfoEdit />} />
        <Route path="profile/:nickname" element={<ProfileMain />} />
      </Route>
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/findPassword" element={<FindPassword />} />
      <Route path="/setPassword" element={<SetPassword />} />
      <Route path="/process" element={<RegisterProcessLayout />} />
    </Routes>
  </ChakraProvider>
);
