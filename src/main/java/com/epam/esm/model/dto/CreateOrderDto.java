package com.epam.esm.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CreateOrderDto {

    @NotNull
    @Min(1)
    private long userId;
    @NotNull
    @Min(1)
    private long certificateId;

    public CreateOrderDto() {}

    public CreateOrderDto(long userId, long certificateId) {
        this.userId = userId;
        this.certificateId = certificateId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(long certificateId) {
        this.certificateId = certificateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateOrderDto that = (CreateOrderDto) o;
        return userId == that.userId && certificateId == that.certificateId;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (certificateId ^ (certificateId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CreateOrderDto{");
        sb.append("userId=").append(userId);
        sb.append(", certificateId=").append(certificateId);
        sb.append('}');
        return sb.toString();
    }
}
