package io.mkolodziejczyk92.views.invoice;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.router.*;
import io.mkolodziejczyk92.data.controllers.InvoicesViewController;
import io.mkolodziejczyk92.data.entity.Invoice;
import io.mkolodziejczyk92.utils.ComponentFactory;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@PageTitle("Invoices")
@Route(value = "invoices", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class InvoicesView extends Div implements HasUrlParameter<String> {

    private final InvoicesViewController invoicesViewController;

    private final InvoiceFilter invoiceFilter = new InvoiceFilter();
    private final InvoiceDataProvider invoiceDataProvider = new InvoiceDataProvider();
    private final ConfigurableFilterDataProvider<Invoice, Void, InvoiceFilter> filterDataProvider
            = invoiceDataProvider.withConfigurableFilter();

    private final Grid<Invoice> grid = new Grid<>(Invoice.class, false);
    private final Button emptyButton = ComponentFactory.createStandardButton("EMPTY");

    private String clientIdWithParameter;

    public InvoicesView(InvoicesViewController invoicesViewController) {
        this.invoicesViewController = invoicesViewController;


        grid.addColumn(Invoice::getNumber).setHeader("Invoice number");
        grid.addColumn(invoice -> invoice.getClient().getFullName()).setHeader("Client");
        grid.addColumn(invoice -> invoice.getContract().getNumber()).setHeader("Contract number");
        grid.addColumn(Invoice::getAmount).setHeader("Amount");
        grid.addColumn(Invoice::getIssueDate).setHeader("Issue date");
        grid.addColumn(Invoice::getPaymentTime).setHeader("Payment time");
        grid.addComponentColumn(invoice -> {
            Icon icon;
            if (invoice.isPaid()) {
                icon = VaadinIcon.CHECK_CIRCLE.create();
                icon.setColor("green");
            } else {
                icon = VaadinIcon.CLOSE_CIRCLE.create();
                icon.setColor("red");
            }
            return icon;
        }).setAutoWidth(true).setHeader("Paid").setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn(Invoice::getType).setHeader("Type");
        grid.addColumn(Invoice::getPaymentMethod).setHeader("Payment method");
        grid.getColumns().forEach(invoiceColumn -> invoiceColumn.setAutoWidth(true));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setItems(filterDataProvider);

        GridContextMenu<Invoice> menu = grid.addContextMenu();
        menu.addItem("View", event -> {
        });
        menu.addItem("Edit", event -> {
        });
        menu.addItem("Delete", event -> {
        });

        add(createTopButtonLayout());
        add(createSearchLayout());
        add(grid);
    }

    private Component createSearchLayout() {
        HorizontalLayout searchLayout = new HorizontalLayout();
        TextField searchField = ComponentFactory.createTextFieldForSearchLayout
                ("Search by client, contract number or invoice number");
        searchField.addValueChangeListener(e -> {
            invoiceFilter.setSearchTerm(e.getValue());
            filterDataProvider.setFilter(invoiceFilter);
        });
        searchLayout.add(searchField);
        return searchLayout;
    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = ComponentFactory.createTopButtonLayout();
        topButtonLayout.add(emptyButton);
        return topButtonLayout;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @WildcardParameter String urlParameter) {
        if(!urlParameter.isEmpty()){
            String clientId = urlParameter.substring(1);
            clientIdWithParameter = urlParameter;
            grid.setItems(invoicesViewController.clientInvoices(Long.valueOf(clientId)));
        }
    }
}
