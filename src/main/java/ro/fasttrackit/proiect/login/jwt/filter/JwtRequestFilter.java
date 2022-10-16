package ro.fasttrackit.proiect.login.jwt.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ro.fasttrackit.proiect.login.jwt.util.JwtTokenUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String jwtToken = getAuthorizationHeader(request);
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);

        if (authenticationRequired(username) && Boolean.TRUE.equals(jwtTokenUtil.tokenIsNotExpired(jwtToken))) {
            UserDetails userDetails = jwtTokenUtil.buildUser(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return Optional.ofNullable(path).orElse("").startsWith("/actuator");
    }

    private String getAuthorizationHeader(HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            return requestTokenHeader.substring(7);
        } else {
            return "";
        }
    }

    private static boolean authenticationRequired(String username) {
        return username != null && SecurityContextHolder.getContext().getAuthentication() == null;
    }
}
