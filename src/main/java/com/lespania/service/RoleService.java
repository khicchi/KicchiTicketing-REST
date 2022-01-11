package com.lespania.service;

import com.lespania.dto.RoleDTO;
import com.lespania.exception.TicketingProjectException;

import java.util.List;

public interface RoleService {

    List<RoleDTO> listAllRoles();

    RoleDTO findById(Long id) throws TicketingProjectException;
}
