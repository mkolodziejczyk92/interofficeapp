package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.data.entity.Contract;
import io.mkolodziejczyk92.data.entity.Purchase;
import io.mkolodziejczyk92.data.service.ClientService;
import io.mkolodziejczyk92.data.service.ContractService;
import io.mkolodziejczyk92.data.service.PurchaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class ContractAddFormViewController {

    private final ContractService contractService;

    private final PurchaseService purchaseService;
    private final ClientService clientService;

    private Binder<Contract> binder;

    public ContractAddFormViewController(ContractService contractService, PurchaseService purchaseService, ClientService clientService) {
        this.contractService = contractService;
        this.purchaseService = purchaseService;
        this.clientService = clientService;
    }

    public void initBinder(Binder<Contract> binder) {
        this.binder = binder;
    }

    public void returnToContracts() {
        UI.getCurrent().navigate("contracts");
    }

    public void clearForm() {
        this.binder.setBean(new Contract());
    }

    public void saveNewPurchase(Contract contract){
        contractService.save(contract);
        Notification.show(  "Contract stored.");
        UI.getCurrent().navigate("contracts");
    }

    public void updateContract(Contract contract){
        contractService.update(contract);
        Notification.show("Contract updated.");
        UI.getCurrent().navigate("contracts");
    }

    public List<Client> allClients() {
        return clientService.allClients();
    }

    public Contract findContractById(Long contractId) {
        return contractService.get(contractId).orElseThrow();
    }

    public String createContractNumber() {
        return contractService.createContractNumber();
    }

    public Purchase fillFormWithDataFromPurchase(Long purchaseId) {
        return purchaseService.get(purchaseId).orElseThrow();
    }
}
