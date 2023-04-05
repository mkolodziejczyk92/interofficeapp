package io.mkolodziejczyk92.views.orders;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import io.mkolodziejczyk92.data.entity.Purchase;
import io.mkolodziejczyk92.data.service.OrdersService;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static io.mkolodziejczyk92.views.FilterHeader.getComponent;

@PageTitle("Orders")
@Route(value = "orders", layout = MainLayout.class)
@PermitAll
public class OrdersView extends Div {

    private final Grid<Purchase> grid = new Grid<>(Purchase.class, false);

    private final OrdersService ordersService;

    public OrdersView(OrdersService ordersService) {
        this.ordersService = ordersService;

        Grid.Column<Purchase> clientFullNameColumn
                = grid.addColumn(purchase -> purchase.getClient().getFullName()).setHeader("Client").setAutoWidth(true);
        Grid.Column<Purchase> contractNumberColumn =
                grid.addColumn("contractNumber").setAutoWidth(true);
        Grid.Column<Purchase> supplierCompanyNameColumn =
                grid.addColumn(purchase -> purchase.getSupplier().getNameOfCompany()).setHeader("Supplier").setAutoWidth(true);
        grid.addColumn("netAmount").setAutoWidth(true);
        grid.addColumn("commodityType").setAutoWidth(true);
        grid.addColumn("status").setAutoWidth(true);
        grid.addColumn("supplierOrderNumber").setAutoWidth(true);
        grid.addColumn("comment").setAutoWidth(true);
        grid.setItems(query -> ordersService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

      add(grid);

        GridContextMenu<Purchase> menu = grid.addContextMenu();

        menu.addItem("View", event -> {
        });
        menu.addItem("Edit", event -> {
        });
        menu.addItem("Delete", event -> {
        });


        List<Purchase> invoiceList = ordersService.purchasesList();
        GridListDataView<Purchase> dataView = grid.setItems(invoiceList);
        InvoicesFilter invoicesFilter = new InvoicesFilter(dataView);

        grid.getHeaderRows().clear();
        HeaderRow headerRow = grid.appendHeaderRow();

        headerRow.getCell(supplierCompanyNameColumn).setComponent(
                createFilterHeader(invoicesFilter::setNameOfCompany));
        headerRow.getCell(clientFullNameColumn).setComponent(
                createFilterHeader(invoicesFilter::setClientFullName));
        headerRow.getCell(contractNumberColumn).setComponent(
                createFilterHeader(invoicesFilter::setContractNumber));

    }


    private static Component createFilterHeader(Consumer<String> filterChangeConsumer) {
        return getComponent(filterChangeConsumer);
    }


    private static class InvoicesFilter {
        private final GridListDataView<Purchase> dataView;

        private String nameOfCompany;
        private String clientFullName;
        private String contractNumber;


        public InvoicesFilter(GridListDataView<Purchase> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setNameOfCompany(String nameOfCompany) {
            this.nameOfCompany = nameOfCompany;
            this.dataView.refreshAll();
        }

        public void setClientFullName(String clientFullName) {
            this.clientFullName = clientFullName;
            this.dataView.refreshAll();
        }

        public void setContractNumber(String contractNumber) {
            this.contractNumber = contractNumber;
            this.dataView.refreshAll();
        }


        public boolean test(Purchase purchase) {
            boolean matchesNameOfCompany = true;
            if(Optional.ofNullable(purchase.getSupplier()).isPresent()){
                matchesNameOfCompany = matches(purchase.getSupplier().getNameOfCompany(), nameOfCompany);
            }
            boolean matchesClientFullName = matches(purchase.getClient().getFullName(), clientFullName);
            boolean matchesContractNumber = matches(purchase.getContractNumber(), contractNumber);
            return matchesNameOfCompany && matchesClientFullName && matchesContractNumber;
        }

        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || value.toLowerCase().contains(searchTerm.toLowerCase());
        }
    }




}
