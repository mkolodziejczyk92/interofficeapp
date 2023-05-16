package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import io.mkolodziejczyk92.data.entity.Supplier;
import io.mkolodziejczyk92.data.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class SupplierFormViewController {

    private final SupplierService supplierService;
    private Binder<Supplier> binder;

    public SupplierFormViewController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    public void initBinder(Binder<Supplier> binder) {
        this.binder = binder;
    }

    public void clearForm() {
        this.binder.setBean(new Supplier());

    }

    public void saveNewSupplier(Supplier supplier) {
        try {
            binder.writeBean(supplier);
            supplierService.saveNewSupplier(supplier);
            Notification.show(supplier.getNameOfCompany() + " stored.");
            UI.getCurrent().navigate("suppliers");
        } catch (ValidationException ex) {
            log.error(ex.getMessage(), ex);
            Notification.show("Validate Error");
        }
    }
}
