package io.mkolodziejczyk92.views.invoice;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;
import io.mkolodziejczyk92.data.controllers.ClientsViewController;
import io.mkolodziejczyk92.data.controllers.InvoiceAddFormViewController;
import io.mkolodziejczyk92.data.controllers.PurchasesViewController;
import io.mkolodziejczyk92.data.entity.Invoice;
import io.mkolodziejczyk92.data.entity.Purchase;
import io.mkolodziejczyk92.data.enums.EInvoiceType;
import io.mkolodziejczyk92.data.enums.EPaymentMethod;
import io.mkolodziejczyk92.utils.ComponentFactory;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;

import static io.mkolodziejczyk92.utils.ComponentFactory.*;


@PageTitle("New invoice")
@Route(value = "new-invoice", layout = MainLayout.class)
@PermitAll
public class NewInvoiceFormView extends Div implements HasUrlParameter<String> {

    private final InvoiceAddFormViewController invoiceAddFormViewController;
    private final PurchasesViewController purchasesViewController;
    private final ClientsViewController clientsViewController;
    private final TextField number = new TextField("Invoice number");
    private final TextField clientName = new TextField("Client");
    private final IntegerField paymentTime = new IntegerField("Payment time");
    private final DatePicker issueDate = new DatePicker("Issue date");
    private final TextField netAmount = new TextField("Net amount");
    private final TextField vat = new TextField("VAT");
    private final Checkbox paid = new Checkbox("Paid");
    private final ComboBox<EInvoiceType> invoiceType = new ComboBox<>("Invoice type");
    private final ComboBox<EPaymentMethod> paymentMethod = new ComboBox<>("Payment method");
    private final Button cancel = createCancelButton();
    private final Button save = createSaveButton();
    private final Button back = createBackButton();
    private final Button update = createUpdateButton();
    private Long purchaseId;
    private Long clientId;
    private Purchase inputPurchase;

    private final Binder<Invoice> binder = new Binder<>(Invoice.class);

    public NewInvoiceFormView(InvoiceAddFormViewController invoiceAddFormViewController,
                              PurchasesViewController purchasesViewController,
                              ClientsViewController clientsViewController) {
        this.invoiceAddFormViewController = invoiceAddFormViewController;
        this.purchasesViewController = purchasesViewController;
        this.clientsViewController = clientsViewController;
        invoiceAddFormViewController.initBinder(binder);

        add(createTopButtonLayout());
        add(createFormLayout());
        createComboBoxes();
        add(createBottomButtonLayout());
        binder.bindInstanceFields(this);
        invoiceAddFormViewController.clearForm();
    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = ComponentFactory.createTopButtonLayout();
        back.addClickListener(e -> invoiceAddFormViewController.returnToClientPurchases(inputPurchase.getClient().getId()));
        topButtonLayout.add(back);
        return topButtonLayout;
    }

    private Component createFormLayout() {
        vat.setReadOnly(true);
        clientName.setReadOnly(true);


        return ComponentFactory.createFormLayout(
                clientName,  number, paymentMethod, paymentTime, issueDate, paid, netAmount, vat, invoiceType);
    }

    private void createComboBoxes() {
        paymentMethod.setItems(EPaymentMethod.values());
        paymentMethod.setItemLabelGenerator(EPaymentMethod::getName);

        invoiceType.setItems(EInvoiceType.values());
        invoiceType.setItemLabelGenerator(EInvoiceType::getName);
    }

    private Component createBottomButtonLayout() {
        HorizontalLayout bottomButtonLayout = ComponentFactory.createBottomButtonLayout();
        bottomButtonLayout.add(cancel, save, update);
        cancel.addClickListener(e -> invoiceAddFormViewController.clearForm());
        save.addClickListener(e -> invoiceAddFormViewController.saveNewInvoice(binder.getBean(), clientId, inputPurchase));
        update.setVisible(false);
        return bottomButtonLayout;
    }


//    private Dialog createDialogWithFileUpload() {
//
//    }

    @Override
    public void setParameter(BeforeEvent event, @WildcardParameter String urlParameter) {
        if (!urlParameter.isBlank()) {
            purchaseId = Long.valueOf(urlParameter.substring(1));
            inputPurchase = purchasesViewController.getPurchaseById(purchaseId);
            clientId = inputPurchase.getClient().getId();
            clientName.setValue(inputPurchase.getClient().getFullName());
            netAmount.setValue(inputPurchase.getNetAmount());
            vat.setValue((inputPurchase.getEVat().getWordForm()));
        }

    }
}
