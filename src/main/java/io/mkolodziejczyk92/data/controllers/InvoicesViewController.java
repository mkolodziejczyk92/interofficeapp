package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.component.notification.Notification;
import io.mkolodziejczyk92.data.entity.Invoice;
import io.mkolodziejczyk92.data.service.InvoiceRepository;
import io.mkolodziejczyk92.data.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class InvoicesViewController {
    private final InvoiceRepository invoiceRepository;

    private final InvoiceService invoiceService;

    public InvoicesViewController(InvoiceService invoiceService,
                                  InvoiceRepository invoiceRepository) {
        this.invoiceService = invoiceService;
        this.invoiceRepository = invoiceRepository;
    }

    public List<Invoice> allInvoices(){
        return invoiceService.allInvoices();
    }

    public List<Invoice> clientInvoices(Long clientId) {
        return invoiceService.clientInvoices(clientId);
    }

    public boolean deleteInvoice(Invoice invoice) {
        try {
            invoiceService.delete(invoice.getId());
        } catch (DataIntegrityViolationException e){
            Notification.show("Invoice " + invoice.getNumber() + " cannot be deleted");
            return false;
        }
        Notification.show("Invoice " + invoice.getNumber() + " deleted.");
        return true;
    }
}
