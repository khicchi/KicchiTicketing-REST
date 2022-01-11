package com.lespania.service;

import com.lespania.dto.ProjectDTO;
import com.lespania.entity.Project;
import com.lespania.entity.User;
import com.lespania.exception.TicketingProjectException;

import java.util.List;

public interface ProjectService {

    ProjectDTO getByProjectCode(String code);

    List<ProjectDTO> listAllProjects();

    ProjectDTO save(ProjectDTO dto) throws TicketingProjectException;

    ProjectDTO update(ProjectDTO dto) throws TicketingProjectException;

    void delete(String code);

    void complete(String projectCode);

    List<ProjectDTO> listAllProjectDetails();

    List<ProjectDTO> readAllByAssignedManager(User user);

    List<ProjectDTO> listAllNonCompletedProjects();
}
