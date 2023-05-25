package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import io.mkolodziejczyk92.data.entity.Manufacturer;
import io.mkolodziejczyk92.data.service.ManufacturerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class ManufacturerFormViewController {

    private final ManufacturerService manufacturerService;
    private Binder<Manufacturer> binder;

    public ManufacturerFormViewController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    public void clearForm() {
        this.binder.setBean(new Manufacturer());
    }

    public void initBinder(Binder<Manufacturer> binder) {
        this.binder = binder;
    }

    public void saveNewManufacturer(Manufacturer manufacturer) {
        try {
            binder.writeBean(manufacturer);
            manufacturerService.saveNewManufacturer(manufacturer);
            Notification.show(manufacturer.getNameOfCompany() + " stored.");
            UI.getCurrent().navigate("manufacturers");
        } catch (ValidationException ex) {
            log.error(ex.getMessage(), ex);
            Notification.show("Validate Error");
        }
    }
}
