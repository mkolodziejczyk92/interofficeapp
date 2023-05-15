package io.mkolodziejczyk92.utils.validators;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;



public class ZipCodeValidator implements Validator<String> {

    private static final String ZIP_CODE_REGEX = "\\d{2}-\\d{3}";

    public ZipCodeValidator() {
    }


    @Override
    public ValidationResult apply(String value, ValueContext valueContext) {
        if (value == null || value.isBlank()) {
            return ValidationResult.error("Zip code is required");
        }
        if (!value.matches(ZIP_CODE_REGEX)) {
            return ValidationResult.error("Invalid zip code format. Insert in format XX-XXX");
        }
        return ValidationResult.ok();
    }
}

