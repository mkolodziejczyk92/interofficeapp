package io.mkolodziejczyk92.views.manufacturer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.mkolodziejczyk92.data.controllers.ManufacturerFormViewController;
import io.mkolodziejczyk92.data.controllers.ManufacturersViewController;
import io.mkolodziejczyk92.data.entity.Manufacturer;
import io.mkolodziejczyk92.utils.ComponentFactory;
import io.mkolodziejczyk92.utils.validators.NipValidator;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.PermitAll;

import static io.mkolodziejczyk92.utils.ComponentFactory.*;

@PageTitle("New Manufacturer")
@Route(value = "new-manufacturer", layout = MainLayout.class)
@PermitAll
public class NewManufacturerFormView extends Div {

    private final ManufacturerFormViewController manufacturerFormViewController;

    private final ManufacturersViewController manufacturersViewController;
    private final TextField nameOfCompany = new TextField("Name of company");
    private final TextField nip = new TextField("NIP");
    private final Button cancel = createCancelButton();
    private final Button save = createSaveButton();
    private final Button back = createBackButton();

    private final Binder<Manufacturer> binder = new Binder<>(Manufacturer.class);

    public NewManufacturerFormView(ManufacturerFormViewController manufacturerFormViewController, ManufacturersViewController manufacturersViewController) {
        this.manufacturerFormViewController = manufacturerFormViewController;
        this.manufacturersViewController = manufacturersViewController;

        manufacturerFormViewController.initBinder(binder);

        add(createTopButtonLayout());
        add(createFormLayout());
        add(createBottomButtonLayout());
        binder.forField(nameOfCompany)
                .asRequired("Company name is required")
                .bind(Manufacturer::getNameOfCompany, Manufacturer::setNameOfCompany);

        binder.forField(nip)
                .withValidator(new NipValidator())
                .bind(Manufacturer::getNip, Manufacturer::setNip);

        manufacturerFormViewController.clearForm();

    }

    private Component createFormLayout() {
        return ComponentFactory.createFormLayout(nameOfCompany, nip);
    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = ComponentFactory.createTopButtonLayout();
        back.addClickListener(e -> UI.getCurrent().navigate(ManufacturersView.class));
        topButtonLayout.add(back);
        return topButtonLayout;
    }

    private Component createBottomButtonLayout() {
        HorizontalLayout bottomButtonLayout = ComponentFactory.createBottomButtonLayout();

        bottomButtonLayout.add(cancel, save);

        cancel.addClickListener(e -> manufacturerFormViewController.clearForm());
        save.addClickListener(e -> {
            manufacturerFormViewController.saveNewManufacturer(binder.getBean());
        });
        return bottomButtonLayout;
    }
}
