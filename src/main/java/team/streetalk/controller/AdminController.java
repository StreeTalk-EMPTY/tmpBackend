package team.streetalk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.streetalk.dto.LoginRequest;
import team.streetalk.dto.LoginResponse;
import team.streetalk.dto.MessageWithData;
import team.streetalk.service.UserService;


@RestController
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;



}
