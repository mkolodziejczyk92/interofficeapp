package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
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


    public Address findAddressById(Long addressId) {
        return addressService.get(addressId).orElseThrow();
    }

    public void updateAddress(Address address) {
        addressService.update(address);
        Notification.show("Address updated.");
        UI.getCurrent().navigate("addresses");

    }

    public void validateAndSave(Address address) {
        try {
            binder.writeBean(address);
            addressService.save(address);
            Notification.show(address.getClient().getFullName() + "'s address stored.");
            UI.getCurrent().navigate("addresses");
        } catch (ValidationException ex) {
            log.error(ex.getMessage(), ex);
            Notification.show("Validate Error");
        }
    }
}
