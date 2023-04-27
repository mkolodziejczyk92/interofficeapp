package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.component.UI;
import io.mkolodziejczyk92.data.entity.Address;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.data.service.AddressService;
import io.mkolodziejczyk92.views.address.ClientAddressesView;
import io.mkolodziejczyk92.views.address.NewAddressFormView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class ClientAddressesViewController {

    private ClientAddressesView clientAddressesView;
    private final AddressService addressService;

    public ClientAddressesViewController(AddressService addressService) {
        this.addressService = addressService;
    }

    public void initView(ClientAddressesView clientAddressesView) {
        this.clientAddressesView = clientAddressesView;
    }

    public List<Address> clientAddresses(Long id) {
        return addressService.clientAddresses(id);
    }

    public void createNewAddressFormForClient(Long clientId) {
        UI.getCurrent().navigate("newAddress/" + clientId);
    }
}

