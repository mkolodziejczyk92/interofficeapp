package io.mkolodziejczyk92.utils.validators;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;

public class PhoneNumberValidator implements Validator<String> {

    private static final String PHONE_NUMBER_REGEX = "[0-9]{9}";

    public PhoneNumberValidator() {
    }



    @Override
    public ValidationResult apply(String value, ValueContext valueContext) {

        if (value == null || value.isBlank()) {
            return ValidationResult.error("Phone number is required");
        }
        String number = value.replaceAll("\\s", "");
        if (!number.matches(PHONE_NUMBER_REGEX)) {
            return ValidationResult.error("Use correct phone number");
        }
        return ValidationResult.ok();
    }
}
