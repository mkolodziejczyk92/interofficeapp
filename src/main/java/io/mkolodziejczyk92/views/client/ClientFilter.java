package io.mkolodziejczyk92.views.client;

import io.mkolodziejczyk92.data.entity.Client;

import java.util.Optional;

public class ClientFilter {

    private String searchTerm;

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public boolean test(Client client) {
        boolean matchesNip = false;
        boolean matchesFullName = matches(client.getFullName(), searchTerm);
        boolean matchesEmail = matches(client.getEmail(), searchTerm);
        if(Optional.ofNullable(client.getNip()).isPresent()) {
            matchesNip = matches(client.getNip(), searchTerm);
        }
        boolean matchesPhoneNumber = matches(client.getPhoneNumber(), searchTerm);
        return matchesFullName || matchesEmail  || matchesPhoneNumber || matchesNip;
    }

    private boolean matches(String value, String searchTerm) {
        return searchTerm == null || searchTerm.isEmpty()
                || value.toLowerCase().contains(searchTerm.toLowerCase());
    }
}

