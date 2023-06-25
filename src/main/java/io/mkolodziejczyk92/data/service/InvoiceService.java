package io.mkolodziejczyk92.data.service;

import io.mkolodziejczyk92.data.entity.Invoice;
import io.mkolodziejczyk92.data.entity.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ClientService clientService;
    private final PurchaseService purchaseService;

    public InvoiceService(InvoiceRepository invoiceRepository, ClientService clientService, PurchaseService purchaseService) {
        this.invoiceRepository = invoiceRepository;
        this.clientService = clientService;
        this.purchaseService = purchaseService;
    }

    public Optional<Invoice> get(Long id) {
        return invoiceRepository.findById(id);
    }

    public Invoice update(Invoice entity) {
        return invoiceRepository.save(entity);
    }

    public void delete(Long id) {
        invoiceRepository.deleteById(id);
    }

    public Page<Invoice> list(Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }

    public Page<Invoice> list(Pageable pageable, Specification<Invoice> filter) {
        return invoiceRepository.findAll(filter, pageable);
    }

    public int count() {
        return (int) invoiceRepository.count();
    }

    public List<Invoice> allInvoices() {
        return invoiceRepository.findAll();
    }

    public List<Invoice> clientInvoices(Long clientId) {
        return invoiceRepository.findInvoiceByClientId(clientId);
    }

    public void save(Invoice invoice, Long clientId, Purchase inputPurchase) {
        invoice.setGrossAmount(purchaseService.countGrossValue(invoice.getVat().replace("%", ""), invoice.getNetAmount()));
        invoice.setClient(clientService.get(clientId).orElseThrow());
        invoice.setPurchase(inputPurchase);
        BigDecimal netInput = new BigDecimal(invoice.getNetAmount().replace(',', '.'));
        BigDecimal netInputAfterScale = netInput.setScale(2, RoundingMode.HALF_UP);
        invoice.setNetAmount(String.valueOf(netInputAfterScale).replace('.', ','));
        invoiceRepository.save(invoice);
    }


    public void changeInvoiceStatus(Invoice invoice) {
        invoice.setPaid(!invoice.isPaid());
        invoiceRepository.save(invoice);
    }

    public List<Invoice> purchaseInvoices(Long purchaseId) {
        return invoiceRepository.findInvoiceByPurchaseId(purchaseId);
    }
}
