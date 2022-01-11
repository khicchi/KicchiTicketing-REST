package com.lespania.implementation;

import com.lespania.dto.RoleDTO;
import com.lespania.entity.Role;
import com.lespania.exception.TicketingProjectException;
import com.lespania.mapper.RoleMapper;
import com.lespania.repository.RoleRepository;
import com.lespania.service.RoleService;
import com.lespania.util.MapperUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    private MapperUtil mapperUtil;

    public RoleServiceImpl(RoleRepository roleRepository, MapperUtil mapperUtil) {
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<RoleDTO> listAllRoles() {
        List<Role> list = roleRepository.findAll();
        return list.stream().map(obj ->
                mapperUtil.convert(obj,new RoleDTO())).collect(Collectors.toList());
    }

    @Override
    public RoleDTO findById(Long id) throws TicketingProjectException {
        Role role = roleRepository.findById(id).orElseThrow(() ->
                        new TicketingProjectException("Role does not exists"));
        return mapperUtil.convert(role,new RoleDTO());
    }
}
