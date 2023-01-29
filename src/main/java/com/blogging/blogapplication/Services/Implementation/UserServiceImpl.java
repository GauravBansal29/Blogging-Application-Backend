package com.blogging.blogapplication.Services.Implementation;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogging.blogapplication.Entities.User;
import com.blogging.blogapplication.Exceptions.ResourceNotFoundException;
import com.blogging.blogapplication.Payloads.UserDto;
import com.blogging.blogapplication.Repositories.UserRepository;
import com.blogging.blogapplication.Services.UserService;
// we make a implementation class which implements the interface

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userdto) {

        User user = dtoTouser(userdto); // convert into user
        User savedUser = userRepo.save(user); // save in database
        return userTodto(savedUser); // returns dto of saved user
    }

    @Override
    public UserDto updateUser(UserDto userdto, Long userid) {

        User user = userRepo.findById(userid).orElseThrow(() -> {
            return new ResourceNotFoundException("User", "id", userid);
        }); // making our own exception by extending runtime exception

        user.setName(userdto.getName());
        user.setEmail(userdto.getEmail());
        user.setPassword(userdto.getPassword());
        user.setAbout(userdto.getAbout());

        User savedUser = userRepo.save(user); // we have not changed the id and id are unique so updated user will come

        return userTodto(savedUser);
    }

    @Override
    public UserDto getUserbyId(Long userId) {

        User user = userRepo.findById(userId).orElseThrow(() -> {
            return new ResourceNotFoundException("User", "id", userId);
        });

        return userTodto(user);
    }

    @Override
    public List<UserDto> getallUsers() {

        List<User> userlist = (List<User>) userRepo.findAll();
        List<UserDto> userdtoList = userlist.stream().map((user) -> userTodto(user)).collect(Collectors.toList()); // convertedtodto

        return userdtoList;
    }

    @Override
    public void deleteUser(Long userId) {

        User findUser = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        userRepo.delete(findUser);
    }

    // mappers between dto and user

    public User dtoTouser(UserDto userdto) {
        User user = modelMapper.map(userdto, User.class);
        return user;
    }

    public UserDto userTodto(User user) {
        UserDto userdto = modelMapper.map(user, UserDto.class);
        return userdto;
    }

}
