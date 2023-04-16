package io.mkolodziejczyk92.views.invoice;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.mkolodziejczyk92.data.controllers.InvoicesViewController;
import io.mkolodziejczyk92.data.entity.Invoice;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@PageTitle("Invoices")
@Route(value = "invoices", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class InvoicesView extends Div {

    private final InvoicesViewController invoicesViewController;

    private InvoiceFilter invoiceFilter = new InvoiceFilter();
    private InvoiceDataProvider invoiceDataProvider = new InvoiceDataProvider();
    private ConfigurableFilterDataProvider<Invoice, Void, InvoiceFilter> filterDataProvider
            = invoiceDataProvider.withConfigurableFilter();
    private Button emptyButton = new Button("EMPTY");

    public InvoicesView(InvoicesViewController invoicesViewController) {
        this.invoicesViewController = invoicesViewController;
        invoicesViewController.initView(this);


        Grid<Invoice> grid = new Grid<>(Invoice.class, false);
        grid.addColumn(Invoice::getNumber).setHeader("Invoice number").setAutoWidth(true);
        grid.addColumn(invoice -> invoice.getClient().getFullName()).setHeader("Client").setAutoWidth(true);
        grid.addColumn(invoice -> invoice.getContract().getNumber()).setHeader("Contract number").setAutoWidth(true);
        grid.addColumn(Invoice::getAmount).setHeader("Amount").setAutoWidth(true);
        grid.addColumn(Invoice::getIssueDate).setHeader("Issue date").setAutoWidth(true);
        grid.addColumn(Invoice::getPaymentTime).setHeader("Payment time").setAutoWidth(true);
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
        grid.addColumn(Invoice::getType).setHeader("Type").setAutoWidth(true);
        grid.addColumn(Invoice::getPaymentMethod).setHeader("Payment method").setAutoWidth(true);
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
        searchLayout.addClassName("button-layout");

        TextField searchField = new TextField();
        searchField.getStyle().set("padding-left", "15px");
        searchField.setWidth("30%");
        searchField.setPlaceholder("Search by client, contract number or invoice number");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> {
            invoiceFilter.setSearchTerm(e.getValue());
            filterDataProvider.setFilter(invoiceFilter);
        });
        searchLayout.add(searchField);
        return searchLayout;
    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = new HorizontalLayout();
        topButtonLayout.add(emptyButton);
        emptyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        emptyButton.getStyle().set("margin-left", "auto");
        topButtonLayout.getStyle().set("padding-right", "15px");
        topButtonLayout.getStyle().set("border-bottom", "1px solid var(--lumo-contrast-10pct)");
        return topButtonLayout;
    }

}
