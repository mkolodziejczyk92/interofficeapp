package io.mkolodziejczyk92.data.controllers;

import io.mkolodziejczyk92.data.entity.Supplier;
import io.mkolodziejczyk92.data.service.SupplierService;
import io.mkolodziejczyk92.views.supplier.SuppliersView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class SuppliersViewController {

    private SuppliersView suppliersView;

    private final SupplierService supplierService;

    public SuppliersViewController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    public void initView(SuppliersView suppliersView) {
        this.suppliersView = suppliersView;
    }

    public List<Supplier> allSuppliers() {
        return supplierService.supplierList();
    }

}
