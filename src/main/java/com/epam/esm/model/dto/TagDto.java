package com.epam.esm.model.dto;

import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

public class TagDto extends RepresentationModel<TagDto> {

    private long id;
    @Size(min = 1, max = 64, message = "Name length should be >= 1 and <= 64")
    @NotEmpty(message = "Name should not be empty")
    private String name;

    /**
     * Instantiates a new Tag dto.
     * Used mapper
     */
    public TagDto() {}

    public TagDto(long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TagDto tagDto = (TagDto) o;
        return id == tagDto.id && Objects.equals(name, tagDto.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TagDto{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
