package io.mkolodziejczyk92.data.controllers;

import io.mkolodziejczyk92.data.entity.Contract;
import io.mkolodziejczyk92.data.service.ContractService;
import io.mkolodziejczyk92.views.contracts.ContractsView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class ContractsViewController {

    private ContractsView contractsView;
    private final ContractService contractService;

    public ContractsViewController(ContractService contractService) {
        this.contractService = contractService;
    }

    public void initView(ContractsView contractsView) {
        this.contractsView = contractsView;
    }

    public List<Contract> allContracts() {
        return contractService.allContracts();
    }
}
