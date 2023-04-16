package io.mkolodziejczyk92.views.invoice;

import io.mkolodziejczyk92.data.entity.Invoice;

import java.util.Optional;

public class InvoiceFilter {
    private String searchTerm;

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public boolean test(Invoice invoice) {
        boolean matchesClientFullName = false;
        boolean matchesInvoiceNumber = false;
        boolean matchesContractNumber = false;


        if (Optional.ofNullable(invoice.getClient().getFullName()).isPresent()) {
            matchesClientFullName = matches(invoice.getClient().getFullName(), searchTerm);
        }
        if (Optional.ofNullable(invoice.getNumber()).isPresent()) {
            matchesInvoiceNumber = matches(invoice.getNumber(), searchTerm);
        }
        if (Optional.ofNullable(invoice.getContract().getNumber()).isPresent()) {
            matchesContractNumber = matches(invoice.getContract().getNumber(), searchTerm);
        }
        return matchesClientFullName || matchesInvoiceNumber || matchesContractNumber;
    }

    private boolean matches(String value, String searchTerm) {
        return searchTerm == null || searchTerm.isEmpty()
                || value.toLowerCase().contains(searchTerm.toLowerCase());
    }
}
