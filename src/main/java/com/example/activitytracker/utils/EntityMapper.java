package com.example.activitytracker.utils;


import com.example.activitytracker.entities.User;
import com.example.activitytracker.models.request.UserRequest;
import com.example.activitytracker.models.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class EntityMapper {

    public User dtoToUserMapper(UserRequest userRequest){
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setPassword(userRequest.getPassword());
        return user;
    }

    public UserResponse userToDtoMapper(User savedUser){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(savedUser.getId());
        userResponse.setName(savedUser.getName());
        userResponse.setEmail(savedUser.getEmail());
        return userResponse;
    }
}
