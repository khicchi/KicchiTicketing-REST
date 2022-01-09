package com.lespania.controller;

import com.lespania.annotation.DefaultExceptionMessage;
import com.lespania.dto.UserDTO;
import com.lespania.entity.ResponseWrapper;
import com.lespania.exception.TicketingProjectException;
import com.lespania.service.ConfirmationTokenService;
import com.lespania.service.RoleService;
import com.lespania.service.UserService;
import com.lespania.util.MapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Value("${app.local-url}")
    private String BASE_URL;

    private UserService userService;
    private MapperUtil mapperUtil;
    private RoleService roleService;
    private ConfirmationTokenService confirmationTokenService;

    public UserController(UserService userService, MapperUtil mapperUtil, RoleService roleService,
                          ConfirmationTokenService confirmationTokenService) {
        this.userService = userService;
        this.mapperUtil = mapperUtil;
        this.roleService = roleService;
        this.confirmationTokenService = confirmationTokenService;
    }

    @DefaultExceptionMessage(defaultMessage = "Something went wrong, try again!")
    @PostMapping("/create-user")
    @Operation(summary = "Create new account")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<ResponseWrapper> doRegister(@RequestBody UserDTO userDTO)
            throws TicketingProjectException {
        UserDTO createdUser = userService.save(userDTO);

        return ResponseEntity.ok(new ResponseWrapper(
                "User has been created!",createdUser));
    }
}
