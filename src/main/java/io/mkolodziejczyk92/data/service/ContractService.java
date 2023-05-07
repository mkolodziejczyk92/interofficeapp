package io.mkolodziejczyk92.data.service;

import io.mkolodziejczyk92.data.entity.Contract;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ContractService {

    private final ContractRepository repository;

    public ContractService(ContractRepository repository) {
        this.repository = repository;
    }

    public Optional<Contract> get(Long id) {
        return repository.findById(id);
    }

    public Contract update(Contract entity) {
        return repository.save(entity);
    }

    public List<Contract> allContracts() {
        return repository.findAll();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Contract> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Contract> list(Pageable pageable, Specification<Contract> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    public void save(Contract contract) {
        repository.save(contract);
    }


    public String createContractNumber() {
        int month = LocalDate.now().getMonthValue();
        int year = Year.now().getValue();
        int thisMonthContractsQuantity = repository.findThisMonthContractsQuantity(month, year);

        return (thisMonthContractsQuantity + 10) + "/" + (month<10?("0"+month):(month))+ "/" + year;
    }

    public List<Contract> clientContracts(Long clientId) {
        return repository.findContractsByClientId(clientId);
    }
}
