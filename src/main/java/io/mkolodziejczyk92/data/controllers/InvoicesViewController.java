package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.charts.model.Navigation;
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

    private final InvoiceService invoiceService;

    public InvoicesViewController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
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

    public boolean changeInvoiceStatus(Invoice invoice) {
        try{
            invoiceService.changeInvoiceStatus(invoice);
        }catch (DataIntegrityViolationException e){
            Notification.show("Invoice dose not exist.");
            return false;
        }
        UI.getCurrent().navigate("invoices/p" + invoice.getPurchase().getId());
        Notification.show("Status changed.");
        return true;
    }

    public List<Invoice> purchaseInvoices(Long purchaseId) {
        return invoiceService.purchaseInvoices(purchaseId);
    }
}
