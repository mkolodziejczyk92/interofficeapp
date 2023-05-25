package io.mkolodziejczyk92.views.contract;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;
import io.mkolodziejczyk92.data.controllers.ContractAddFormViewController;
import io.mkolodziejczyk92.data.entity.Address;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.data.entity.Contract;
import io.mkolodziejczyk92.data.entity.Purchase;
import io.mkolodziejczyk92.data.enums.EAddressType;
import io.mkolodziejczyk92.data.enums.ECommodityType;
import io.mkolodziejczyk92.utils.ComponentFactory;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.mkolodziejczyk92.data.enums.EAddressType.*;
import static io.mkolodziejczyk92.utils.ComponentFactory.*;

@PageTitle("New Contract")
@Route(value = "newContract", layout = MainLayout.class)
@PermitAll
public class NewContractFormView extends Div implements HasUrlParameter<String> {

    private final ComboBox<Client> client = new ComboBox<>("Client");

    private final ComboBox<Address>  residenceAddress = new ComboBox<>("Residence Address");
    private final ComboBox<Address> investmentAddress = new ComboBox<>("Investment Address");

    private final ComboBox<ECommodityType> commodityType = new ComboBox<>("Commodity Type");

    private final DatePicker signatureDate = new DatePicker("Signature Day");

    private final DatePicker plannedImplementationDate = new DatePicker("Planned Realization Date");

    private final TextField netAmount = new TextField("Net Amount");

    private final TextField number = new TextField("Contract Number");

    private final Button cancel = createCancelButton();
    private final Button save = createSaveButton();
    private final Button back = createBackButton();

    private final Checkbox completed = new Checkbox("Completed");

    private final Button update = createUpdateButton();

    private final Binder<Contract> binder = new Binder<>(Contract.class);

    private final ContractAddFormViewController contractAddFormController;

    public NewContractFormView(ContractAddFormViewController contractAddFormController) {
        this.contractAddFormController = contractAddFormController;
        contractAddFormController.initBinder(binder);

        add(createTopButtonLayout());
        add(createFormLayout());
        createComboBoxes();
        add(createBottomButtonLayout());

        binder.bindInstanceFields(this);
        contractAddFormController.clearForm();
    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = ComponentFactory.createTopButtonLayout();

        back.addClickListener(e -> contractAddFormController.returnToContracts());

        topButtonLayout.add(back);
        return topButtonLayout;
    }

    private Component createFormLayout() {
        completed.setEnabled(false);
        number.setReadOnly(true);
        signatureDate.setReadOnly(true);

        return ComponentFactory.createFormLayout(client, commodityType, investmentAddress, residenceAddress,
                signatureDate, plannedImplementationDate, number, netAmount, completed);
    }

    private void createComboBoxes() {

        client.setItems(contractAddFormController.allClients());
        client.setItemLabelGenerator(Client::getFullName);
        client.setMaxWidth("300px");

        commodityType.setItems(ECommodityType.values());
        commodityType.setItemLabelGenerator(ECommodityType::getName);
        commodityType.setMaxWidth("350px");
        commodityType.getStyle().set("padding-right", "30px");


    }

    private Component createBottomButtonLayout() {
        HorizontalLayout bottomButtonLayout = ComponentFactory.createBottomButtonLayout();

        bottomButtonLayout.add(cancel, save, update);

        cancel.addClickListener(e -> contractAddFormController.clearForm());
        save.addClickListener(e -> {
            contractAddFormController.saveNewPurchase(binder.getBean());
        });

        update.addClickListener(e -> contractAddFormController.updateContract(binder.getBean()));
        update.setVisible(false);
        return bottomButtonLayout;

    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @WildcardParameter String urlParameter) {
        if (!urlParameter.isBlank()) {
            LocalDate now = LocalDate.now(ZoneId.systemDefault());

            if (urlParameter.charAt(0) == 'p') {
                String purchaseId = urlParameter.substring(1);
                Purchase purchase = contractAddFormController.fillFormWithDataFromPurchase(Long.valueOf(purchaseId));
                Client clientFromPurchase = purchase.getClient();
                Set<Address> allAddresses = clientFromPurchase.getAllAddresses();
                residenceAddress.setItems(allAddresses
                        .stream()
                        .filter(address -> address.getAddressType().equals(RESIDENCE))
                        .collect(Collectors.toList()));
                residenceAddress.setItemLabelGenerator
                        (address -> address.getStreet() + " " + address.getHouseNumber() +
                                " | City: " + address.getCity() +
                                " | Zip Code: " + address.getZipCode());

                investmentAddress.setItems(allAddresses
                        .stream()
                        .filter(address -> address.getAddressType().equals(INVESTMENT))
                        .collect(Collectors.toList()));
                investmentAddress.setItemLabelGenerator
                        (address -> "Plot Number: " + address.getPlotNumber() +
                                " | Zip Code: " + address.getZipCode() +
                                " | Municipality: " + address.getMunicipality());

                client.setValue(clientFromPurchase);
                netAmount.setValue(purchase.getNetAmount());
                commodityType.setValue(purchase.getCommodityType());
                plannedImplementationDate.setMin(now);
                number.setValue(contractAddFormController.createContractNumber());
            } else {
                Contract contract = contractAddFormController.findContractById(Long.valueOf(urlParameter));
                binder.setBean(contract);
                client.setValue(contract.getClient());
                number.setValue(contract.getNumber());
                commodityType.setValue(contract.getCommodityType());
                netAmount.setValue(contract.getNetAmount());
                signatureDate.setValue(contract.getSignatureDate());
                plannedImplementationDate.setValue(contract.getPlannedImplementationDate());
                completed.setEnabled(true);
                cancel.setVisible(false);
                save.setVisible(false);
                update.setVisible(true);
            }
            signatureDate.setValue(now);
        }
    }
}
