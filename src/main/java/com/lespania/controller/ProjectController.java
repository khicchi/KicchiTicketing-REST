package com.lespania.controller;

import com.lespania.dto.ProjectDTO;
import com.lespania.dto.TaskDTO;
import com.lespania.dto.UserDTO;
import com.lespania.enums.Status;
import com.lespania.service.ProjectService;
import com.lespania.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private ProjectService projectService;
    private UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

}
