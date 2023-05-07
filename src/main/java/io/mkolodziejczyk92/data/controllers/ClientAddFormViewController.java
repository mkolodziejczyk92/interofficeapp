package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.data.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class ClientAddFormViewController {

    private ClientService clientService;
    private Binder<Client> binder;

    public ClientAddFormViewController(ClientService clientService) {
        this.clientService = clientService;
    }

    public void initBinder(Binder<Client> binder) {
        this.binder = binder;
    }

    public void clearForm() {
        this.binder.setBean(new Client());
    }

    public void saveNewClient(Client client) {
        clientService.save(client);
        Notification.show(client.getFullName() + " stored.");
        UI.getCurrent().navigate("clients");
    }

    public void updateClient(Client client){
        clientService.update(client);
        Notification.show(client.getFullName() + " updated.");
        UI.getCurrent().navigate("clients");
    }

    public Client findClientById(Long id){
        return clientService.get(id).orElseThrow();
    }


    public void returnToClients() {
        UI.getCurrent().navigate("clients");
    }
}
