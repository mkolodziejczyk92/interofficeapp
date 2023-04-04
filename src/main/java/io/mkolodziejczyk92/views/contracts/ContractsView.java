package io.mkolodziejczyk92.views.contracts;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
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
                grid.addColumn(contract -> contract.getClient().getFullName()).setAutoWidth(true);

        Grid.Column<Contract> contractNumberColumn =
                grid.addColumn(Contract::getNumber).setAutoWidth(true);

        Grid.Column<Contract> netAmountColumn =
                grid.addColumn(Contract::getNetAmount).setAutoWidth(true);

        Grid.Column<Contract> signatureDateColumn =
                grid.addColumn(Contract::getSignatureDate).setAutoWidth(true);

        Grid.Column<Contract> plannedImplementationDateColumn =
                grid.addColumn(Contract::getPlannedImplementationDate).setAutoWidth(true);

        Grid.Column<Contract> completedColumn =
                grid.addColumn(Contract::isCompleted).setAutoWidth(true);

        Grid.Column<Contract> commodityTypeColumn =
                grid.addColumn(Contract::getCommodityType).setAutoWidth(true);

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        List<Contract> contracts = contractService.contractList();
        GridListDataView<Contract> dataView = grid.setItems(contracts);
        ContractsFilter contractsFilter = new ContractsFilter(dataView);

        grid.getHeaderRows().clear();
        HeaderRow headerRow = grid.appendHeaderRow();

        headerRow.getCell(clientFullNameColumn).setComponent(
                createFilterHeader("Client", contractsFilter::setClientFullName));
        headerRow.getCell(contractNumberColumn).setComponent(
                createFilterHeader("Contract Number", contractsFilter::setContractNumber));
        headerRow.getCell(netAmountColumn).setText("Net Amount");
        headerRow.getCell(completedColumn).setText("Completed");
        headerRow.getCell(signatureDateColumn).setText("Signature Date");
        headerRow.getCell(plannedImplementationDateColumn).setText("Planned Date");
        headerRow.getCell(commodityTypeColumn).setText("Commodity Type");



        add(grid);
    }

    private static Component createFilterHeader(String labelText,
                                                Consumer<String> filterChangeConsumer) {
        return getComponent(labelText, filterChangeConsumer);
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

