package com.epam.esm.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class GiftCertificate {
    private long id;
    private String name;
    private BigDecimal price;
    private Date date;
    private int duration;
    private Date createDate;
    private Date lastUpdateDate;
}