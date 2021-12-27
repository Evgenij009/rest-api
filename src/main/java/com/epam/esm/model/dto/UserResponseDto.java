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

public class UserResponseDto extends RepresentationModel<UserResponseDto> {

    private long id;
    @NotEmpty
    @Size(min = 1, max = 64, message = "Name length should be >= 1 and <= 64")
    private String name;
    private BigDecimal spentMoney;
    @NotEmpty
    @Size(min = 1, max = 64)
    private String login;
    @NotEmpty
    @Email
    @Size(min = 5, max = 64, message = "Email length should be >= 5 and <= 64")
    private String email;
    private Status status;
    private Set<Role> roles = new HashSet<>();

    /**
     * Instantiates a new User response dto.
     * Used mapper
     */
    public UserResponseDto() {}

    public UserResponseDto(long id, String name, BigDecimal spentMoney) {
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
        UserResponseDto that = (UserResponseDto) o;
        return id == that.id && Objects.equals(name, that.name)
                && Objects.equals(spentMoney, that.spentMoney)
                && Objects.equals(login, that.login)
                && Objects.equals(email, that.email)
                && status == that.status
                && Objects.equals(roles, that.roles);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (spentMoney != null ? spentMoney.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserResponseDto{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", spentMoney=").append(spentMoney);
        sb.append(", login='").append(login).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", status=").append(status);
        sb.append(", roles=").append(roles);
        sb.append('}');
        return sb.toString();
    }
}
