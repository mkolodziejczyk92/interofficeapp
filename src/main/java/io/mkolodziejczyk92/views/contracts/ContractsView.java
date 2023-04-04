package io.mkolodziejczyk92.views.contracts;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.data.entity.Contract;
import io.mkolodziejczyk92.data.service.ContractService;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;

import java.util.List;
import java.util.function.Consumer;

import static io.mkolodziejczyk92.views.FilterHeader.getComponent;

@PageTitle("Contracts")
@Route(value = "contracts", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class ContractsView extends Div {

    private final Grid<Contract> grid = new Grid<>(Contract.class, false);

    private final ContractService contractService;

    public ContractsView(ContractService contractService) {
        this.contractService = contractService;

        Grid.Column<Contract> clientFullNameColumn =
                grid.addColumn(contract -> contract.getClient().getFullName()).setAutoWidth(true).setHeader("Client");

        Grid.Column<Contract> contractNumberColumn =
                grid.addColumn(Contract::getNumber).setAutoWidth(true).setHeader("Contract Number");

        grid.addColumn(Contract::getNetAmount).setAutoWidth(true).setHeader("Net Amount");
        grid.addColumn(Contract::getSignatureDate).setAutoWidth(true).setHeader("Signature Date");
        grid.addColumn(Contract::getPlannedImplementationDate).setAutoWidth(true).setHeader("Planned Date");
        grid.addComponentColumn(contract -> {
            Icon icon;
            if(contract.isCompleted()){
                icon = VaadinIcon.CHECK_CIRCLE.create();
                icon.setColor("green");
            } else {
                icon = VaadinIcon.CLOSE_CIRCLE.create();
                icon.setColor("red");
            } return icon;
                }).setAutoWidth(true).setHeader("Completed").setTextAlign(ColumnTextAlign.CENTER);

        grid.addColumn(Contract::getCommodityType).setAutoWidth(true).setHeader("Commodity Type");

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        GridContextMenu<Contract> menu = grid.addContextMenu();

        menu.addItem("View", event -> {
        });
        menu.addItem("Edit", event -> {
        });
        menu.addItem("Delete", event -> {
        });


        List<Contract> contracts = contractService.contractList();
        GridListDataView<Contract> dataView = grid.setItems(contracts);
        ContractsFilter contractsFilter = new ContractsFilter(dataView);
        grid.getHeaderRows().clear();
        HeaderRow headerRow = grid.appendHeaderRow();

        headerRow.getCell(clientFullNameColumn).setComponent(
                createFilterHeader(contractsFilter::setClientFullName));
        headerRow.getCell(contractNumberColumn).setComponent(
                createFilterHeader(contractsFilter::setContractNumber));

        add(grid);
    }

    private static Component createFilterHeader(
            Consumer<String> filterChangeConsumer) {
        return getComponent(filterChangeConsumer);
    }

    private static class ContractsFilter {
        private final GridListDataView<Contract> dataView;

        private String contractNumber;
        private String clientFullName;


        public ContractsFilter(GridListDataView<Contract> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setContractNumber(String contractNumber) {
            this.contractNumber = contractNumber;
            this.dataView.refreshAll();
        }

        public void setClientFullName(String clientFullName) {
            this.clientFullName = clientFullName;
            this.dataView.refreshAll();
        }


        public boolean test(Contract contract) {
            boolean matchesContractNumber = matches(contract.getNumber(), contractNumber);
            boolean matchesClientFullName = matches(contract.getClient().getFullName(), clientFullName);
            return matchesContractNumber && matchesClientFullName;
        }

        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || value.toLowerCase().contains(searchTerm.toLowerCase());
        }
    }


}

