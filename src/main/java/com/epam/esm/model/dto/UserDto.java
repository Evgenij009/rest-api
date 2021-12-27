package com.epam.esm.model.dto;

import com.epam.esm.model.entity.Role;
import com.epam.esm.model.entity.Status;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

public class UserDto extends RepresentationModel<UserDto> {
    private long id;

    @NotEmpty
    @Size(min = 1, max = 64, message = "Name length should be >= 1 and <= 64")
    private String name;

    private BigDecimal spentMoney;

    @NotEmpty
    @Size(min = 1, max = 64)
    private String password;

    @NotEmpty
    @Email
    @Size(min = 5, max = 64, message = "Email length should be >= 5 and <= 64")
    private String email;

    private Status status;

    private Set<Role> roles = new HashSet<>();

    public UserDto() {}

    public UserDto(long id, String name, BigDecimal spentMoney) {
        this.id = id;
        this.name = name;
        this.spentMoney = spentMoney;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSpentMoney() {
        return spentMoney;
    }

    public void setSpentMoney(BigDecimal spentMoney) {
        this.spentMoney = spentMoney;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserDto userDto = (UserDto) o;
        return id == userDto.id && Objects.equals(name, userDto.name)
                && Objects.equals(spentMoney, userDto.spentMoney)
                && Objects.equals(password, userDto.password)
                && Objects.equals(email, userDto.email)
                && status == userDto.status
                && Objects.equals(roles, userDto.roles);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (spentMoney != null ? spentMoney.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("spentMoney=" + spentMoney)
                .add("password='" + password + "'")
                .add("email='" + email + "'")
                .add("status=" + status)
                .add("roles=" + roles)
                .toString();
    }
}
