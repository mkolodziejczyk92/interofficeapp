package io.mkolodziejczyk92.views.invoice;


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
import io.mkolodziejczyk92.data.entity.Invoice;
import io.mkolodziejczyk92.data.service.InvoiceService;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;

import java.util.List;
import java.util.function.Consumer;

import static io.mkolodziejczyk92.views.FilterHeader.getComponent;

@PageTitle("Invoices")
@Route(value = "invoices", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class InvoicesView extends Div {

    private final Grid<Invoice> grid = new Grid<>(Invoice.class, false);

    private final InvoiceService invoiceService;

    public InvoicesView(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;

        Grid.Column<Invoice> invoiceNumberColumn =
                grid.addColumn(Invoice::getNumber).setAutoWidth(true).setHeader("Invoice Number");

        Grid.Column<Invoice> clientFullNameColumn =
                grid.addColumn(invoice -> invoice.getClient().getFullName()).setAutoWidth(true).setHeader("Client");

        Grid.Column<Invoice> contractNumberColumn =
                grid.addColumn(invoice -> invoice.getContract().getNumber()).setAutoWidth(true).setHeader("Contract Number");

        grid.addColumn(Invoice::getAmount).setAutoWidth(true).setHeader("Amount");
        grid.addColumn(Invoice::getIssueDate).setAutoWidth(true).setHeader("Issue Date");
        grid.addColumn(Invoice::getPaymentTime).setAutoWidth(true).setHeader("Payment Time");
        grid.addColumn(Invoice::isPaid).setAutoWidth(true).setHeader("Paid");
        grid.addColumn(Invoice::getType).setAutoWidth(true).setHeader("Type");
        grid.addColumn(Invoice::getPaymentMethod).setAutoWidth(true).setHeader("Payment Method");


        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        List<Invoice> invoiceList = invoiceService.invoiceList();
        GridListDataView<Invoice> dataView = grid.setItems(invoiceList);
        InvoicesFilter invoicesFilter = new InvoicesFilter(dataView);

        grid.getHeaderRows().clear();
        HeaderRow headerRow = grid.appendHeaderRow();

        headerRow.getCell(invoiceNumberColumn).setComponent(
                createFilterHeader(invoicesFilter::setInvoiceNumber));
        headerRow.getCell(clientFullNameColumn).setComponent(
                createFilterHeader(invoicesFilter::setClientFullName));
        headerRow.getCell(contractNumberColumn).setComponent(
                createFilterHeader(invoicesFilter::setContractNumber));


        add(grid);
    }

    private static Component createFilterHeader(Consumer<String> filterChangeConsumer) {
        return getComponent(filterChangeConsumer);
    }

    private static class InvoicesFilter {
        private final GridListDataView<Invoice> dataView;

        private String invoiceNumber;
        private String clientFullName;

        private String contractNumber;


        public InvoicesFilter(GridListDataView<Invoice> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setInvoiceNumber(String invoiceNumber) {
            this.invoiceNumber = invoiceNumber;
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


        public boolean test(Invoice invoice) {
            boolean matchesInvoiceNumber = matches(invoice.getNumber(), invoiceNumber);
            boolean matchesClientFullName = matches(invoice.getClient().getFullName(), clientFullName);
            boolean matchesContractNumber = matches(invoice.getContract().getNumber(), contractNumber);
            return matchesInvoiceNumber && matchesClientFullName && matchesContractNumber;
        }

        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty()
                    || value.toLowerCase().contains(searchTerm.toLowerCase());
        }
    }

}
