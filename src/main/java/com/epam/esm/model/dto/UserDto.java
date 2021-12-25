package com.epam.esm.model.dto;

import com.epam.esm.model.entity.Role;
import com.epam.esm.model.entity.Status;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class UserDto extends RepresentationModel<UserDto> {
    private long id;

    @NotEmpty
    @Size(min = 1, max = 64, message = "Name  length should be >= 1 and <= 64")
    private String name;

    private BigDecimal spentMoney;

    @NotEmpty
    @Size(min = 1, max = 64)
    private String password;

    @NotEmpty
    @Email
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

        if (id != userDto.id) return false;
        if (name != null ? !name.equals(userDto.name) : userDto.name != null) return false;
        if (spentMoney != null ? !spentMoney.equals(userDto.spentMoney) : userDto.spentMoney != null) return false;
        if (password != null ? !password.equals(userDto.password) : userDto.password != null) return false;
        if (email != null ? !email.equals(userDto.email) : userDto.email != null) return false;
        if (status != userDto.status) return false;
        return roles != null ? roles.equals(userDto.roles) : userDto.roles == null;
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
        final StringBuilder sb = new StringBuilder("UserDto{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", spentMoney=").append(spentMoney);
        sb.append(", password='").append(password).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", status=").append(status);
        sb.append(", roles=").append(roles);
        sb.append('}');
        return sb.toString();
    }
}
