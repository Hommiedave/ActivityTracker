package com.example.activitytracker.services;


import com.example.activitytracker.entities.User;
import com.example.activitytracker.models.request.LoginRequest;
import com.example.activitytracker.models.request.UserRequest;
import com.example.activitytracker.models.response.UserResponse;
import com.example.activitytracker.repositories.UserRepository;
import com.example.activitytracker.utils.EntityMapper;
import com.example.activitytracker.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EntityMapper entityMapper;

    @Autowired
    public UserService(UserRepository userRepository, EntityMapper entityMapper) {
        this.userRepository = userRepository;
        this.entityMapper = entityMapper;
    }

    public GenericResponse createUser(UserRequest userRequest){
        Optional<User> optionalUser = userRepository.findByEmail(userRequest.getEmail());
        if (optionalUser.isPresent()){
            return new GenericResponse("User already exist", "01");
        }
        User user = entityMapper.dtoToUserMapper(userRequest);
        return new GenericResponse("Request Processed Successfully", "00",
                entityMapper.userToDtoMapper(userRepository.save(user)));
//        User savedUser = userRepository.save(user);
//        UserResponse userResponse = entityMapper.userToDtoMapper(savedUser);
//        return new GenericResponse("Request Processed Successfully", "00", userResponse);
    }

    public GenericResponse login(LoginRequest loginRequest, HttpServletRequest request){
        GenericResponse genericResponse = new GenericResponse();
        Optional<User> optionalUser = userRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        if (optionalUser.isEmpty()){
            genericResponse.setMessage("Invalid Request");
            genericResponse.setStatus("01");
            genericResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            return genericResponse;
        }
        UserResponse userResponse = entityMapper.userToDtoMapper(optionalUser.get());
        HttpSession session = request.getSession();
        session.setAttribute("userResponse", userResponse);
        genericResponse.setMessage("Request Processed Successfully");
        genericResponse.setStatus("00");
        genericResponse.setHttpStatus(HttpStatus.OK);
        return genericResponse;
    }
}
