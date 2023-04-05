package io.mkolodziejczyk92.views.address;

import io.mkolodziejczyk92.data.entity.Address;

import java.util.Optional;

public class AddressFilter {

    private String searchTerm;

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public boolean test(Address address) {
        boolean matchesClientFullName = false;
        boolean matchesStreet = false;
        boolean matchesHouseNumber = false;
        boolean matchesApartmentNumber = false;
        boolean matchesZipCode = false;
        boolean matchesCity = false;
        boolean matchesVoivodeship = false;
        boolean matchesPlotNumber = false;

        if (Optional.ofNullable(address.getClient().getFullName()).isPresent()) {
            matchesClientFullName = matches(address.getClient().getFullName(), searchTerm);
        }
        if (Optional.ofNullable(address.getStreet()).isPresent()) {
            matchesStreet = matches(address.getStreet(), searchTerm);
        }
        if (Optional.ofNullable(address.getHouseNumber()).isPresent()) {
            matchesHouseNumber = matches(address.getHouseNumber(), searchTerm);
        }
        if (Optional.ofNullable(address.getApartmentNumber()).isPresent()) {
            matchesApartmentNumber = matches(address.getApartmentNumber(), searchTerm);
        }
        if (Optional.ofNullable(address.getZipCode()).isPresent()) {
            matchesZipCode = matches(address.getZipCode(), searchTerm);
        }
        if (Optional.ofNullable(address.getCity()).isPresent()) {
            matchesCity = matches(address.getCity(), searchTerm);
        }
        if (Optional.ofNullable(address.getVoivodeship()).isPresent()) {
            matchesVoivodeship = matches(String.valueOf(address.getVoivodeship()), searchTerm); // POPRAWIC PARSTOSTRING NA WARTOSC Z ENUMA!!!
        }
        if (Optional.ofNullable(address.getPlotNumber()).isPresent()) {
            matchesPlotNumber = matches(address.getPlotNumber(), searchTerm);
        }

        return matchesClientFullName || matchesStreet || matchesHouseNumber
                || matchesApartmentNumber || matchesZipCode
                || matchesCity || matchesVoivodeship
                || matchesPlotNumber;
    }

    private boolean matches(String value, String searchTerm) {
        return searchTerm == null || searchTerm.isEmpty()
                || value.toLowerCase().contains(searchTerm.toLowerCase());
    }
}

