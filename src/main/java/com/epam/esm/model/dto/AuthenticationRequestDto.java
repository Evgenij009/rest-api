package com.epam.esm.model.dto;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;
import java.util.StringJoiner;

public class AuthenticationRequestDto {
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;

    public AuthenticationRequestDto() {}

    public AuthenticationRequestDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationRequestDto that = (AuthenticationRequestDto) o;
        return Objects.equals(login, that.login) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AuthenticationRequestDto.class.getSimpleName() + "[", "]")
                .add("login='" + login + "'")
                .add("password='" + password + "'")
                .toString();
    }
}
