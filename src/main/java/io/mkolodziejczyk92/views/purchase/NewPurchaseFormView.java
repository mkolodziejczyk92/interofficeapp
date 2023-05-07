package io.mkolodziejczyk92.views.purchase;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;
import io.mkolodziejczyk92.data.controllers.PurchaseAddFormViewController;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.data.entity.Purchase;
import io.mkolodziejczyk92.data.entity.Supplier;
import io.mkolodziejczyk92.data.enums.ECommodityType;
import io.mkolodziejczyk92.data.enums.EPurchaseStatus;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;


@PageTitle("New Purchase")
@Route(value = "newPurchase", layout = MainLayout.class)
@PermitAll
public class NewPurchaseFormView extends Div implements HasUrlParameter<String> {
    private final PurchaseAddFormViewController purchaseAddFormViewController;

    private final ComboBox<Client> client = new ComboBox<>("Client");

    private final TextField netAmount = new TextField("Net Amount");

    private final TextField supplierPurchaseNumber = new TextField("Supplier Purchase Number");

    private final TextArea comment = new TextArea("Comment");

    private final ComboBox<EPurchaseStatus> status = new ComboBox<>("Purchase Status");

    private final ComboBox<Supplier> supplier = new ComboBox<>("Supplier");

    private final ComboBox<ECommodityType> commodityType = new ComboBox<>("Commodity Type");

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");
    private final Button back = new Button("Back");

    private final Button update = new Button("Update");
    private final Binder<Purchase> binder = new Binder<>(Purchase.class);

    public NewPurchaseFormView(PurchaseAddFormViewController purchaseAddFormViewController) {
        this.purchaseAddFormViewController = purchaseAddFormViewController;
        purchaseAddFormViewController.initBinder(binder);
        add(createTopButtonLayout());
        createFormComboBoxes();
        add(createFormLayout());
        binder.bindInstanceFields(this);
        add(createBottomButtonLayout());
        purchaseAddFormViewController.clearForm();
    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = new HorizontalLayout();
        topButtonLayout.getStyle().set("padding-right", "15px");
        topButtonLayout.getStyle().set("border-bottom", "1px solid var(--lumo-contrast-10pct)");

        back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        back.addClickListener(e -> purchaseAddFormViewController.returnToPurchases());
        back.getStyle().set("margin-left", "auto");
        back.addClickShortcut(Key.ESCAPE);

        topButtonLayout.add(back);
        return topButtonLayout;
    }

    private Component createFormLayout() {

        FormLayout formLayout = new FormLayout(client, commodityType,  status, supplier,
                netAmount, supplierPurchaseNumber, comment);
        formLayout.getStyle().set("padding-left", "30px");
        formLayout.getStyle().set("padding-right", "30px");
        return formLayout;
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

        cancel.addClickListener(e -> purchaseAddFormViewController.clearForm());
        save.addClickListener(e -> {
            purchaseAddFormViewController.saveNewPurchase(binder.getBean());
            Notification.show(binder.getBean().getClass().getSimpleName() + " stored.");
            purchaseAddFormViewController.clearForm();
        });

        update.addClickListener(e -> {
            purchaseAddFormViewController.updatePurchase(binder.getBean());
        });

        update.setVisible(false);
        return bottomButtonLayout;

    }

    private void createFormComboBoxes() {

        client.setItems(purchaseAddFormViewController.allClients());
        client.setItemLabelGenerator(Client::getFullName);
        client.setMaxWidth("300px");

        commodityType.setItems(ECommodityType.values());
        commodityType.setItemLabelGenerator(ECommodityType::getName);
        commodityType.setMaxWidth("350px");
        commodityType.getStyle().set("padding-right", "30px");

        status.setItems(EPurchaseStatus.values());
        status.setItemLabelGenerator(EPurchaseStatus::getStatusName);
        status.setMaxWidth("300px");

        supplier.setItems(purchaseAddFormViewController.allSuppliers());
        supplier.setItemLabelGenerator(Supplier::getNameOfCompany);
        supplier.setMaxWidth("300px");

    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @WildcardParameter String purchaseId) {
      if(!purchaseId.isEmpty()){
          Purchase purchase = purchaseAddFormViewController.findPurchaseById(Long.valueOf(purchaseId));
          binder.setBean(purchase);
          netAmount.setValue(purchase.getNetAmount());
          client.setValue(purchase.getClient());
          status.setValue(purchase.getStatus());
          supplierPurchaseNumber.setValue(purchase.getSupplierPurchaseNumber());
          comment.setValue(purchase.getComment());
          commodityType.setValue(purchase.getCommodityType());
          supplier.setValue(purchase.getSupplier());

          cancel.setVisible(false);
          save.setVisible(false);
          update.setVisible(true);
      }
    }
}
