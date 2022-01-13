package com.epam.esm.model.dto;

import java.util.Objects;
import java.util.StringJoiner;

public class AuthenticationResponseDto {
    private String login;
    private String token;

    public AuthenticationResponseDto() {}

    public AuthenticationResponseDto(String login, String token) {
        this.login = login;
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationResponseDto that = (AuthenticationResponseDto) o;
        return Objects.equals(login, that.login) && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, token);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AuthenticationResponseDto.class.getSimpleName() + "[", "]")
                .add("login='" + login + "'")
                .add("token='" + token + "'")
                .toString();
    }
}
