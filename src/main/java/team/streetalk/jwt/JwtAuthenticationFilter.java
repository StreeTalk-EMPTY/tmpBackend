package team.streetalk.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {  // * 클래스로서 요청당 한번의 filter를 수행하도록 * //

    //Authorization값을 꺼내어 토큰을 검사하고 해당 유저가 실제 DB에 있는지 검사하는 등의 전반적인 인증처리
    private final CustomUserDetailService customUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //토큰에 헤드를 넣어 넘길때 : "Bearer "+ token 으로 만들어서 Authorization: 에 넣어 보내기
        if(request.getHeader("Authorization") == null){
            filterChain.doFilter(request,response);
            return;
        }
        String jwtToken = jwtTokenProvider.resolveToken(request);
        String email = jwtTokenProvider.getEmailFromToken(jwtToken);

        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
            try{
                if(jwtTokenProvider.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception ex) {
                //this is very important, since it guarantees the user is not authenticated at all
                SecurityContextHolder.clearContext();
                System.out.println("error in building securityContextholder");
                return;
            }
        }

        System.out.println("done filtering");
        filterChain.doFilter(request,response);
    }



}