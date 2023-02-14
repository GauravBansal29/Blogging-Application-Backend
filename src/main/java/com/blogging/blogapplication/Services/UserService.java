package com.blogging.blogapplication.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogging.blogapplication.Entities.User;
import com.blogging.blogapplication.Repositories.UserRepository;
import com.blogging.blogapplication.Payloads.UserDto;

public interface UserService {

    UserDto registerUser(UserDto user);

    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, Long userid);

    UserDto getUserbyId(Long userId);

    List<UserDto> getallUsers();

    void deleteUser(Long userId);

}
