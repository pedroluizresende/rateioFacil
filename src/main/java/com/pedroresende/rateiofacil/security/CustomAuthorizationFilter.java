package com.pedroresende.rateiofacil.security;

import com.pedroresende.rateiofacil.models.entities.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filtro para verificação do id do usuário logado.
 */
@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    if (isUserIdRequest(request)) {
      Long id = extractIdFromRequest(request);
      if (isAdmin()) {
        filterChain.doFilter(request, response);
      }
      if (!hasPermission(id)) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return;
      }
    }
    filterChain.doFilter(request, response);
  }

  private boolean isUserIdRequest(HttpServletRequest request) {
    if ("GET".equals(request.getMethod()) && request.getRequestURI().matches("/users/\\d+")) {
      return true;
    }

    if ("PUT".equals(request.getMethod()) && request.getRequestURI().matches("/users/\\d+")) {
      return true;
    }
    if ("DELETE".equals(request.getMethod()) && request.getRequestURI().matches("/users/\\d+")) {
      return true;
    }

    if ("POST".equals(request.getMethod()) && request.getRequestURI()
        .matches("/users/\\d+/bills")) {
      return true;
    }
    if ("GET".equals(request.getMethod()) && request.getRequestURI().matches("/users/\\d+/bills")) {
      return true;
    }

    if ("GET".equals(request.getMethod()) && request.getRequestURI()
        .matches("/users/\\d+/bills/\\d+")) {
      return true;
    }
    return false;
  }

  private Long extractIdFromRequest(HttpServletRequest request) {
    String[] pathSegments = request.getRequestURI().split("/");
    if (pathSegments.length >= 3) {
      return Long.parseLong(pathSegments[2]);
    }
    return null;
  }

  private boolean isAdmin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    return authentication.getAuthorities().stream()
        .anyMatch(authority -> authority.getAuthority().equals("admin"));
  }

  private boolean hasPermission(Long id) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) authentication.getPrincipal();
    return user.getId().equals(id);
  }
}
