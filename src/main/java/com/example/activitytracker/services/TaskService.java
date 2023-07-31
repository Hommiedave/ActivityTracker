package com.example.activitytracker.services;


import com.example.activitytracker.entities.Task;
import com.example.activitytracker.entities.User;
import com.example.activitytracker.enums.TaskStatus;
import com.example.activitytracker.models.request.TaskRequest;
import com.example.activitytracker.models.response.TaskResponse;
import com.example.activitytracker.models.response.UserResponse;
import com.example.activitytracker.repositories.TaskRepository;
import com.example.activitytracker.repositories.UserRepository;
import com.example.activitytracker.utils.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }


    public GenericResponse createTask(TaskRequest taskRequest, HttpServletRequest request){
        GenericResponse genericResponse = new GenericResponse();
        HttpSession session = request.getSession();
        UserResponse userResponse = (UserResponse) session.getAttribute("userResponse");
        Optional<User> user = userRepository.findById(userResponse.getId());
        if (user.isEmpty()){
            genericResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            genericResponse.setStatus("01");
            genericResponse.setMessage("Invalid Request");
            return genericResponse;
        }
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(TaskStatus.PENDING);
        task.setUser(user.get());
        taskRepository.save(task);
        genericResponse.setHttpStatus(HttpStatus.CREATED);
        genericResponse.setStatus("00");
        genericResponse.setMessage("Request Processed Successfully");
        return genericResponse;
    }


    public GenericResponse findAllTaskByUser(HttpServletRequest request){
        GenericResponse genericResponse = new GenericResponse();
        HttpSession session = request.getSession();
        UserResponse userResponse = (UserResponse) session.getAttribute("userResponse");
        Optional<User> user = userRepository.findById(userResponse.getId());
        if (user.isEmpty()){
            genericResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            genericResponse.setStatus("01");
            genericResponse.setMessage("Invalid Request");
            return genericResponse;
        }
        List<TaskResponse> taskResponses = new ArrayList<>();
        List<Task> taskList = taskRepository.findAllByUser(user.get());
        taskList.forEach(task -> {
            TaskResponse taskResponse = new TaskResponse();
            taskResponse.setId(task.getId());
            taskResponse.setTitle(task.getTitle());
            taskResponse.setDescription(task.getDescription());
            taskResponse.setStatus(String.valueOf(task.getStatus()));
            taskResponse.setCreatedAt(task.getCreatedAt());
            taskResponse.setUpdatedAt(task.getUpdatedAt());
            taskResponse.setCompletedAt(task.getCompletedAt());
            taskResponses.add(taskResponse);
        });
        genericResponse.setHttpStatus(HttpStatus.OK);
        genericResponse.setStatus("00");
        genericResponse.setMessage("Request Processed Successfully");
        genericResponse.setData(taskResponses);
        return genericResponse;
    }


    public GenericResponse findTaskById(Long id, HttpServletRequest request){
        GenericResponse genericResponse = new GenericResponse();
        HttpSession session = request.getSession();
        UserResponse userResponse = (UserResponse) session.getAttribute("userResponse");
        Optional<User> user = userRepository.findById(userResponse.getId());
        if (user.isEmpty()){
            genericResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            genericResponse.setStatus("01");
            genericResponse.setMessage("Invalid Request");
            return genericResponse;
        }
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()){
            genericResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            genericResponse.setStatus("01");
            genericResponse.setMessage("Invalid Request");
            return genericResponse;
        }
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.get().getId());
        taskResponse.setTitle(task.get().getTitle());
        taskResponse.setDescription(task.get().getDescription());
        taskResponse.setStatus(String.valueOf(task.get().getStatus()));
        taskResponse.setCreatedAt(task.get().getCreatedAt());
        taskResponse.setUpdatedAt(task.get().getUpdatedAt());
        taskResponse.setCompletedAt(task.get().getCompletedAt());
        genericResponse.setHttpStatus(HttpStatus.OK);
        genericResponse.setStatus("00");
        genericResponse.setMessage("Request Processed Successfully");
        genericResponse.setData(taskResponse);
        return genericResponse;
    }


    public GenericResponse findTaskByStatus(HttpServletRequest request, String status){
        GenericResponse genericResponse = new GenericResponse();
        HttpSession session = request.getSession();
        UserResponse userResponse = (UserResponse) session.getAttribute("userResponse");
        Optional<User> user = userRepository.findById(userResponse.getId());
        if (user.isEmpty()){
            genericResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            genericResponse.setStatus("01");
            genericResponse.setMessage("Invalid Request");
            return genericResponse;
        }
        List<TaskResponse> taskResponses = new ArrayList<>();
        List<Task> taskList = taskRepository.findAllByStatus(status);
        taskList.forEach(task -> {
            TaskResponse taskResponse = new TaskResponse();
            taskResponse.setId(task.getId());
            taskResponse.setTitle(task.getTitle());
            taskResponse.setDescription(task.getDescription());
            taskResponse.setStatus(String.valueOf(task.getStatus()));
            taskResponse.setCreatedAt(task.getCreatedAt());
            taskResponse.setUpdatedAt(task.getUpdatedAt());
            taskResponse.setCompletedAt(task.getCompletedAt());
            taskResponses.add(taskResponse);
        });
        genericResponse.setHttpStatus(HttpStatus.OK);
        genericResponse.setStatus("00");
        genericResponse.setMessage("Request Processed Successfully");
        genericResponse.setData(taskResponses);
        return genericResponse;
    }


    public GenericResponse moveTaskByStatus(HttpServletRequest request, String status, Long id){
        GenericResponse genericResponse = new GenericResponse();
        HttpSession session = request.getSession();
        UserResponse userResponse = (UserResponse) session.getAttribute("userResponse");
        Optional<User> user = userRepository.findById(userResponse.getId());
        if (user.isEmpty()){
            genericResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            genericResponse.setStatus("01");
            genericResponse.setMessage("Invalid Request");
            return genericResponse;
        }
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()){
            genericResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            genericResponse.setStatus("01");
            genericResponse.setMessage("Invalid Request");
            return genericResponse;
        }
        if (status.equals("DONE")){
            task.get().setStatus(TaskStatus.DONE);
            task.get().setCompletedAt(LocalDateTime.now());
            taskRepository.save(task.get());
            genericResponse.setHttpStatus(HttpStatus.OK);
            genericResponse.setStatus("00");
            genericResponse.setMessage("Request Processed Successfully");
            return genericResponse;
        }else if (status.equals("IN_PROGRESS")){
            task.get().setStatus(TaskStatus.IN_PROGRESS);
            task.get().setCompletedAt(null);
            taskRepository.save(task.get());
            genericResponse.setHttpStatus(HttpStatus.OK);
            genericResponse.setStatus("00");
            genericResponse.setMessage("Request Processed Successfully");
            return genericResponse;
        }else {
            task.get().setStatus(TaskStatus.PENDING);
            task.get().setCompletedAt(null);
            taskRepository.save(task.get());
            genericResponse.setHttpStatus(HttpStatus.OK);
            genericResponse.setStatus("00");
            genericResponse.setMessage("Request Processed Successfully");
            return genericResponse;
        }
    }

}
