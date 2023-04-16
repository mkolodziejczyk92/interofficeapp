package io.mkolodziejczyk92.views.contract;

import io.mkolodziejczyk92.data.entity.Contract;

import java.util.Optional;

public class ContractFilter {

    private String searchTerm;

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public boolean test(Contract contract) {
        boolean matchesClientFullName = false;
        boolean matchesNumber = false;

        if (Optional.ofNullable(contract.getClient().getFullName()).isPresent()) {
            matchesClientFullName = matches(contract.getClient().getFullName(), searchTerm);
        }
        if (Optional.ofNullable(contract.getNumber()).isPresent()) {
            matchesNumber = matches(contract.getNumber(), searchTerm);
        }

        return matchesClientFullName || matchesNumber;
    }

    private boolean matches(String value, String searchTerm) {
        return searchTerm == null || searchTerm.isEmpty()
                || value.toLowerCase().contains(searchTerm.toLowerCase());
    }
}

