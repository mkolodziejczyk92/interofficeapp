package io.mkolodziejczyk92.views.contract;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.router.*;
import io.mkolodziejczyk92.data.controllers.ContractsViewController;
import io.mkolodziejczyk92.data.entity.Contract;
import io.mkolodziejczyk92.utils.ContractWriter;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;

import static io.mkolodziejczyk92.utils.ComponentFactory.createTextFieldForSearchLayout;
import static io.mkolodziejczyk92.utils.ComponentFactory.createTopButtonLayout;

@PageTitle("Contracts")
@Route(value = "contracts", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class ContractsView extends Div implements HasUrlParameter<String> {

    private final ContractsViewController contractsViewController;

    private final ContractDetailsDialogView dialogView;

    private ContractFilter contractFilter = new ContractFilter();
    private ContractDataProvider contractDataProvider = new ContractDataProvider();
    private ConfigurableFilterDataProvider<Contract, Void, ContractFilter> filterDataProvider
            = contractDataProvider.withConfigurableFilter();
    private final Grid<Contract> grid = new Grid<>(Contract.class, false);

    public ContractsView(ContractsViewController contractsViewController, ContractDetailsDialogView dialogView) {
        this.contractsViewController = contractsViewController;
        this.dialogView = dialogView;


        grid.addColumn(contract -> contract.getClient().getFullName()).setHeader("Client");
        grid.addColumn(Contract::getNumber).setHeader("Contract Number");
        grid.addColumn(Contract::getPlannedImplementationDate).setHeader("Planned Date");
        grid.addComponentColumn(contract -> {
            Icon icon;
            if (contract.isCompleted()) {
                icon = VaadinIcon.CHECK_CIRCLE.create();
                icon.setColor("green");
            } else {
                icon = VaadinIcon.CLOSE_CIRCLE.create();
                icon.setColor("red");
            }
            return icon;
        }).setHeader("Completed").setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn(contract -> contract.getCommodityType().getName()).setHeader("Commodity Type");
        grid.getColumns().forEach(contractColumn -> contractColumn.setAutoWidth(true));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);


        GridContextMenu<Contract> menu = grid.addContextMenu();
        menu.addItem("Details", event -> {
            if (event.getItem().isPresent()) {
                dialogView.createDialogWithContractDetails(event.getItem().get());
            } else menu.close();
        });
        menu.add(new Hr());
        menu.addItem("Edit", event -> {
            if (event.getItem().isPresent()) {
                contractsViewController.editContractInformationForm(event.getItem().get().getId());
            } else menu.close();
        });
        menu.addItem("Delete", event -> {
        });
        menu.add(new Hr());
        menu.addItem("Create PDF contract", event ->
            ContractWriter.createPdfContract()
        );

        add(createTopButtonLayout());
        add(createSearchLayout());
        add(grid);
    }

    private Component createSearchLayout() {
        HorizontalLayout searchLayout = new HorizontalLayout();
        TextField searchField = createTextFieldForSearchLayout("Search by client or contract number");
        searchField.addValueChangeListener(e -> {
            contractFilter.setSearchTerm(e.getValue());
            filterDataProvider.setFilter(contractFilter);
        });
        searchLayout.add(searchField);
        return searchLayout;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @WildcardParameter String urlParameter) {
        if(!urlParameter.isBlank()){
            String clientId = urlParameter.substring(1);
            grid.setItems(contractsViewController.clientContracts(Long.valueOf(clientId)));
        }else {
            grid.setItems(filterDataProvider);
        }
    }
}

