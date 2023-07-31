package com.example.activitytracker.controllers;


import com.example.activitytracker.models.request.TaskRequest;
import com.example.activitytracker.models.request.UserRequest;
import com.example.activitytracker.services.TaskService;
import com.example.activitytracker.utils.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @PostMapping()
    public ResponseEntity<GenericResponse> createTask(@RequestBody TaskRequest taskRequest, HttpServletRequest request){
        GenericResponse genericResponse = taskService.createTask(taskRequest, request);
        return new ResponseEntity<>(genericResponse, genericResponse.getHttpStatus());
    }

    @GetMapping
    public ResponseEntity<GenericResponse> findAllTaskByUser( HttpServletRequest request){
        GenericResponse genericResponse = taskService.findAllTaskByUser(request);
        return new ResponseEntity<>(genericResponse, genericResponse.getHttpStatus());
    }


    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse> findTaskById(HttpServletRequest request, @PathVariable("id") Long id){
        GenericResponse genericResponse = taskService.findTaskById(id, request);
        return new ResponseEntity<>(genericResponse, genericResponse.getHttpStatus());
    }

    @GetMapping("/")
    public ResponseEntity<GenericResponse> findTaskByStatus(HttpServletRequest request, @RequestParam("status") String status){
        GenericResponse genericResponse = taskService.findTaskByStatus(request, status);
        return new ResponseEntity<>(genericResponse, genericResponse.getHttpStatus());
    }


    @PostMapping("/{id}/")
    public ResponseEntity<GenericResponse> moveTaskByStatus(HttpServletRequest request, @RequestParam("status") String status, @PathVariable("id") Long id){
        GenericResponse genericResponse = taskService.moveTaskByStatus(request,status, id);
        return new ResponseEntity<>(genericResponse, genericResponse.getHttpStatus());
    }
}
