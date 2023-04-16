package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.data.binder.Binder;
import io.mkolodziejczyk92.data.entity.Address;
import io.mkolodziejczyk92.data.service.AddressService;
import io.mkolodziejczyk92.views.address.NewAddressFormView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class AddressNewFormViewController {

    private NewAddressFormView newAddressFormView;

    private AddressService addressService;
    private Binder<Address> binder;

    public AddressNewFormViewController(AddressService addressService) {
        this.addressService = addressService;
    }

    public void initView(NewAddressFormView newAddressFormView, Binder<Address> binder) {
        this.newAddressFormView = newAddressFormView;
        this.binder = binder;

    }

    public void clearForm() {
        this.binder.setBean(new Address());
    }

    public void saveNewAddress(Address address) {
        addressService.saveNewAddress(address);
    }

    public List<Address> allAddresses() {
        return addressService.allAddresses();

    }
}