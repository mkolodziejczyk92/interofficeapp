package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.data.binder.Binder;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.data.entity.Contract;
import io.mkolodziejczyk92.data.service.ClientService;
import io.mkolodziejczyk92.data.service.ContractService;
import org.springframework.stereotype.Controller;
import io.mkolodziejczyk92.views.contract.NewContractFormView;

import java.util.List;

@Controller
public class ContractAddFormViewController {

    private final ContractService contractService;

    private final ClientService clientService;

    private Binder<Contract> binder;

    private NewContractFormView newContractFormView;

    public ContractAddFormViewController(ContractService contractService, ClientService clientService) {
        this.contractService = contractService;
        this.clientService = clientService;
    }


    public void initView(NewContractFormView newContractFormView, Binder<Contract> binder) {
        this.newContractFormView = newContractFormView;
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
    }

    public void updateContract(Contract contract){
        contractService.update(contract);
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
}
