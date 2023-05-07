package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;
import io.mkolodziejczyk92.data.entity.Address;
import io.mkolodziejczyk92.data.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class AddressFormViewController {

    private AddressService addressService;
    private Binder<Address> binder;

    public AddressFormViewController(AddressService addressService) {
        this.addressService = addressService;
    }

    public void initBinder(Binder<Address> binder) {
        this.binder = binder;
    }

    public void clearForm() {
        this.binder.setBean(new Address());
    }

    public void saveNewAddress(Address address) {
        addressService.saveNewAddress(address);
        Notification.show("Address stored.");
        UI.getCurrent().navigate("client-addresses/" + address.getClient().getId());
    }
}
