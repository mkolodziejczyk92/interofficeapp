package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.component.UI;
import io.mkolodziejczyk92.data.entity.Address;
import io.mkolodziejczyk92.data.service.AddressService;
import io.mkolodziejczyk92.views.address.AddressesView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class AddressesViewController {


    private AddressesView addressesView;
    private final AddressService addressService;

    public AddressesViewController(AddressService addressService) {
        this.addressService = addressService;
    }

    public void initView(AddressesView addressesView) {
        this.addressesView = addressesView;
    }

    public List<Address> allAddresses() {
        return addressService.allAddresses();

    }
    public void createNewAddressForm() {
        UI.getCurrent().navigate("newAddress");
    }
}

