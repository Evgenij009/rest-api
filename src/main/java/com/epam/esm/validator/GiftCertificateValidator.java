package com.epam.esm.validator;

import com.epam.esm.exception.InvalidEntityException;
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

    public boolean isNameValid(String name) {
        return name != null
                && name.length() >= NAME_MIN_LENGTH
                && name.length() <= NAME_MAX_LENGTH;
    }

    public boolean isDescriptionValid(String description) {
        return description != null
                && description.length() >= DESCRIPTION_MIN_LENGTH
                && description.length() <= DESCRIPTION_MAX_LENGTH;
    }

    public boolean isPriceValid(BigDecimal price) {
        return price != null
                && price.compareTo(PRICE_MIN_VALUE) > 0
                && price.compareTo(PRICE_MAX_VALUE) < 0;
    }

    public boolean isDurationValid(int duration) {
        return duration >= DURATION_MIN_VALUE;
    }

    public void isValidateCarefullyGiftCertificateValues(GiftCertificate giftCertificate) {
        String name = giftCertificate.getName();
        if (name != null && !isNameValid(name)) {
            throw new InvalidEntityException("validation.name.invalid");
        }
        String description = giftCertificate.getDescription();
        if (description != null && !isDescriptionValid(description)) {
            throw new InvalidEntityException("validation.description.invalid");
        }
        BigDecimal price = giftCertificate.getPrice();
        if (price != null && !isPriceValid(price)) {
            throw new InvalidEntityException("validation.price.invalid");
        }
        int duration = giftCertificate.getDuration();
        if (duration != 0 && !isDurationValid(duration)) {
            throw new InvalidEntityException("validation.duration.invalid");
        }
    }
}
