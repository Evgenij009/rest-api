package com.epam.esm.model.entity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "certificate_id", nullable = false)
    private GiftCertificate giftCertificate;

    @Column(name = "order_date", nullable = false, updatable = false)
    private ZonedDateTime orderDate;

    @Column(nullable = false, updatable = false)
    @DecimalMin(value = "1", message = "Cost should be >= 1")
    private BigDecimal cost;

    public Order() {}

    @PrePersist
    public void onCreate() {
        orderDate = ZonedDateTime.now();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificate giftCertificate) {
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

        Order order = (Order) o;

        if (user != null ? !user.equals(order.user) : order.user != null) return false;
        if (giftCertificate != null ? !giftCertificate.equals(order.giftCertificate) : order.giftCertificate != null)
            return false;
        if (orderDate != null ? !orderDate.equals(order.orderDate) : order.orderDate != null) return false;
        return cost != null ? cost.equals(order.cost) : order.cost == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (giftCertificate != null ? giftCertificate.hashCode() : 0);
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("user=").append(user);
        sb.append(", giftCertificate=").append(giftCertificate);
        sb.append(", orderDate=").append(orderDate);
        sb.append(", cost=").append(cost);
        sb.append('}');
        return sb.toString();
    }
}
