package com.lespania.controller;

import com.lespania.annotation.DefaultExceptionMessage;
import com.lespania.dto.MailDTO;
import com.lespania.dto.UserDTO;
import com.lespania.entity.ConfirmationToken;
import com.lespania.entity.ResponseWrapper;
import com.lespania.entity.User;
import com.lespania.exception.TicketingProjectException;
import com.lespania.service.ConfirmationTokenService;
import com.lespania.service.RoleService;
import com.lespania.service.UserService;
import com.lespania.util.MapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User Controller",description = "User API")
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
        sendEmail(createEmail(createdUser));
        return ResponseEntity.ok(new ResponseWrapper(
                "User has been created!",createdUser));
    }

    private MailDTO createEmail(UserDTO userDTO){
        User user = mapperUtil.convert(userDTO,new User());

        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationToken.setIsDeleted(false);

        ConfirmationToken createdConfirmationToken = confirmationTokenService.save(confirmationToken);

        return MailDTO
                .builder()
                .emailTo(user.getUserName())
                .token(createdConfirmationToken.getToken())
                .subject("Confirm Registration")
                .message("To confirm your account, please click here:")
                .url(BASE_URL + "/confirmation?token=")
                .build();
    }

    private void sendEmail(MailDTO mailDTO){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailDTO.getEmailTo());
        mailMessage.setSubject(mailDTO.getSubject());
        mailMessage.setText(mailDTO.getMessage() + mailDTO.getUrl() + mailDTO.getToken() );

        confirmationTokenService.sendEmail(mailMessage);
    }
}
