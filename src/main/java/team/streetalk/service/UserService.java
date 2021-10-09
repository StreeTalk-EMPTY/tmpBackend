package team.streetalk.service;


import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.streetalk.ExceptionHandler.ArithmeticException;
import team.streetalk.domain.User;
import team.streetalk.dto.LoginRequest;
import team.streetalk.dto.LoginResponse;
import team.streetalk.jwt.JwtTokenProvider;
import team.streetalk.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Comparator;


@Service
@AllArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
        User existUser = userRepository.findByPhoneNum(loginRequest.getPhoneNum())
                .orElseGet(()->signup(loginRequest));
        try{
            loginResponse.setToken(doGenerateToken(loginRequest));
        }catch (Exception e){
            throw new ArithmeticException(404, "아이디 혹은 비밀번호가 일치하지 않습니다.");
        }
        return loginResponse;
    }

    @Transactional
    public User signup(LoginRequest loginRequest) {
        User user = new User();
        user.setPhoneNum(loginRequest.getPhoneNum());
        user.setPassword(passwordEncoder.encode(loginRequest.getPassword())); //id = email, pwd = loginId
        userRepository.save(user);
        return user;
    }

    public String doGenerateToken(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getPhoneNum(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return jwtTokenProvider.createToken(authenticate);
    }

    public User getCurrentUser(HttpServletRequest req) {
        return userRepository.findByPhoneNum(jwtTokenProvider.getPhoneNumFromToken(jwtTokenProvider.resolveToken(req)))
                .orElseThrow(() -> new RuntimeException("can't find who am i"));
    }

}
