package io.mkolodziejczyk92.utils.validators;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PriceFormatValidator implements Validator<String> {


    private static final String PRICE_FORMAT_REGEX = "\\d+(\\,\\d{1,2})?";
    @Override
    public ValidationResult apply(String value, ValueContext valueContext) {

        if (value == null || value.isBlank()) {
            return ValidationResult.ok();
        }
        if (!value.matches(PRICE_FORMAT_REGEX)) {
            return ValidationResult.error("Enter the correct price format ex. 128,36 or 79");
        }
        return ValidationResult.ok();
    }
}
