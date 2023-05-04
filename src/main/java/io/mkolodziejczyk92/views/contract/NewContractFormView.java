package io.mkolodziejczyk92.views.contract;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;
import io.mkolodziejczyk92.data.controllers.ContractAddFormViewController;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.data.entity.Contract;
import io.mkolodziejczyk92.data.enums.ECommodityType;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@PageTitle("New Contract")
@Route(value = "newContract", layout = MainLayout.class)
@PermitAll
public class NewContractFormView extends Div implements HasUrlParameter<String> {

    private final ComboBox<Client> client = new ComboBox<>("Client");

    private final ComboBox<ECommodityType> commodityType = new ComboBox<>("Commodity Type");

    private final TextField netAmount = new TextField("Net Amount");

    private final TextField number = new TextField("Contract Number");

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");
    private final Button back = new Button("Back");

    private final Checkbox completed = new Checkbox("Completed");

    private final Button update = new Button("Update");

    private final Binder<Contract> binder = new Binder<>(Contract.class);

    private final ContractAddFormViewController contractAddFormController;

    public NewContractFormView(ContractAddFormViewController contractAddFormController) {
        this.contractAddFormController = contractAddFormController;
        contractAddFormController.initView(this, binder);

        add(createTopButtonLayout());
        add(createFormLayout());
        createComboBoxes();
        add(createBottomButtonLayout());

        binder.bindInstanceFields(this);
        contractAddFormController.clearForm();
    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = new HorizontalLayout();
        topButtonLayout.getStyle().set("padding-right", "15px");
        topButtonLayout.getStyle().set("border-bottom", "1px solid var(--lumo-contrast-10pct)");

        back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        back.addClickListener(e -> contractAddFormController.returnToContracts());
        back.getStyle().set("margin-left", "auto");
        back.addClickShortcut(Key.ESCAPE);

        topButtonLayout.add(back);
        return topButtonLayout;
    }

    private Component createFormLayout(){
        FormLayout formLayout = new FormLayout(client, commodityType, number, netAmount, completed);
        completed.setEnabled(false);
        number.setReadOnly(true);

        formLayout.getStyle().set("padding-left", "30px");
        formLayout.getStyle().set("padding-right", "30px");
        return formLayout;
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
        HorizontalLayout bottomButtonLayout = new HorizontalLayout();
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        bottomButtonLayout.add(cancel);
        bottomButtonLayout.add(save);
        bottomButtonLayout.add(update);
        bottomButtonLayout.getStyle().set("padding-left", "30px");
        bottomButtonLayout.getStyle().set("padding-top", "30px");

        cancel.addClickListener(e -> contractAddFormController.clearForm());
        save.addClickListener(e -> {
            number.setValue(contractAddFormController.createContractNumber());
            contractAddFormController.saveNewPurchase(binder.getBean());
            Notification.show(binder.getBean().getClass().getSimpleName() + " stored.");
            contractAddFormController.clearForm();
        });

        update.addClickListener(e -> {
            contractAddFormController.updateContract(binder.getBean());
            Notification.show(binder.getBean().getClass().getSimpleName() + " updated.");
            contractAddFormController.clearForm();

        });

        update.setVisible(false);
        return bottomButtonLayout;

    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @WildcardParameter String contractId) {
        if(!contractId.isEmpty()){
            Contract contract = contractAddFormController.findContractById(Long.valueOf(contractId));
            binder.setBean(contract);
            client.setValue(contract.getClient());
            number.setValue(contract.getNumber());
            commodityType.setValue(contract.getCommodityType());
            netAmount.setValue(contract.getNetAmount());

            completed.setEnabled(true);
            cancel.setVisible(false);
            save.setVisible(false);
            update.setVisible(true);
        } else {
            number.setValue("Contract number is generated automatically");
        }
    }
}
