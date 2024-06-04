package com.wave.systemManagement.service;

import com.wave.systemManagement.model.User;

public interface UserService {

    User findUserProfileByJwt(String jwt) throws Exception;

    User findUserByEmail(String email) throws Exception;

    User findUserById(Long userId) throws Exception;

    User updateUsersProjectsSize(User user, int number) throws Exception;
}
