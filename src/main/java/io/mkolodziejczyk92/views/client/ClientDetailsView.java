package io.mkolodziejczyk92.views.client;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.*;
import io.mkolodziejczyk92.data.controllers.ClientDetailsController;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@PageTitle("Client Details")
@Route(value = "clients", layout = MainLayout.class)
@PermitAll
public class ClientDetailsView extends Div implements HasUrlParameter<String>{

    private  Long clientId;
    private final ClientDetailsController clientDetailsController;

    public ClientDetailsView(ClientDetailsController clientDetailsController) {
        this.clientDetailsController = clientDetailsController;
    }


    @Override
    public void setParameter(BeforeEvent beforeEvent, @WildcardParameter String parameter) {
        clientId = Long.valueOf(parameter);
        Client client = clientDetailsController.findClientById(clientId).get();
        setText("Client Name:" + client.getFullName()
                + "     ||Client Email:" + client.getEmail()
                + "     ||Client Number:" + client.getPhoneNumber() );
    }

}
