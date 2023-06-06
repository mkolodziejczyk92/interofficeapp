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
import java.util.HashSet;
import java.util.Set;

import static io.mkolodziejczyk92.data.enums.EAddressType.INVESTMENT;
import static io.mkolodziejczyk92.data.enums.EAddressType.RESIDENCE;
import static io.mkolodziejczyk92.utils.ComponentFactory.*;

@PageTitle("New Contract")
@Route(value = "newContract", layout = MainLayout.class)
@PermitAll
public class NewContractFormView extends Div implements HasUrlParameter<String> {

    private final ComboBox<Client> client = new ComboBox<>("Client");

    private final ComboBox<Address>  residenceAddress = new ComboBox<>("Residence Address");
    private final ComboBox<Address> investmentAddress = new ComboBox<>("Investment Address");

    private  Set<Address> investmentAndResidenceAddresses;

    private final ComboBox<ECommodityType> commodityType = new ComboBox<>("Commodity Type");


    private final DatePicker signatureDate = new DatePicker("Signature Day");

    private final DatePicker plannedImplementationDate = new DatePicker("Planned Realization Date");

    private final TextField number = new TextField("Contract Number");

    private final Button cancel = createCancelButton();
    private final Button save = createSaveButton();
    private final Button back = createBackButton();

    private final Checkbox completed = new Checkbox("Completed");

    private final Button update = createUpdateButton();

    private final Binder<Contract> binder = new Binder<>(Contract.class);

    private final ContractAddFormViewController contractAddFormController;

    private Long purchaseId;

    public NewContractFormView(ContractAddFormViewController contractAddFormController) {
        this.contractAddFormController = contractAddFormController;
        contractAddFormController.initBinder(binder);

        add(createTopButtonLayout());
        add(createFormLayout());
        createComboBoxes();
        add(createBottomButtonLayout());

        binder.bindInstanceFields(this);

        createFieldsValidation();
        save.setEnabled(false);
        createSaveButtonStatus();
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
                signatureDate, plannedImplementationDate, number, completed);
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
            investmentAndResidenceAddresses = new HashSet<>();
            investmentAndResidenceAddresses.add(residenceAddress.getValue());
            investmentAndResidenceAddresses.add(investmentAddress.getValue());
            binder.getBean().setInvestmentAndResidenceAddresses(investmentAndResidenceAddresses);
            contractAddFormController.validateAndSaveNewContract(binder.getBean());
            contractAddFormController.updatePurchase(purchaseId, binder.getBean().getNumber());
        });

        update.addClickListener(e ->{
            investmentAndResidenceAddresses = new HashSet<>();
            investmentAndResidenceAddresses.add(residenceAddress.getValue());
            investmentAndResidenceAddresses.add(investmentAddress.getValue());
            binder.getBean().setInvestmentAndResidenceAddresses(investmentAndResidenceAddresses);
                    contractAddFormController.updateContract(binder.getBean());
                });

        update.setVisible(false);
        return bottomButtonLayout;

    }

    private static void setAddressType(Contract contract, Address address, EAddressType addressType) {
        Set<Address> addresses = contract.getInvestmentAndResidenceAddresses();
        if (addresses != null) {
            addresses.stream()
                    .filter(a -> a.getAddressType().equals(addressType))
                    .findFirst()
                    .ifPresent(a -> {
                        addresses.remove(a);
                        addresses.add(address);
                    });
        }
    }

    private static Address getAddressByType(Set<Address> addresses, EAddressType addressType) {
        if (addresses != null) {
            return addresses.stream()
                    .filter(address -> address.getAddressType().equals(addressType))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    private void createFieldsValidation() {
        binder.forField(investmentAddress)
                .asRequired("Choose investment address")
                .bind(contract -> getAddressByType(contract.getInvestmentAndResidenceAddresses(), INVESTMENT),
                        (contract, address) -> setAddressType(contract, address, INVESTMENT));

        binder.forField(residenceAddress)
                .asRequired("Choose residence address")
                .bind(contract -> getAddressByType(contract.getInvestmentAndResidenceAddresses(), RESIDENCE),
                        (contract, address) -> setAddressType(contract, address, RESIDENCE));

        binder.forField(plannedImplementationDate)
                .asRequired("Please select a date")
                .bind(Contract::getPlannedImplementationDate, Contract::setPlannedImplementationDate);
    }

    private void createSaveButtonStatus() {
        investmentAddress.addValueChangeListener(e -> updateSaveButtonStatus(investmentAddress.isInvalid()));
        residenceAddress.addValueChangeListener(e -> updateSaveButtonStatus(residenceAddress.isInvalid()));
        plannedImplementationDate.addValueChangeListener(e -> updateSaveButtonStatus(plannedImplementationDate.isInvalid()));

    }

    private void updateSaveButtonStatus(boolean isInvalid) {
        save.setEnabled(!isInvalid && !investmentAddress.isEmpty() && !residenceAddress.isEmpty() && !plannedImplementationDate.isEmpty());
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @WildcardParameter String urlParameter) {
        if (!urlParameter.isBlank()) {
            LocalDate now = LocalDate.now(ZoneId.systemDefault());
            Set<Address> allAddresses;
            char identificationChar = urlParameter.charAt(0);
            switch (identificationChar){
                case 'p' -> {
                    purchaseId = Long.valueOf(urlParameter.substring(1));
                    Purchase purchase = contractAddFormController.fillFormWithDataFromPurchase(purchaseId);
                    Client clientFromPurchase = purchase.getClient();
                    allAddresses = clientFromPurchase.getAllAddresses();
                    residenceAddress.setItems(allAddresses
                            .stream()
                            .filter(address -> address.getAddressType().equals(RESIDENCE))
                            .toList());
                    investmentAddress.setItems(allAddresses
                            .stream()
                            .filter(address -> address.getAddressType().equals(INVESTMENT))
                            .toList());
                    signatureDate.setValue(now);
                    client.setValue(clientFromPurchase);
                    commodityType.setValue(purchase.getCommodityType());
                    plannedImplementationDate.setMin(now);
                    number.setValue(contractAddFormController.createContractNumber());
                }
                default -> {
                    Contract contract = contractAddFormController.findContractById(Long.valueOf(urlParameter));
                    Client clientFromContract = contract.getClient();
                    allAddresses = clientFromContract.getAllAddresses();
                    residenceAddress.setItems(allAddresses
                            .stream()
                            .filter(address -> address.getAddressType().equals(RESIDENCE))
                            .toList());
                    investmentAddress.setItems(allAddresses
                            .stream()
                            .filter(address -> address.getAddressType().equals(INVESTMENT))
                            .toList());
                    binder.setBean(contract);
                    investmentAndResidenceAddresses = binder.getBean().getInvestmentAndResidenceAddresses();
                    client.setValue(clientFromContract);
                    number.setValue(contract.getNumber());
                    commodityType.setValue(contract.getCommodityType());
                    signatureDate.setValue(contract.getSignatureDate());
                    plannedImplementationDate.setValue(contract.getPlannedImplementationDate());
                    if( investmentAndResidenceAddresses != null && !investmentAndResidenceAddresses.isEmpty()){
                        residenceAddress.setValue
                                (investmentAndResidenceAddresses.stream()
                                        .filter(address ->  address.getAddressType().equals(RESIDENCE))
                                        .findFirst().orElse(new Address()));
                        investmentAddress.setValue(investmentAndResidenceAddresses.stream()
                                .filter(address ->  address.getAddressType().equals(INVESTMENT))
                                .findFirst().orElse(new Address()));
                    }
                    completed.setEnabled(true);
                    cancel.setVisible(false);
                    save.setVisible(false);
                    update.setVisible(true);
                }
            }
            residenceAddress.setItemLabelGenerator
                    (address -> address.getStreet() + " " + address.getHouseNumber() +
                            " | City: " + address.getCity() +
                            " | Zip Code: " + address.getZipCode());
            investmentAddress.setItemLabelGenerator
                    (address -> "Plot Number: " + address.getPlotNumber() +
                            " | Zip Code: " + address.getZipCode() +
                            " | Municipality: " + address.getMunicipality());

        }
    }
}
