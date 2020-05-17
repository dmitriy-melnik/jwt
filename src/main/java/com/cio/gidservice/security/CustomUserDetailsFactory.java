package com.cio.gidservice.security;

import com.cio.gidservice.model.Role;
import com.cio.gidservice.model.Status;
import com.cio.gidservice.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class CustomUserDetailsFactory {
    public CustomUserDetailsFactory() {
    }

    public static CustomUserDetails create(User user) {
        return new CustomUserDetails(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getPassword(),
                mapToGrantedAuthority(user.getRole()),
                user.getStatus().equals(Status.ACTIVE),
                user.getUpdated()
        );
    }

    /*private static List<GrantedAuthority> mapToGrantedAuthorities (Role role) {
        List<GrantedAuthority> result = new ArrayList<>();
        result.add(new SimpleGrantedAuthority(role.toString()));
        return result;

    }*/

    private static GrantedAuthority mapToGrantedAuthority(Role role) {
        return new SimpleGrantedAuthority(role.toString());
    }
}
