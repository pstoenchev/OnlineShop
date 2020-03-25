package org.softuni.productshop.service;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserServiceModel registerUser(UserServiceModel userServiceModel);

    UserServiceModel findUserName(String username);

    UserServiceModel editUserProfile(UserServiceModel userServiceModel, String olPassword);

    List<UserServiceModel> findAllUsers();

    void setUserRole(String id, String role);



}
