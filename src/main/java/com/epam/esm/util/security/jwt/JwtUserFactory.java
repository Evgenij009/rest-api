package com.epam.esm.util.security.jwt;

import com.epam.esm.model.entity.Role;
import com.epam.esm.model.entity.Status;
import com.epam.esm.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUserFactory {

    private JwtUserFactory() {}

    public static  JwtUser create(User user) {
        return new JwtUser(user.getId(), user.getLogin(), user.getPassword(),
                user.getName(), user.getEmail(), user.getStatus().equals(Status.ACTIVE),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles())));
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(ArrayList<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
