package ivents.ivents_ui_support.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import ivents.ivents_ui_support.dto.response.EntityResponse;
import ivents.ivents_ui_support.dto.role_permission.UserDetailsData;
import ivents.ivents_ui_support.repository.PermissionRepository;
import ivents.ivents_ui_support.service.TokenBlacklistService;
import ivents.ivents_ui_support.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JWTFilterConfig extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final PermissionRepository permissionRepository;
    private final TokenBlacklistService tokenBlacklistService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String pathUrl = request.getServletPath();

        if (pathUrl.startsWith("/swagger") || pathUrl.startsWith("/v3/api-docs") || pathUrl.startsWith("/actuator")) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendJsonError(response, HttpStatus.UNAUTHORIZED.value(), "Missing or invalid token");
            return;
        }

        String token = authHeader.substring(7);

        if (tokenBlacklistService.isBlacklisted(token)) {
            sendJsonError(response, HttpStatus.UNAUTHORIZED.value(), "Token has been revoked");
            return;
        }

        Claims claims;
        try {
            claims = jwtUtil.getAllClaimsFromToken(token);
        } catch (ExpiredJwtException ex) {
            sendJsonError(response, HttpStatus.UNAUTHORIZED.value(), "Token has expired");
            return;
        } catch (JwtException | IllegalArgumentException ex) {
            sendJsonError(response, HttpStatus.UNAUTHORIZED.value(), "Invalid token");
            return;
        }

        // Extract user info from claims
        Long userId = claims.get("id", Long.class);
        String email = claims.get("email", String.class);
        String username = claims.get("username", String.class);
        Long roleId = claims.get("role_id", Long.class);

        List<String> permissions = (List<String>) claims.get("permissions");
        String path = request.getRequestURI();
        String method = request.getMethod();

        boolean allowed = permissions.stream()
                .map(p -> permissionRepository.findByPermissionName(p))
                .filter(o -> o.isPresent())
                .map(o -> o.get())
                .anyMatch(pe -> pe.getUri().equals(path) && pe.getVerb().equalsIgnoreCase(method));

        if (!allowed) {
            sendJsonError(response, HttpStatus.FORBIDDEN.value(), "Access denied");
            return;
        }

        List<GrantedAuthority> authorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetailsData userData = UserDetailsData.builder()
                .id(userId)
                .email(email)
                .username(username)
                .roleId(roleId)
                .build();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userData, null, authorities);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);



        chain.doFilter(request, response);
    }

    private void sendJsonError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        EntityResponse entityResponse = EntityResponse.builder()
                .status(false)
                .code(status)
                .message(message)
                .build();

        String json = objectMapper.writeValueAsString(entityResponse);
        response.getWriter().write(json);
    }
}