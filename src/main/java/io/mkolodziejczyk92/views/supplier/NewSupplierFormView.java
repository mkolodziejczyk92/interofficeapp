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

        add(createFormLayout());
        add(createButtonLayout());
        binder.bindInstanceFields(this);

        supplierFormViewController.clearForm();

    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(nameOfCompany, nip);

        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        buttonLayout.add(back);

        cancel.addClickListener(e -> supplierFormViewController.clearForm());
        save.addClickListener(e -> {
            supplierFormViewController.saveNewSupplier(binder.getBean());
            Notification.show(binder.getBean().getClass().getSimpleName() + " stored.");
            supplierFormViewController.clearForm();
        });
        back.addClickListener(e -> UI.getCurrent().navigate(SuppliersView.class));

        return buttonLayout;
    }
}
