package io.mkolodziejczyk92.data.controllers;

import io.mkolodziejczyk92.data.entity.Address;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.data.service.AddressService;
import io.mkolodziejczyk92.views.address.ClientAddressesView;
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

    public List<Address> clientAddresses(Client client) {
        return addressService.clientAddresses(client);
    }
}
