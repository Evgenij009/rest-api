package com.epam.esm.model.dto;

import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class OrderDto extends RepresentationModel<OrderDto> {
    private long id;
    private UserDto user;
    private GiftCertificateDto giftCertificate;
    private ZonedDateTime orderDate;
    private BigDecimal cost;

    public OrderDto() {}

    public OrderDto(long id, UserDto user, GiftCertificateDto giftCertificate, ZonedDateTime orderDate, BigDecimal cost) {
        this.id = id;
        this.user = user;
        this.giftCertificate = giftCertificate;
        this.orderDate = orderDate;
        this.cost = cost;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public GiftCertificateDto getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificateDto giftCertificate) {
        this.giftCertificate = giftCertificate;
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrderDto orderDto = (OrderDto) o;

        if (id != orderDto.id) return false;
        if (user != null ? !user.equals(orderDto.user) : orderDto.user != null) return false;
        if (giftCertificate != null ? !giftCertificate.equals(orderDto.giftCertificate) : orderDto.giftCertificate != null)
            return false;
        if (orderDate != null ? !orderDate.equals(orderDto.orderDate) : orderDto.orderDate != null) return false;
        return cost != null ? cost.equals(orderDto.cost) : orderDto.cost == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (giftCertificate != null ? giftCertificate.hashCode() : 0);
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderDto{");
        sb.append("id=").append(id);
        sb.append(", user=").append(user);
        sb.append(", giftCertificate=").append(giftCertificate);
        sb.append(", orderDate=").append(orderDate);
        sb.append(", cost=").append(cost);
        sb.append('}');
        return sb.toString();
    }
}
