package team.projectpulse.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import team.projectpulse.user.PeerEvaluationUser;
import team.projectpulse.user.UserRepository;

import java.io.IOException;

@Component
public class ActiveAccountFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    public ActiveAccountFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof Jwt jwt) {
            PeerEvaluationUser user = this.userRepository.findByUsername(jwt.getSubject()).orElse(null);
            if (user == null || !user.isEnabled()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User account is inactive.");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
