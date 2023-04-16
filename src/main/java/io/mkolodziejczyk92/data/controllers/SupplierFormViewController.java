package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.data.binder.Binder;
import io.mkolodziejczyk92.data.entity.Supplier;
import io.mkolodziejczyk92.data.service.SupplierService;
import io.mkolodziejczyk92.views.supplier.NewSupplierFormView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class SupplierFormViewController {

    private final SupplierService supplierService;
    private NewSupplierFormView newSupplierFormView;
    private Binder<Supplier> binder;

    public SupplierFormViewController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    public void initView(NewSupplierFormView newSupplierFormView, Binder<Supplier> binder) {
        this.newSupplierFormView = newSupplierFormView;
        this.binder = binder;
    }

    public void clearForm() {
        this.binder.setBean(new Supplier());

    }

    public void saveNewSupplier(Supplier supplier) {
        supplierService.saveNewSupplier(supplier);
    }
}
