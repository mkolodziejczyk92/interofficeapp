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

import static io.mkolodziejczyk92.utils.ComponentFactory.*;

@PageTitle("New Supplier")
@Route(value = "new-supplier", layout = MainLayout.class)
@PermitAll
public class NewSupplierFormView extends Div {

    private final SupplierFormViewController supplierFormViewController;

    private final SuppliersViewController suppliersViewController;
    private final TextField nameOfCompany = new TextField("Name of company");
    private final TextField nip = new TextField("NIP");
    private final Button cancel = createCancelButton();
    private final Button save = createSaveButton();
    private final Button back = createBackButton();

    private final Binder<Supplier> binder = new Binder<>(Supplier.class);

    public NewSupplierFormView(SuppliersViewController suppliersViewController, SupplierFormViewController supplierFormViewController) {
        this.suppliersViewController = suppliersViewController;
        this.supplierFormViewController = supplierFormViewController;
        supplierFormViewController.initBinder(binder);

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
