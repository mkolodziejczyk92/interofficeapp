package io.mkolodziejczyk92.views.purchase;

import io.mkolodziejczyk92.data.entity.Purchase;

import java.util.Optional;

public class PurchaseFilter {

    private String searchTerm;

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public boolean test(Purchase purchase) {
        boolean matchesClientFullName = false;
        boolean matchesSupplierName = false;
        boolean matchesSupplierPurchaseNumber = false;
        boolean matchesContractNumber = false;


        if (Optional.ofNullable(purchase.getClient().getFullName()).isPresent()) {
            matchesClientFullName = matches(purchase.getClient().getFullName(), searchTerm);
        }
        if (Optional.ofNullable(purchase.getSupplier().getNameOfCompany()).isPresent()) {
            matchesSupplierName = matches(purchase.getSupplier().getNameOfCompany(), searchTerm);
        }
        if (Optional.ofNullable(purchase.getSupplierPurchaseNumber()).isPresent()) {
            matchesSupplierPurchaseNumber = matches(purchase.getSupplierPurchaseNumber(), searchTerm);
        }
        if (Optional.ofNullable(purchase.getContractNumber()).isPresent()) {
            matchesContractNumber = matches(purchase.getContractNumber(), searchTerm);
        }


        return matchesClientFullName || matchesSupplierName
                || matchesSupplierPurchaseNumber || matchesContractNumber;
    }

    private boolean matches(String value, String searchTerm) {
        return searchTerm == null || searchTerm.isEmpty()
                || value.toLowerCase().contains(searchTerm.toLowerCase());
    }
}
