package io.mkolodziejczyk92.utils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ComponentFactory {

    public static final String PARAMETER_FOR_CLIENT_ID_FROM_GRID = "c";

    public static TextField createTextFieldForSearchLayout(String placeholder){
        TextField searchField = new TextField();
        searchField.getStyle().set("padding-left", "15px");
        searchField.setWidth("30%");
        searchField.setPlaceholder(placeholder);
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);

        return searchField;
    }

    public static FormLayout createFormLayout(Component...args){
        FormLayout formLayout = new FormLayout();
        formLayout.getStyle().set("padding-left", "30px");
        formLayout.getStyle().set("padding-right", "30px");
        formLayout.add(args);
        return formLayout;
    }

    public static HorizontalLayout createTopButtonLayout(){
        HorizontalLayout topButtonLayout = new HorizontalLayout();
        topButtonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        topButtonLayout.getStyle().set("padding-right", "15px");
        topButtonLayout.getStyle().set("border-bottom", "1px solid var(--lumo-contrast-10pct)");
        return topButtonLayout;
    }

    public static HorizontalLayout createBottomButtonLayout(){
        HorizontalLayout bottomButtonLayout = new HorizontalLayout();
        bottomButtonLayout.getStyle().set("padding-left", "30px");
        bottomButtonLayout.getStyle().set("padding-top", "30px");
        return bottomButtonLayout;
    }

    public static Button createBackButton(){
        Button back = new Button("Back");
        back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        back.addClickShortcut(Key.ESCAPE);
        return back;
    }

    public static Button createSaveButton(){
        Button save = new Button("Save");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);
        return save;
    }

    public static Button createUpdateButton(){
        Button update = new Button("Update");
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        update.addClickShortcut(Key.ENTER);
        return update;
    }

    public static Button createCancelButton(){
        Button cancel = new Button("Cancel");
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return cancel;
    }

    public static Button createStandardButton(String nameOfButton){
        Button button = new Button(nameOfButton);
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }

}
