package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.data.binder.Binder;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.data.service.ClientService;
import io.mkolodziejczyk92.views.client.NewClientFormView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class ClientAddFormViewController {

    private NewClientFormView newClientFormView;

    private ClientService clientService;
    private Binder<Client> binder;

    public ClientAddFormViewController(ClientService clientService) {
        this.clientService = clientService;
    }

    public void initView(NewClientFormView newClientFormView, Binder<Client> binder) {
        this.newClientFormView = newClientFormView;
        this.binder = binder;

    }

    public void clearForm() {
        this.binder.setBean(new Client());
    }

    public void saveNewClient(Client client) {
        clientService.save(client);
    }

    public void updateClient(Client client){
        clientService.update(client);
    }

    public Client findClientById(Long id){
        return clientService.get(id).orElseThrow();
    }


    public void returnToClients() {
        UI.getCurrent().navigate("clients");
    }
}
