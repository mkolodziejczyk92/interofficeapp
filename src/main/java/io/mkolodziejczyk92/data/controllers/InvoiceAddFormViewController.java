package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;
import io.mkolodziejczyk92.data.entity.Invoice;
import io.mkolodziejczyk92.data.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class InvoiceAddFormViewController {

    private final InvoiceService invoiceService;

    private Binder<Invoice> binder;

    public InvoiceAddFormViewController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    public void initBinder(Binder<Invoice> binder) {
        this.binder = binder;
    }

    public void clearForm() {
        this.binder.setBean(new Invoice());
    }

    public void returnToClientPurchases(Long clientId) {
        UI.getCurrent().navigate("purchases/c" + clientId);
    }

    public void saveNewInvoice(Invoice invoice, Long clientId) {
        invoiceService.save(invoice, clientId);
        UI.getCurrent().navigate("invoices");
        Notification.show("Invoice no. " + invoice.getNumber() + " stored");
    }
}
