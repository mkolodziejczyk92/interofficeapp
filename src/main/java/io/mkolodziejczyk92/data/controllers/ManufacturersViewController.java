package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import io.mkolodziejczyk92.data.entity.Manufacturer;
import io.mkolodziejczyk92.data.service.ManufacturerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Slf4j
public class ManufacturersViewController {

    private final ManufacturerService manufacturerService;


    public ManufacturersViewController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    public List<Manufacturer> allManufacturers() {
        return manufacturerService.allManufacturers();
    }

    public void showAllPurchasesForManufacturer(Manufacturer manufacturer) {
        UI.getCurrent().navigate("purchases/m" + manufacturer.getId());
    }

    public boolean deleteManufacturer(Manufacturer manufacturer) {
        try {
            manufacturerService.delete(manufacturer.getId());
        } catch (DataIntegrityViolationException e) {
            Notification.show(manufacturer.getNameOfCompany()
                    + " cannot be deleted because it has connections in the database.");
            return false;
        }
        Notification.show(manufacturer.getNameOfCompany() + " deleted.");
            return true;
    }
}
