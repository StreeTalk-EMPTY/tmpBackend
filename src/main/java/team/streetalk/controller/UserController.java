package team.streetalk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.streetalk.dto.LoginRequest;
import team.streetalk.dto.LoginResponse;
import team.streetalk.dto.MessageWithData;
import team.streetalk.service.UserService;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users/login")
    public ResponseEntity<MessageWithData> signIn(@RequestBody LoginRequest loginRequest) {
        LoginResponse data = userService.login(loginRequest);
        return new ResponseEntity<>(new MessageWithData(200, true, "Gave you home", data), HttpStatus.OK);
    }


}
