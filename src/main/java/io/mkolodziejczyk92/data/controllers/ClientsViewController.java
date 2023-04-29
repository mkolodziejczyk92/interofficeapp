package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.component.UI;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.data.service.ClientService;
import io.mkolodziejczyk92.views.client.ClientsView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class ClientsViewController {

    private ClientsView clientsView;

    private final ClientService clientService;


    public ClientsViewController(ClientService clientService) {
        this.clientService = clientService;
    }

    public void initView(ClientsView clientsView) {
        this.clientsView = clientsView;
    }

    public List<Client> allClients(){
        return clientService.allClients();
    }

    public void clientAddresses(Long clientId) {
        UI.getCurrent().navigate("clientAddresses/" + clientId);
    }
    public Client findClientById(Long clientId) {
        return clientService.get(clientId).orElseThrow();
    }

    public void editClientInformationForm(Long clientId) {
        UI.getCurrent().navigate("newClient/" + clientId);
    }
}
