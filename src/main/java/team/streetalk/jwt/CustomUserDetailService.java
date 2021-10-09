package team.streetalk.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import team.streetalk.domain.User;
import team.streetalk.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String phoneNum) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNum(phoneNum)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다." + phoneNum));
        System.out.println("build customUser");
        return CustomUserDetails.build(user);
    }
}
