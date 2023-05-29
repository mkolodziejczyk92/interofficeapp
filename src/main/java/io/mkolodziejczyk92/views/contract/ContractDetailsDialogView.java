package io.mkolodziejczyk92.views.contract;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.mkolodziejczyk92.data.controllers.ContractsViewController;
import io.mkolodziejczyk92.data.entity.Contract;
import lombok.Getter;

import static com.vaadin.flow.component.dialog.Dialog.DialogFooter;

@Getter
public class ContractDetailsDialogView {

    private final ContractsViewController contractsViewController;

    public ContractDetailsDialogView(ContractsViewController contractsViewController) {

        this.contractsViewController = contractsViewController;
    }

    public Dialog createDialogWithContractDetails(Contract contract){
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle(
                String.format("%s contract number details", contract.getNumber()));


        VerticalLayout dialogLayout = createDialogLayout(contract);
        dialog.add(dialogLayout);
        dialog.setDraggable(true);
        dialog.setResizable(true);




        return dialog;
    }

    private VerticalLayout createDialogLayout(Contract contract) {
        Grid<Contract> dialogGrid = new Grid<>(Contract.class, false);


//        dialogGrid.addColumn(Contract::get)
        dialogGrid.addColumn(Contract::getSignatureDate).setHeader("Signature Date");


        dialogGrid.setItems(contract);
        VerticalLayout dialogLayout = new VerticalLayout(dialogGrid);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("min-width", "300px")
                .set("max-width", "100%").set("height", "50%");

        return dialogLayout;
    }

    private DialogFooter createFooterSectionForDialog(Dialog dialog){
        DialogFooter footer = dialog.getFooter();

        Button cancelButton = new Button("Close", event -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        footer.add(cancelButton);
        return footer;
    }

}
