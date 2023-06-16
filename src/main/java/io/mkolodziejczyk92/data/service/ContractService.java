package io.mkolodziejczyk92.data.service;

import io.mkolodziejczyk92.data.entity.Address;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.data.entity.Contract;
import io.mkolodziejczyk92.data.entity.Purchase;
import io.mkolodziejczyk92.utils.ContractWriter;
import io.mkolodziejczyk92.utils.NumberToWordsConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import static io.mkolodziejczyk92.data.enums.EAddressType.INVESTMENT;
import static io.mkolodziejczyk92.data.enums.EAddressType.RESIDENCE;

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
        BigDecimal advancePaymentInput = new BigDecimal(contract.getAdvancePayment().replace(',', '.'));
        BigDecimal netInputAfterScale = advancePaymentInput.setScale(2, RoundingMode.HALF_UP);
        contract.setAdvancePayment(String.valueOf(netInputAfterScale).replace('.', ','));
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

    public void printPdfContractFromPurchaseAndContractData(Purchase purchaseByContractNumber, Contract contract) {
        Client client = contract.getClient();
        Address investmentAddress = contract.getInvestmentAndResidenceAddresses().stream()
                .filter(address -> address.getAddressType()
                        .equals(INVESTMENT))
                .findAny().get();
        Address clientAddress = contract.getInvestmentAndResidenceAddresses().stream()
                .filter(address -> address.getAddressType()
                        .equals(RESIDENCE))
                .findAny().get();

        String netAmount = purchaseByContractNumber.getNetAmount().replace(",",".");

        double advancePayment = Double.parseDouble(contract.getAdvancePayment().replace(",","."));
        double grossAmountDouble = Double.parseDouble(purchaseByContractNumber.getGrossAmount().replace(",","."));
        double netAmountDouble = Double.parseDouble(purchaseByContractNumber.getNetAmount().replace(",","."));
        double vatAmount = grossAmountDouble - netAmountDouble;
        double grossAmountMinusAdvancePayment = grossAmountDouble  - advancePayment;

        long wholePartFromPrice = NumberToWordsConverter.getWholePartFromDouble(Double.parseDouble(netAmount));
        long fractionalPartFromPrice = NumberToWordsConverter.getFractionalPartFromDouble(Double.parseDouble(netAmount));


        ContractWriter.createPdfContract(client.getPhoneNumber(), contract.getNumber(), client.getFullName(), investmentAddress.investmentAddressToString(),
                contract.getCommodityType().getName(), clientAddress.residenceAddressToString(), contract.getPlannedImplementationDate().toString(),
                contract.getSignatureDate().toString(), netAmount, NumberToWordsConverter.convertNumberToWords(wholePartFromPrice), NumberToWordsConverter.convertNumberToWords(fractionalPartFromPrice),
                purchaseByContractNumber.getGrossAmount(), String.valueOf(vatAmount), contract.getAdvancePayment(), String.valueOf(grossAmountMinusAdvancePayment));
    }
}
