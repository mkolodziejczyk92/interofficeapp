package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import io.mkolodziejczyk92.data.entity.Supplier;
import io.mkolodziejczyk92.data.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class SuppliersViewController {


    private final SupplierService supplierService;


    public SuppliersViewController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    public List<Supplier> allSuppliers() {
        return supplierService.allSuppliers();
    }

    public void deleteSupplier(Supplier supplier) {
        try {
            supplierService.delete(supplier.getId());
        } catch (DataIntegrityViolationException e) {
            Notification.show(supplier.getNameOfCompany()
                    + " cannot be deleted because it has connections in the database.");
            return;
        }
        Notification.show(supplier.getNameOfCompany() + " deleted.");
    }

    public void showAllPurchasesForSupplier(Supplier supplier) {
        UI.getCurrent().navigate("purchases/s" + supplier.getId());
    }
}
