package io.mkolodziejczyk92.data.controllers;

import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.data.service.ClientService;
import io.mkolodziejczyk92.views.clients.ClientsView;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class ClientViewController {

    private ClientsView clientsView;

    private final ClientService clientService;


    public ClientViewController(ClientService clientService) {
        this.clientService = clientService;
    }

    public void initView(ClientsView clientsView) {
        this.clientsView = clientsView;
    }

    public List<Client> allClients(){
        return clientService.allClients();
    }
}
