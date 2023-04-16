package io.mkolodziejczyk92.views.supplier;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.mkolodziejczyk92.data.controllers.SuppliersViewController;
import io.mkolodziejczyk92.data.entity.Supplier;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;

import java.util.List;
import java.util.function.Consumer;

import static io.mkolodziejczyk92.views.FilterHeader.getComponent;


@PageTitle("Suppliers")
@Route(value = "suppliers", layout = MainLayout.class)
@PermitAll
public class SuppliersView extends Div {

    private final Grid<Supplier> grid = new Grid<>(Supplier.class, false);

    private Button newSupplierButton = new Button("Add new supplier");

    private final SuppliersViewController suppliersViewController;

    public SuppliersView(SuppliersViewController suppliersViewController) {
        this.suppliersViewController = suppliersViewController;
        suppliersViewController.initView(this);

        Grid.Column<Supplier> nameOfCompanyColumn
                = grid.addColumn("nameOfCompany").setHeader("Name of Company").setAutoWidth(true);
        Grid.Column<Supplier> nipColumn =
                grid.addColumn("nip").setAutoWidth(true);

        add(createTopButtonLayout());
        add(grid);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        GridContextMenu<Supplier> menu = grid.addContextMenu();

        menu.addItem("View", event -> {
        });
        menu.addItem("Edit", event -> {
        });
        menu.addItem("Delete", event -> {
        });

        List<Supplier> invoiceList = suppliersViewController.allSuppliers();
        GridListDataView<Supplier> dataView = grid.setItems(invoiceList);
        InvoicesFilter invoicesFilter = new InvoicesFilter(dataView);

        grid.getHeaderRows().clear();
        HeaderRow headerRow = grid.appendHeaderRow();

        headerRow.getCell(nameOfCompanyColumn).setComponent(
                createFilterHeader(invoicesFilter::setNameOfCompany));
        headerRow.getCell(nipColumn).setComponent(
                createFilterHeader(invoicesFilter::setNip));

    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = new HorizontalLayout();
        topButtonLayout.getStyle().set("padding-right", "15px");
        topButtonLayout.getStyle().set("border-bottom", "1px solid var(--lumo-contrast-10pct)");
        newSupplierButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newSupplierButton.getStyle().set("margin-left", "auto");
        newSupplierButton.addClickListener(e -> UI.getCurrent().navigate(NewSupplierFormView.class));

        topButtonLayout.add(newSupplierButton);
        return topButtonLayout;
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
            return matchesClientFullName && matchesContractNumber;
        }

        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || value.toLowerCase().contains(searchTerm.toLowerCase());
        }
    }
}
