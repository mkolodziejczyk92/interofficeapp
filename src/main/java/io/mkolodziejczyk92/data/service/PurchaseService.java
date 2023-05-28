package io.mkolodziejczyk92.data.service;

import io.mkolodziejczyk92.data.entity.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    private final PurchaseRepository repository;

    public PurchaseService(PurchaseRepository repository) {
        this.repository = repository;
    }

    public Optional<Purchase> get(Long id) {
        return repository.findById(id);
    }

    public void update(Purchase purchase) {
        repository.save(purchase);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Purchase> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Purchase> list(Pageable pageable, Specification<Purchase> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    public List<Purchase> allPurchases() {
        return repository.findAll();
    }

    public void save(Purchase purchase) {
        purchase.setGrossAmount(
                countGrossValue(purchase.getEVat().getVatValue(), purchase.getNetAmount()));
        BigDecimal netInput = new BigDecimal(purchase.getNetAmount().replace(',', '.'));
        BigDecimal netInputAfterScale = netInput.setScale(2, RoundingMode.HALF_UP);
        purchase.setNetAmount(String.valueOf(netInputAfterScale).replace('.', ','));
        repository.save(purchase);
    }

    private String countGrossValue(String vatValue, String netAmount) {
        String netInput;
        if (netAmount.contains(",")) {
            netInput = netAmount.replace(",", ".");
        } else {
            netInput = netAmount;
        }
        BigDecimal netValue = new BigDecimal(netInput);
        BigDecimal vat = new BigDecimal(vatValue);
        BigDecimal grossValue =
                netValue
                        .add(netValue.multiply(vat)
                                .divide(new BigDecimal(100), RoundingMode.HALF_UP)
                                .setScale(2, RoundingMode.HALF_UP));
        return String.valueOf(grossValue).replace(".", ",");
    }

    public List<Purchase> clientPurchases(Long clientId) {
        return repository.findPurchasesByClientId(clientId);
    }

    public boolean isExist(Long purchaseId) {
        return repository.existsById(purchaseId);
    }

    public List<Purchase> allPurchaseForSupplier(Long supplierId) {
        return repository.findPurchasesBySupplierId(supplierId);
    }

    public List<Purchase> allPurchasesForManufacturer(Long manufacturerId) {
        return repository.findPurchasesByManufacturerId(manufacturerId);
    }
}
