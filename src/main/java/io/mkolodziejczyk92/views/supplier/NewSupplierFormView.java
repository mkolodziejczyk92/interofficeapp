package io.mkolodziejczyk92.views.supplier;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
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
import io.mkolodziejczyk92.utils.ComponentFactory;
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
    private Button cancel = ComponentFactory.createCancelButton();
    private Button save = ComponentFactory.createSaveButton();
    private Button back = ComponentFactory.createBackButton();

    private Binder<Supplier> binder = new Binder<>(Supplier.class);

    public NewSupplierFormView(SuppliersViewController suppliersViewController, SupplierFormViewController supplierFormViewController) {
        this.suppliersViewController = suppliersViewController;
        this.supplierFormViewController = supplierFormViewController;
        supplierFormViewController.initBinder(binder);

        addClassName("supplier-view");

        add(createTopButtonLayout());
        add(createFormLayout());
        add(createBottomButtonLayout());
        binder.bindInstanceFields(this);

        supplierFormViewController.clearForm();

    }

    private Component createFormLayout() {
       return ComponentFactory.createFormLayout(nameOfCompany, nip);
    }

    private Component createTopButtonLayout(){
        HorizontalLayout topButtonLayout = ComponentFactory.createTopButtonLayout();
        back.addClickListener(e -> UI.getCurrent().navigate(SuppliersView.class));
        topButtonLayout.add(back);
        return topButtonLayout;
    }

    private Component createBottomButtonLayout() {
        HorizontalLayout bottomButtonLayout = ComponentFactory.createBottomButtonLayout();

        bottomButtonLayout.add(cancel, save);

        cancel.addClickListener(e -> supplierFormViewController.clearForm());
        save.addClickListener(e -> {
            supplierFormViewController.saveNewSupplier(binder.getBean());
            Notification.show(binder.getBean().getClass().getSimpleName() + " stored.");
            supplierFormViewController.clearForm();
        });
        return bottomButtonLayout;
    }
}
