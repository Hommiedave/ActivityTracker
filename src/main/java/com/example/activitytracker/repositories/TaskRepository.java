package com.example.activitytracker.repositories;

import com.example.activitytracker.entities.Task;
import com.example.activitytracker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByUser(User user);
    List<Task> findAllByStatus(String status);
}
