package io.mkolodziejczyk92.views.supplier;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.mkolodziejczyk92.data.controllers.SupplierFormViewController;
import io.mkolodziejczyk92.data.controllers.SuppliersViewController;
import io.mkolodziejczyk92.data.entity.Supplier;
import io.mkolodziejczyk92.views.MainLayout;
import io.mkolodziejczyk92.views.address.AddressesView;
import jakarta.annotation.security.PermitAll;

@PageTitle("New Supplier")
@Route(value = "new-supplier", layout = MainLayout.class)
@PermitAll
public class NewSupplierFormView extends Div {

    private final SupplierFormViewController supplierFormViewController;

    private final SuppliersViewController suppliersViewController;
    private TextField nameOfCompany = new TextField("Name of company");
    private TextField nip = new TextField("NIP");
    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");
    private Button back = new Button("Back");

    private Binder<Supplier> binder = new Binder<>(Supplier.class);

    public NewSupplierFormView(SuppliersViewController suppliersViewController, SupplierFormViewController supplierFormViewController) {
        this.suppliersViewController = suppliersViewController;
        this.supplierFormViewController = supplierFormViewController;
        supplierFormViewController.initView(this, binder);

        addClassName("supplier-view");

        add(createTopButtonLayout());
        add(createFormLayout());
        add(createBottomButtonLayout());
        binder.bindInstanceFields(this);

        supplierFormViewController.clearForm();

    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(nameOfCompany, nip);
        formLayout.getStyle().set("padding-left", "30px");
        formLayout.getStyle().set("padding-right", "30px");

        return formLayout;
    }

    private Component createTopButtonLayout(){
        HorizontalLayout topButtonLayout = new HorizontalLayout();
        topButtonLayout.getStyle().set("padding-right", "15px");
        topButtonLayout.getStyle().set("border-bottom", "1px solid var(--lumo-contrast-10pct)");

        back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        back.addClickListener(e -> UI.getCurrent().navigate(SuppliersView.class));
        back.getStyle().set("margin-left", "auto");

        topButtonLayout.add(back);
        return topButtonLayout;
    }

    private Component createBottomButtonLayout() {
        HorizontalLayout bottomButtonLayout = new HorizontalLayout();
        bottomButtonLayout.getStyle().set("padding-top", "30px");
        bottomButtonLayout.getStyle().set("padding-left", "30px");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        bottomButtonLayout.add(save);
        bottomButtonLayout.add(cancel);


        cancel.addClickListener(e -> supplierFormViewController.clearForm());
        save.addClickListener(e -> {
            supplierFormViewController.saveNewSupplier(binder.getBean());
            Notification.show(binder.getBean().getClass().getSimpleName() + " stored.");
            supplierFormViewController.clearForm();
        });
        return bottomButtonLayout;
    }
}
