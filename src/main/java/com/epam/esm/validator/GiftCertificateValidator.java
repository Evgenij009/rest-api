package com.epam.esm.validator;

import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class GiftCertificateValidator implements Validator<GiftCertificate> {
    private static final int NAME_MIN_LENGTH = 1;
    private static final int NAME_MAX_LENGTH = 128;
    private static final int DESCRIPTION_MIN_LENGTH = 1;
    private static final int DESCRIPTION_MAX_LENGTH = 256;
    private static final BigDecimal PRICE_MIN_VALUE = BigDecimal.ONE;
    private static final BigDecimal PRICE_MAX_VALUE = new BigDecimal(Integer.MAX_VALUE);
    private static final int DURATION_MIN_VALUE = 1;

    @Override
    public boolean isValid(GiftCertificate item) {
        return isNameValid(item.getName())
                && isDescriptionValid(item.getDescription())
                && isPriceValid(item.getPrice())
                && isDurationValid(item.getDuration());
    }

    private boolean isNameValid(String name) {
        return name != null
                && name.length() > NAME_MIN_LENGTH
                && name.length() < NAME_MAX_LENGTH;
    }

    private boolean isDescriptionValid(String description) {
        return description != null
                && description.length() > DESCRIPTION_MIN_LENGTH
                && description.length() < DESCRIPTION_MAX_LENGTH;
    }

    private boolean isPriceValid(BigDecimal price) {
        return price != null
                && price.compareTo(PRICE_MIN_VALUE) > 0
                && price.compareTo(PRICE_MAX_VALUE) < 0;
    }

    private boolean isDurationValid(int duration) {
        return duration >= DURATION_MIN_VALUE;
    }
}
