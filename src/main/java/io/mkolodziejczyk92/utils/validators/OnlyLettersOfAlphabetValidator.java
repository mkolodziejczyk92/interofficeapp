package io.mkolodziejczyk92.utils.validators;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;

public class OnlyLettersOfAlphabetValidator implements Validator<String> {

    private static final String FIRST_NAME_AND_LAST_NAME_REGEX = "[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]{1,50}";

    public OnlyLettersOfAlphabetValidator() {
    }

    @Override
    public ValidationResult apply(String value, ValueContext valueContext) {
        if (value == null || value.isBlank()) {
            return ValidationResult.error("This field  is required");
        }
        if (!value.matches(FIRST_NAME_AND_LAST_NAME_REGEX)) {
            return ValidationResult.error("Only letters of the  alphabet are allowed");
        }
        return ValidationResult.ok();
    }
}
