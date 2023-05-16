package io.mkolodziejczyk92.views.user;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.mkolodziejczyk92.data.controllers.UsersViewController;
import io.mkolodziejczyk92.data.entity.User;
import io.mkolodziejczyk92.utils.ComponentFactory;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Users")
@Route(value = "users", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class UsersView extends Div {

    private final UsersViewController usersViewController;
    private final Button newUserButton = ComponentFactory.createStandardButton("New User");


    public UsersView(UsersViewController usersViewController) {
        this.usersViewController = usersViewController;

        Grid<User> grid = new Grid<>(User.class, false);
        grid.addColumn(User::getUserName).setHeader("User name");
        grid.addColumn(User::getFirstName).setHeader("First name");
        grid.addColumn(User::getLastName).setHeader("Last name");
        grid.addColumn(User::getEmail).setHeader("Email");
        grid.getColumns().forEach(userColumn -> userColumn.setAutoWidth(true));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setItems(usersViewController::allUsersPageableStream);

        GridContextMenu<User> menu = grid.addContextMenu();
        menu.addItem("Edit", event -> {
            if (event.getItem().isPresent()) {
                usersViewController.editUserInformation(event.getItem().get());
            } else menu.close();
        });
        menu.addItem("Delete", event -> {
            if (event.getItem().isPresent()) {
                Dialog confirmDialog = createDialogConfirmForDeleteUser(event.getItem().get());
                add(confirmDialog);
                confirmDialog.open();
            } else menu.close();
        });

        add(createTopButtonLayout());
        add(grid);

    }

    private Component createTopButtonLayout() {
        HorizontalLayout topButtonLayout = ComponentFactory.createTopButtonLayout();
        newUserButton.addClickListener(e -> usersViewController.addNewUser());
        topButtonLayout.add(newUserButton);
        return topButtonLayout;
    }

    private Dialog createDialogConfirmForDeleteUser(User user) {
        Dialog dialog = new Dialog();
        dialog.add(String.format("Are you sure you want to delete this user: %s?", user.getUserName()));
        Button deleteButton = new Button("Delete", event ->
        {
            usersViewController.deleteUser(user);
            dialog.close();
        });
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteButton.getStyle().set("margin-right", "auto");
        dialog.getFooter().add(deleteButton);

        Button cancelButton = new Button("Cancel", event -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        dialog.getFooter().add(cancelButton);
        return dialog;
    }

}
