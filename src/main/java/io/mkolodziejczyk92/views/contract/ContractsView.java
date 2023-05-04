package io.mkolodziejczyk92.views.contract;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
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
import io.mkolodziejczyk92.data.controllers.ContractsViewController;
import io.mkolodziejczyk92.data.entity.Contract;
import io.mkolodziejczyk92.views.MainLayout;
import io.mkolodziejczyk92.views.purchase.NewPurchaseFormView;
import jakarta.annotation.security.PermitAll;

@PageTitle("Contracts")
@Route(value = "contracts", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class ContractsView extends Div {

    private final ContractsViewController contractsViewController;

    private ContractFilter contractFilter = new ContractFilter();
    private ContractDataProvider contractDataProvider = new ContractDataProvider();
    private ConfigurableFilterDataProvider<Contract, Void, ContractFilter> filterDataProvider
            = contractDataProvider.withConfigurableFilter();

    private Button newContract = new Button("New Contract");


    public ContractsView(ContractsViewController contractsViewController) {
        this.contractsViewController = contractsViewController;
        contractsViewController.initView(this);

        Grid<Contract> grid = new Grid<>(Contract.class, false);
        grid.addColumn(contract -> contract.getClient().getFullName()).setHeader("Client");
        grid.addColumn(Contract::getNumber).setHeader("Contract Number");
        grid.addColumn(Contract::getNetAmount).setHeader("Net Amount");
        grid.addColumn(Contract::getSignatureDate).setHeader("Signature Date");
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
        grid.setItems(filterDataProvider);

        GridContextMenu<Contract> menu = grid.addContextMenu();
        menu.addItem("View", event -> {
        });
        menu.addItem("Edit", event -> {
            if(event.getItem().isPresent()){
                contractsViewController.editContractInformationForm(event.getItem().get().getId());
            }else menu.close();
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
        searchField.setPlaceholder("Search by client or contract number");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> {
            contractFilter.setSearchTerm(e.getValue());
            filterDataProvider.setFilter(contractFilter);
        });
        searchLayout.add(searchField);
        return searchLayout;
    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = new HorizontalLayout();
        topButtonLayout.add(newContract);
        newContract.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newContract.addClickListener( e -> UI.getCurrent().navigate(NewContractFormView.class));
        newContract.getStyle().set("margin-left", "auto");
        topButtonLayout.getStyle().set("padding-right", "15px");
        topButtonLayout.getStyle().set("border-bottom", "1px solid var(--lumo-contrast-10pct)");
        return topButtonLayout;
    }


}

