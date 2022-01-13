package com.epam.esm.util.security;

import com.epam.esm.model.dto.UserResponseDto;
import com.epam.esm.service.UserService;
import com.epam.esm.util.security.jwt.JwtTokenProvider;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserAccessService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public UserAccessService(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    public void checkAccess(HttpServletRequest httpServletRequest, long userId) {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String login = jwtTokenProvider.getLogin(token);
        List<String> roles = jwtTokenProvider.getRoles(token);
        UserResponseDto userDto = userService.findByLogin(login);
        if ((userDto.getId() != userId) && !roles.contains("ROLE_ADMIN")) {
            throw new AuthorizationServiceException("exception.no.access");
        }
    }
}
