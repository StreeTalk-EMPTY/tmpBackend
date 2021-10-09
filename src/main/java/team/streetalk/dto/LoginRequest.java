package team.streetalk.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class LoginRequest {
    private String PhoneNum;
    private String password;
}
