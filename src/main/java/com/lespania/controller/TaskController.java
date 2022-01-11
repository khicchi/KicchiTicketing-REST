package com.lespania.controller;

import com.lespania.annotation.DefaultExceptionMessage;
import com.lespania.dto.TaskDTO;
import com.lespania.entity.ResponseWrapper;
import com.lespania.enums.Status;
import com.lespania.service.ProjectService;
import com.lespania.service.TaskService;
import com.lespania.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/task")
@Tag(name = "Task Controller",description = "Task API")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @DefaultExceptionMessage(defaultMessage = "Something went wrong,please try again!")
    @Operation(summary = "Read all tasks")
    @PreAuthorize("hasAuthority('Manager')")
    public ResponseEntity<ResponseWrapper> readAll(){
        return ResponseEntity.ok(new ResponseWrapper("Successfully retrieved all tasks",
                                                            taskService.listAllTasks()));
    }

}
