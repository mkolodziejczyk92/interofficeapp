package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.data.binder.Binder;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.data.service.ClientService;
import io.mkolodziejczyk92.views.clients.NewClientFormView;
import org.springframework.stereotype.Controller;

@Controller
public class ClientAddNewFormController {

    private NewClientFormView newClientFormView;

    private ClientService clientService;
    private Binder<Client> binder;

    public ClientAddNewFormController(ClientService clientService) {
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


}
