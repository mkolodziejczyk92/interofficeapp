package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.data.service.ClientRepository;
import io.mkolodziejczyk92.data.service.ClientService;
import io.mkolodziejczyk92.views.client.ClientsView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Slf4j
@Controller
public class ClientsViewController {
    private final ClientRepository clientRepository;

    private ClientsView clientsView;

    private final ClientService clientService;


    public ClientsViewController(ClientService clientService,
                                 ClientRepository clientRepository) {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
    }

    public void initView(ClientsView clientsView) {
        this.clientsView = clientsView;
    }

    public List<Client> allClients() {
        return clientService.allClients();
    }

    public void clientAddresses(Long clientId) {
        UI.getCurrent().navigate("clientAddresses/" + clientId);
    }

    public Client findClientById(Long clientId) {

        return clientService.get(clientId).orElseThrow();
    }

    public void deleteClient(Client client) {
        try{
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
            UI.getCurrent().navigate("newClient/" + client.getId());
        } else {
            Notification.show("Client "
                    + client.getFullName()
                    + " does not exist in the database.");
        }
    }
}
