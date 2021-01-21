package ru.ndg.crudproject.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.ndg.crudproject.model.Role;
import ru.ndg.crudproject.model.User;

import java.util.Set;
import java.util.stream.Collectors;

public class SecurityUtil {

    public static void refreshRolesForAuthenticatedUser(User user) {
        Set<Role> roles = user.getRoles();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getName().equals(user.getNickname())) {
            Authentication token = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(),
                    roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet()));
            SecurityContextHolder.getContext().setAuthentication(token);
        }
    }
}
