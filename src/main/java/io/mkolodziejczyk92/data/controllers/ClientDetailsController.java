package io.mkolodziejczyk92.data.controllers;

import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.data.service.ClientService;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class ClientDetailsController {

    private ClientService clientService;

    public ClientDetailsController(ClientService clientService) {
        this.clientService = clientService;
    }


    public Optional<Client> findClientById(Long id){
       return clientService.get(id);
    }

}
