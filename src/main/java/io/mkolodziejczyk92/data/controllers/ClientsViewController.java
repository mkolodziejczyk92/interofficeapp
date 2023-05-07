package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.data.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class ClientsViewController {

    private final ClientService clientService;


    public ClientsViewController(ClientService clientService) {
        this.clientService = clientService;
    }

    public List<Client> allClients() {
        return clientService.allClients();
    }

    public void clientAddresses(Long clientId) {
        UI.getCurrent().navigate("client-addresses/" + clientId);
    }

    public void clientPurchases(Long clientId) {
        UI.getCurrent().navigate("purchases/c" + clientId);
    }

    public Client findClientById(Long clientId) {
        return clientService.get(clientId).orElseThrow();
    }

    public void deleteClient(Client client) {
        try {
            clientService.delete(client.getId());
        } catch (DataIntegrityViolationException e) {
            Notification.show("Client "
                    + client.getFullName()
                    + " cannot be deleted because it has connections in the database.");
            return;
        }
        Notification.show("Client " + client.getFullName() + " deleted.");
    }

    public void editClientInformationForm(Client client) {
        if (clientService.isExist(client.getId())) {
            UI.getCurrent().navigate("new-client/" + client.getId());
        } else {
            Notification.show("Client "
                    + client.getFullName()
                    + " does not exist in the database.");
        }
    }

    public void clientInvoices(Long clientId) {
        UI.getCurrent().navigate("invoices/c" + clientId);
    }

    public void clientContracts(Long clientId) {
        UI.getCurrent().navigate("contracts/c" + clientId);
    }
}
