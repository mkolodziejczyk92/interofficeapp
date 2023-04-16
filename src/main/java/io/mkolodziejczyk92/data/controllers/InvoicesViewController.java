package io.mkolodziejczyk92.data.controllers;

import io.mkolodziejczyk92.data.entity.Invoice;
import io.mkolodziejczyk92.data.service.InvoiceService;
import io.mkolodziejczyk92.views.invoice.InvoicesView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class InvoicesViewController {

    private final InvoiceService invoiceService;

    private InvoicesView invoicesView;

    public InvoicesViewController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    public void initView(InvoicesView invoicesView){
        this.invoicesView = invoicesView;
    }

    public List<Invoice> allInvoices(){
        return invoiceService.allInvoices();
    }
}
