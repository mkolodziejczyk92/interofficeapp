package io.mkolodziejczyk92.utils.validators;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;

public class NipValidator implements Validator<String> {

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        if (value == null || value.isEmpty()) {
            return ValidationResult.error("NIP is required");
        }

        String nip = value.replaceAll("\\s", "");

        if (!isValidNipFormat(nip)) {
            return ValidationResult.error("Incorrect NIP number");
        }

        if (!isValidNipChecksum(nip)) {
            return ValidationResult.error("Incorrect NIP number");
        }

        return ValidationResult.ok();
    }

    private boolean isValidNipFormat(String nip) {
        return nip.matches("\\d{10}");
    }

    private boolean isValidNipChecksum(String nip) {
        int[] weights = {6, 5, 7, 2, 3, 4, 5, 6, 7};
        int sum = 0;

        for (int i = 0; i < 9; i++) {
            sum += (nip.charAt(i) - '0') * weights[i];
        }

        int checksum = sum % 11;
        if (checksum == 10) {
            checksum = 0;
        }

        return checksum == (nip.charAt(9) - '0');
    }
}
