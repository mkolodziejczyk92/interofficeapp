package io.mkolodziejczyk92.views.user;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.mkolodziejczyk92.data.controllers.UserViewController;
import io.mkolodziejczyk92.data.entity.User;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Users")
@Route(value = "users", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class UsersView extends Div {

    private final UserViewController userViewController;
    private final Button newUserButton = new Button("Add new user");


    public UsersView(io.mkolodziejczyk92.data.controllers.UserViewController userViewController) {
        this.userViewController = userViewController;
        userViewController.initView(this);

        Grid<User> grid = new Grid<>(User.class, false);
        grid.addColumn(User::getUserName).setHeader("User name");
        grid.addColumn(User::getFirstName).setHeader("First name");
        grid.addColumn(User::getLastName).setHeader("Last name");
        grid.addColumn(User::getEmail).setHeader("Email");
        grid.getColumns().forEach(userColumn -> userColumn.setAutoWidth(true));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setItems(userViewController::allUsersPageableStream);




        GridContextMenu<User> menu = grid.addContextMenu();
        menu.addItem("View", event -> {
        });
        menu.addItem("Edit", event -> {
        });
        menu.addItem("Delete", event -> {
        });

        add(createTopButtonLayout());
        add(grid);

    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = new HorizontalLayout();
        topButtonLayout.addClassName("button-layout");
        topButtonLayout.getStyle().set("padding-right", "15px");
        newUserButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newUserButton.getStyle().set("margin-left", "auto");
        newUserButton.addClickListener(e -> UI.getCurrent().navigate(NewUserFormView.class));
        topButtonLayout.getStyle().set("border-bottom", "1px solid var(--lumo-contrast-10pct)");

        topButtonLayout.add(newUserButton);
        return topButtonLayout;

    }

}
