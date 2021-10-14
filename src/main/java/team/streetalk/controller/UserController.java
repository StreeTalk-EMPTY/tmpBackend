package team.streetalk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.streetalk.dto.LoginRequest;
import team.streetalk.dto.LoginResponse;
import team.streetalk.dto.MessageOnly;
import team.streetalk.dto.MessageWithData;
import team.streetalk.dto.sms.SmsResponse;
import team.streetalk.service.SmsService;
import team.streetalk.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final SmsService smsService;

    @PostMapping("/users/login")
    public ResponseEntity<MessageWithData> signIn(@RequestBody LoginRequest loginRequest) {
        LoginResponse data = userService.login(loginRequest);
        return new ResponseEntity<>(new MessageWithData(200, true, "Gave you home", data), HttpStatus.OK);
    }

    @PostMapping("/user/test")
    public ResponseEntity<MessageWithData> test(HttpServletRequest req) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        System.out.println("do something");
        SmsResponse data = smsService.sendSms("01030323682", "success!!!");
        return new ResponseEntity<>(new MessageWithData(200, true, "nice", data), HttpStatus.OK);
    }

    @GetMapping("/onlytest")
    public void dodo(HttpServletRequest req){
        System.out.println("hihi");
    }


}
