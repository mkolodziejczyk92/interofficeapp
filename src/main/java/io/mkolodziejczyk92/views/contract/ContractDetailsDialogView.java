package io.mkolodziejczyk92.views.contract;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import io.mkolodziejczyk92.data.controllers.ContractsViewController;
import io.mkolodziejczyk92.data.entity.Address;
import io.mkolodziejczyk92.data.entity.Contract;
import io.mkolodziejczyk92.data.entity.Purchase;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static io.mkolodziejczyk92.data.enums.EAddressType.INVESTMENT;
import static io.mkolodziejczyk92.data.enums.EAddressType.RESIDENCE;

@Getter
@Component
public class ContractDetailsDialogView extends Dialog {

    private final ContractsViewController contractsViewController;

    private TextField netAmount, grossAmount, vat, manufacturer, supplier, signatureDate, purchaseStatus, commodityType,
            residenceAddressDetails, investmentZipCode, investmentMunicipality, investmentVoivodeship, investmentCountry, street, houseNumber,
            apartmentNumber, residenceZipCode, residenceCity, residenceMunicipality, residenceVoivodeship, residenceCountry;

    private HorizontalLayout dialogSectionInformation, dialogSectionInformation2, dialogSectionInformation3, investmentAddressDataInDialog,
            investmentAddressDataInDialog2, residenceAddressDataInDialog, residenceAddressDataInDialog2, residenceAddressDataInDialog3;

    private VerticalLayout investmentAddressDialogSection, residenceAddressDialogSection;

    private  H4 investmentAddressHeader, residenceAddressHeader;


    public ContractDetailsDialogView(ContractsViewController contractsViewController) {
        this.contractsViewController = contractsViewController;

        netAmount = new TextField("Net Amount");
        grossAmount = new TextField("Gross Amount");
        vat = new TextField("Vat");
        manufacturer = new TextField("Manufacturer");
        supplier = new TextField("Supplier");
        signatureDate = new TextField("Signature Date");
        purchaseStatus = new TextField("Purchase Status");
        commodityType = new TextField("Commodity Type");
        residenceAddressDetails = new TextField("Residence Address");
        investmentZipCode = new TextField("Zip Code");
        investmentMunicipality = new TextField("Municipality");
        investmentVoivodeship = new TextField("Voivodeship");
        investmentCountry = new TextField("Country");

        street = new TextField("Street");
        houseNumber = new TextField("House Number");
        apartmentNumber = new TextField("Apartment Number");
        residenceZipCode = new TextField("Zip Code");
        residenceCity = new TextField("City");
        residenceMunicipality = new TextField("Municipality");
        residenceVoivodeship = new TextField("Voivodeship");
        residenceCountry = new TextField("Country");

        dialogSectionInformation = new HorizontalLayout();
        dialogSectionInformation2 = new HorizontalLayout();
        dialogSectionInformation3 = new HorizontalLayout();
        investmentAddressDataInDialog = new HorizontalLayout();
        investmentAddressDataInDialog2 = new HorizontalLayout();
        residenceAddressDataInDialog = new HorizontalLayout();
        residenceAddressDataInDialog2 = new HorizontalLayout();
        residenceAddressDataInDialog3 = new HorizontalLayout();

        investmentAddressDialogSection = new VerticalLayout();
        residenceAddressDialogSection = new VerticalLayout();

        investmentAddressHeader = new H4("Investment Address Details");
        residenceAddressHeader = new H4("Residence Address Details");
        setReadOnlyProperties();
        createFooterSectionForDialog(this);
        setDraggable(true);
        setResizable(true);

    }

    private void setReadOnlyProperties() {
        netAmount.setReadOnly(true);
        grossAmount.setReadOnly(true);
        vat.setReadOnly(true);
        manufacturer.setReadOnly(true);
        supplier.setReadOnly(true);
        signatureDate.setReadOnly(true);
        manufacturer.setReadOnly(true);
        purchaseStatus.setReadOnly(true);
        commodityType.setReadOnly(true);
        investmentZipCode.setReadOnly(true);
        investmentVoivodeship.setReadOnly(true);
        investmentCountry.setReadOnly(true);
        investmentMunicipality.setReadOnly(true);
        street.setReadOnly(true);
        houseNumber.setReadOnly(true);
        apartmentNumber.setReadOnly(true);
        residenceZipCode.setReadOnly(true);
        residenceCity.setReadOnly(true);
        residenceMunicipality.setReadOnly(true);
        residenceVoivodeship.setReadOnly(true);
        residenceCountry.setReadOnly(true);

    }


    public Dialog createDialogWithContractDetails(Contract contract) {
        Purchase purchaseDetails = getPurchaseDetails(contract.getNumber());

        setHeaderTitle(
                String.format("%s (%s) contract details", contract.getClient().getFullName() , contract.getNumber()));
        vat.setValue(purchaseDetails.getEVat().getVatValue() + "%");
        netAmount.setValue(purchaseDetails.getNetAmount());
        grossAmount.setValue(purchaseDetails.getGrossAmount());
        signatureDate.setValue(contract.getSignatureDate().toString());
        manufacturer.setValue(purchaseDetails.getManufacturer().getNameOfCompany());
        purchaseStatus.setValue(purchaseDetails.getStatus().getStatusName());
        supplier.setValue(purchaseDetails.getSupplier().getNameOfCompany());
        commodityType.setValue(contract.getCommodityType().getName());

        Optional<Address> investmentAddress = contract.getInvestmentAndResidenceAddresses()
                .stream()
                .filter(address -> address.getAddressType().equals(INVESTMENT))
                .findAny();

        Optional<Address> residenceAddress = contract.getInvestmentAndResidenceAddresses()
                .stream()
                .filter(address -> address.getAddressType().equals(RESIDENCE))
                .findAny();

        open();
        setMaxHeight("85%");
        dialogSectionInformation.add(netAmount, grossAmount, vat);
        dialogSectionInformation2.add(signatureDate, purchaseStatus, commodityType);
        dialogSectionInformation3.add(manufacturer, supplier);
        if(investmentAddress.isPresent() && residenceAddress.isPresent()){
            Address invAdr = investmentAddress.get();
            investmentAddressDialogSection.add(investmentAddressHeader, investmentAddressDataInDialog, investmentAddressDataInDialog2);
            investmentZipCode.setValue(invAdr.getZipCode());
            investmentMunicipality.setValue(invAdr.getMunicipality());
            investmentVoivodeship.setValue(invAdr.getVoivodeship().getNameOfVoivodeship());
            investmentCountry.setValue(invAdr.getCountry().getCountry());
            investmentAddressDataInDialog.add(investmentZipCode, investmentMunicipality);
            investmentAddressDataInDialog2.add(investmentVoivodeship, investmentCountry);

            Address resAdr = residenceAddress.get();
            residenceAddressDialogSection.add(residenceAddressHeader, residenceAddressDataInDialog, residenceAddressDataInDialog2, residenceAddressDataInDialog3);
            street.setValue(resAdr.getStreet());
            houseNumber.setValue(resAdr.getHouseNumber());
            apartmentNumber.setValue(resAdr.getApartmentNumber());
            residenceZipCode.setValue(resAdr.getZipCode());
            residenceCity.setValue(resAdr.getCity());
            residenceMunicipality.setValue(resAdr.getMunicipality());
            residenceVoivodeship.setValue(resAdr.getVoivodeship().getNameOfVoivodeship());
            residenceCountry.setValue(resAdr.getCountry().getCountry());

            residenceAddressDataInDialog.add(street, houseNumber, apartmentNumber);
            residenceAddressDataInDialog2.add(residenceZipCode, residenceCity, residenceMunicipality);
            residenceAddressDataInDialog3.add(residenceVoivodeship, residenceCountry);

        }
        add(dialogSectionInformation, dialogSectionInformation2, dialogSectionInformation3, investmentAddressDialogSection, residenceAddressDialogSection);

        return this;
    }


    private DialogFooter createFooterSectionForDialog(Dialog dialog) {
        DialogFooter footer = dialog.getFooter();

        Button cancelButton = new Button("Close", event -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        footer.add(cancelButton);
        return footer;
    }

    private Purchase getPurchaseDetails(String contractNumber) {
        return contractsViewController.findPurchaseByContractNumber(contractNumber);
    }

}
