package io.mkolodziejczyk92.views.purchase;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.*;
import io.mkolodziejczyk92.data.controllers.PurchasesViewController;
import io.mkolodziejczyk92.data.entity.Purchase;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@PageTitle("Client Purchases")
@Route(value = "clientPurchases", layout = MainLayout.class)
@PermitAll
public class ClientPurchasesView extends Div implements HasUrlParameter<String> {

    private final PurchasesViewController purchasesViewController;
    private Long clientId;

    private Button newPurchaseButton = new Button("Add new purchase");
    private Button back = new Button("Back");
    private Grid<Purchase> grid = new Grid<>();

    @Override
    public void setParameter(BeforeEvent event, @WildcardParameter String id) {
        this.clientId = Long.valueOf(id);
        grid.setItems(purchasesViewController.clientPurchases(clientId));
    }

    public ClientPurchasesView(PurchasesViewController purchasesViewController) {
        this.purchasesViewController = purchasesViewController;

        grid.addColumn(purchase -> purchase.getClient().getFullName()).setHeader("Client");
        grid.addColumn(Purchase::getContractNumber).setHeader("Contract number");
        grid.addColumn(purchase -> purchase.getSupplier().getNameOfCompany()).setHeader("Supplier");
        grid.addColumn(Purchase::getNetAmount).setHeader("Net amount");
        grid.addColumn(purchase -> purchase.getCommodityType().getName()).setHeader("Commodity type");
        grid.addColumn(purchase -> purchase.getStatus().getStatusName()).setHeader("Status");
        grid.addColumn(Purchase::getSupplierPurchaseNumber).setHeader("Supplier purchase number");
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, purchase) -> {
                    if (!purchase.getComment().isBlank()) {
                        button.addThemeVariants(ButtonVariant.LUMO_ICON,
                                ButtonVariant.LUMO_PRIMARY);
                        button.addClickListener(e -> {
                            Dialog commentDialog = createCommentDialogLayout(purchase.getComment());
                            add(commentDialog);
                            commentDialog.open();
                        });
                    } else {
                        button.addThemeVariants(ButtonVariant.LUMO_ICON,
                                ButtonVariant.LUMO_CONTRAST);
                    }
                    button.setIcon(new Icon(VaadinIcon.COMMENT));
                })).setHeader("Comment");
        grid.getColumns().forEach(purchaseColumn -> purchaseColumn.setAutoWidth(true));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);


        GridContextMenu<Purchase> menu = grid.addContextMenu();
        menu.addItem("Edit", event -> {
            if (event.getItem().isPresent()) {
                purchasesViewController.editPurchaseInformation(event.getItem().get());
            } else menu.close();
        });
        menu.addItem("Delete", event -> {
            if (event.getItem().isPresent()) {
                purchasesViewController.deletePurchase(event.getItem().get());
            } else menu.close();
        });
        add(createTopButtonLayout());
        add(grid);
    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = new HorizontalLayout();
        topButtonLayout.add(newPurchaseButton);
        newPurchaseButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newPurchaseButton.getStyle().set("margin-left", "auto");
        newPurchaseButton.addClickListener(e -> UI.getCurrent().navigate(NewPurchaseFormView.class));
        topButtonLayout.getStyle().set("padding-right", "15px");
        topButtonLayout.getStyle().set("border-bottom", "1px solid var(--lumo-contrast-10pct)");
        return topButtonLayout;
    }

    private Dialog createCommentDialogLayout(String comment) {
        Dialog dialog = new Dialog();
        dialog.setModal(false);
        dialog.setDraggable(true);
        dialog.setHeaderTitle("Comment");
        dialog.add(comment);
        Button cancelButton = new Button("Close", event -> dialog.close());
        cancelButton.addClickShortcut(Key.ESCAPE);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        dialog.getFooter().add(cancelButton);
        return dialog;
    }
}



