package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.component.UI;
import io.mkolodziejczyk92.data.entity.Contract;
import io.mkolodziejczyk92.data.service.ContractService;
import io.mkolodziejczyk92.views.contract.ContractsView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class ContractsViewController {
    private final ContractService contractService;

    public ContractsViewController(ContractService contractService) {
        this.contractService = contractService;
    }

    public List<Contract> allContracts() {
        return contractService.allContracts();
    }

    public void editContractInformationForm(Long contractId) {
        UI.getCurrent().navigate("newContract/" + contractId);
    }

    public List<Contract> clientContracts(Long clientId) {
        return contractService.clientContracts(clientId);
    }
}
