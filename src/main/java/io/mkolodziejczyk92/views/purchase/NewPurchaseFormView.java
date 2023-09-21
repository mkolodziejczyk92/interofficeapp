package io.mkolodziejczyk92.views.purchase;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;
import io.mkolodziejczyk92.data.controllers.PurchaseAddFormViewController;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.data.entity.Manufacturer;
import io.mkolodziejczyk92.data.entity.Purchase;
import io.mkolodziejczyk92.data.entity.Supplier;
import io.mkolodziejczyk92.data.enums.ECommodityType;
import io.mkolodziejczyk92.data.enums.EPurchaseStatus;
import io.mkolodziejczyk92.data.enums.EVat;
import io.mkolodziejczyk92.utils.ComponentFactory;
import io.mkolodziejczyk92.utils.validators.PriceFormatValidator;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;

import static com.vaadin.flow.component.Key.ENTER;
import static io.mkolodziejczyk92.utils.ComponentFactory.*;


@PageTitle("New Purchase")
@Route(value = "new-purchase", layout = MainLayout.class)
@PermitAll
public class NewPurchaseFormView extends Div implements HasUrlParameter<String> {

    private final PurchaseAddFormViewController purchaseAddFormViewController;
    private final ComboBox<Client> client = new ComboBox<>("Client");
    private final TextField netAmount = new TextField("Net amount");
    private final TextField supplierPurchaseNumber = new TextField("Supplier Purchase Number");
    private final TextField comment = new TextField("Comment");
    private final ComboBox<EPurchaseStatus> status = new ComboBox<>("Purchase Status");
    private final ComboBox<Supplier> supplier = new ComboBox<>("Supplier");
    private final ComboBox<ECommodityType> commodityType = new ComboBox<>("Commodity Type");
    private final ComboBox<EVat> eVat = new ComboBox<>("VAT");
    private final ComboBox<Manufacturer> manufacturer = new ComboBox<>("Manufacturer");
    private final Button cancel = createCancelButton();
    private final Button save = createStandardButton("Save");
    private final Button back = createBackButton();
    private final Button update = createStandardButton("Update");
    private final Binder<Purchase> binder = new Binder<>(Purchase.class);

    public NewPurchaseFormView(PurchaseAddFormViewController purchaseAddFormViewController) {
        this.purchaseAddFormViewController = purchaseAddFormViewController;
        purchaseAddFormViewController.initBinder(binder);
        add(createTopButtonLayout());
        createFormComboBoxes();

        binder.forField(netAmount)
                .withValidator(new PriceFormatValidator())
                .bind(Purchase::getNetAmount, Purchase::setNetAmount);

        netAmount.addValueChangeListener(event -> {
            binder.validate();
        });

        createFormLayout();
        createFieldsValidation();


        save.setEnabled(false);
        update.setEnabled(false);
        createSaveAndUpdateButtonStatus();

        binder.bindInstanceFields(this);
        add(createBottomButtonLayout());
        purchaseAddFormViewController.clearForm();

    }


    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = ComponentFactory.createTopButtonLayout();

        back.addClickListener(e -> purchaseAddFormViewController.returnToPurchases());
        topButtonLayout.add(back);
        return topButtonLayout;
    }

    private void createFormLayout() {
        HorizontalLayout firstLineInFormLayout = new HorizontalLayout();
        firstLineInFormLayout.setPadding(true);
        firstLineInFormLayout.addAndExpand(client, commodityType, status, supplier);
        add(firstLineInFormLayout);
        HorizontalLayout secondLineInFormLayout = new HorizontalLayout();
        secondLineInFormLayout.setPadding(true);
        secondLineInFormLayout.addAndExpand(manufacturer, eVat, netAmount);
        add(secondLineInFormLayout);
        HorizontalLayout thirdLineInFormLayout = new HorizontalLayout();
        thirdLineInFormLayout.addAndExpand(supplierPurchaseNumber,comment);
        thirdLineInFormLayout.setPadding(true);
        add(thirdLineInFormLayout);
    }

    private Component createBottomButtonLayout() {
        HorizontalLayout bottomButtonLayout = ComponentFactory.createBottomButtonLayout();

        bottomButtonLayout.add(cancel, save, update);
        cancel.addClickListener(e -> purchaseAddFormViewController.clearForm());
        save.addClickListener(e -> {
            purchaseAddFormViewController.saveNewPurchase(binder.getBean());
        });

        update.addClickListener(e -> {
            purchaseAddFormViewController.updatePurchase(binder.getBean());
        });
        save.addClickShortcut(ENTER);
        update.setVisible(false);
        return bottomButtonLayout;

    }

    private void createFieldsValidation() {
        binder.forField(client)
                .asRequired("Choose client")
                .bind(Purchase::getClient, Purchase::setClient);
        binder.forField(commodityType)
                .asRequired("Choose commodity type")
                .bind(Purchase::getCommodityType, Purchase::setCommodityType );
        binder.forField(status)
                .asRequired("Choose purchase status")
                .bind(Purchase::getStatus, Purchase::setStatus );
        binder.forField(supplier)
                .asRequired("Choose supplier")
                .bind(Purchase::getSupplier, Purchase::setSupplier );
        binder.forField(manufacturer)
                .asRequired("Choose manufacturer")
                .bind(Purchase::getManufacturer, Purchase::setManufacturer);
        binder.forField(eVat)
                .asRequired("Choose vat")
                .bind(Purchase::getEVat,Purchase::setEVat);
    }

    private void createSaveAndUpdateButtonStatus() {
        client.addValueChangeListener(e -> updateSaveAndUpdateButtonStatus(client.isInvalid()));
        commodityType.addValidationStatusChangeListener(e -> updateSaveAndUpdateButtonStatus(commodityType.isInvalid()));
        status.addValidationStatusChangeListener(e -> updateSaveAndUpdateButtonStatus(status.isInvalid()));
        supplier.addValidationStatusChangeListener(e -> updateSaveAndUpdateButtonStatus(supplier.isInvalid()));
        manufacturer.addValidationStatusChangeListener(e -> updateSaveAndUpdateButtonStatus(manufacturer.isInvalid()));
        eVat.addValidationStatusChangeListener(e -> updateSaveAndUpdateButtonStatus(eVat.isInvalid()));
        netAmount.addValidationStatusChangeListener(e -> updateSaveAndUpdateButtonStatus(netAmount.isInvalid()));
        supplierPurchaseNumber.addValidationStatusChangeListener(e -> updateSaveAndUpdateButtonStatus(supplierPurchaseNumber.isInvalid()));
    }

    private void updateSaveAndUpdateButtonStatus(boolean isInvalid) {
        save.setEnabled(!isInvalid && !client.isEmpty() && !commodityType.isEmpty() && !status.isEmpty()
                && !supplier.isEmpty() && !manufacturer.isEmpty() && !eVat.isEmpty() && !netAmount.isEmpty()
                && !supplierPurchaseNumber.isEmpty());
        update.setEnabled(!isInvalid && !client.isEmpty() && !commodityType.isEmpty() && !status.isEmpty()
                && !supplier.isEmpty() && !manufacturer.isEmpty() && !eVat.isEmpty() && !netAmount.isEmpty()
                && !supplierPurchaseNumber.isEmpty());

    }

    private void createFormComboBoxes() {

        client.setItems(purchaseAddFormViewController.allClients());
        client.setItemLabelGenerator(Client::getFullName);

        commodityType.setItems(ECommodityType.values());
        commodityType.setItemLabelGenerator(ECommodityType::getName);


        status.setItems(EPurchaseStatus.values());
        status.setItemLabelGenerator(EPurchaseStatus::getStatusName);

        supplier.setItems(purchaseAddFormViewController.allSuppliers());
        supplier.setItemLabelGenerator(Supplier::getNameOfCompany);

        manufacturer.setItems(purchaseAddFormViewController.allManufacturers());
        manufacturer.setItemLabelGenerator(Manufacturer::getNameOfCompany);
        manufacturer.setMaxWidth("300px");

        eVat.setItems(EVat.values());
        eVat.setItemLabelGenerator(EVat::getVatValue);
        eVat.setMaxWidth("100px");

    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @WildcardParameter String urlWithClientId) {

        if (!urlWithClientId.isBlank() && !urlWithClientId.startsWith(PARAMETER_FOR_CLIENT_ID_FROM_GRID)) {
            binder.setBean(purchaseAddFormViewController.findPurchaseById(Long.valueOf(urlWithClientId)));

            cancel.setVisible(false);
            save.setVisible(false);
            update.setVisible(true);

        } else if (!urlWithClientId.isBlank()) {
            String clientId = urlWithClientId.substring(1);
            client.setValue(purchaseAddFormViewController.findClientById(Long.valueOf(clientId)));
        }

    }

}
