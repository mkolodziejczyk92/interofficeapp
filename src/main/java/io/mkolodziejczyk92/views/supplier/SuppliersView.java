package io.mkolodziejczyk92.views.supplier;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.mkolodziejczyk92.data.entity.Purchase;
import io.mkolodziejczyk92.data.entity.Supplier;
import io.mkolodziejczyk92.data.service.SupplierService;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.function.Consumer;

import static io.mkolodziejczyk92.views.FilterHeader.getComponent;


@PageTitle("Suppliers")
@Route(value = "suppliers", layout = MainLayout.class)
@PermitAll
public class SuppliersView extends Div {

    private final Grid<Supplier> grid = new Grid<>(Supplier.class, false);
    private final SupplierService supplierService;

    public SuppliersView(SupplierService supplierService) {
        this.supplierService = supplierService;


        Grid.Column<Supplier> nameOfCompanyColumn
                = grid.addColumn("nameOfCompany").setHeader("Name of Company").setAutoWidth(true);
        Grid.Column<Supplier> nipColumn =
                grid.addColumn("nip").setAutoWidth(true);
        grid.addColumn(supplier ->
                supplier.getPurchases().stream().map(Purchase::getContractNumber).toList()).setHeader("Purchases").setAutoWidth(true);

        add(grid);

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        GridContextMenu<Supplier> menu = grid.addContextMenu();

        menu.addItem("View", event -> {
        });
        menu.addItem("Edit", event -> {
        });
        menu.addItem("Delete", event -> {
        });

        List<Supplier> invoiceList = supplierService.supplierList();
        GridListDataView<Supplier> dataView = grid.setItems(invoiceList);
        InvoicesFilter invoicesFilter = new InvoicesFilter(dataView);

        grid.getHeaderRows().clear();
        HeaderRow headerRow = grid.appendHeaderRow();

        headerRow.getCell(nameOfCompanyColumn).setComponent(
                createFilterHeader(invoicesFilter::setNameOfCompany));
        headerRow.getCell(nipColumn).setComponent(
                createFilterHeader(invoicesFilter::setNip));

    }

    private static Component createFilterHeader(Consumer<String> filterChangeConsumer) {
        return getComponent(filterChangeConsumer);
    }

    private static class InvoicesFilter {
        private final GridListDataView<Supplier> dataView;

        private String nameOfCompany;
        private String nip;


        public InvoicesFilter(GridListDataView<Supplier> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setNameOfCompany(String nameOfCompany) {
            this.nameOfCompany = nameOfCompany;
            this.dataView.refreshAll();
        }


        public void setNip(String nip) {
            this.nip = nip;
            this.dataView.refreshAll();
        }


        public boolean test(Supplier supplier) {

            boolean matchesClientFullName = matches(supplier.getNameOfCompany(), nameOfCompany);
            boolean matchesContractNumber = matches(supplier.getNip(), nip);
            return  matchesClientFullName && matchesContractNumber;
        }

        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || value.toLowerCase().contains(searchTerm.toLowerCase());
        }
    }
}
