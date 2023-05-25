package io.mkolodziejczyk92.views.manufacturer;

import io.mkolodziejczyk92.data.entity.Manufacturer;

import java.util.Optional;

public class ManufacturerFilter {

    private String searchTerm;

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public boolean test(Manufacturer supplier) {
        boolean matchesNameOfCompany = false;
        boolean matchesNip = false;


        if (Optional.ofNullable(supplier.getNameOfCompany()).isPresent()) {
            matchesNameOfCompany = matches(supplier.getNameOfCompany(), searchTerm);
        }
        if (Optional.ofNullable(supplier.getNip()).isPresent()) {
            matchesNip = matches(supplier.getNip(), searchTerm);
        }


        return matchesNameOfCompany || matchesNip;
    }

    private boolean matches(String value, String searchTerm) {
        return searchTerm == null || searchTerm.isEmpty()
                || value.toLowerCase().contains(searchTerm.toLowerCase());
    }
}
