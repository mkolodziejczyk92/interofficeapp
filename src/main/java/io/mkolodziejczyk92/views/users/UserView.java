package io.mkolodziejczyk92.views.users;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import io.mkolodziejczyk92.data.entity.Invoice;
import io.mkolodziejczyk92.data.entity.User;
import io.mkolodziejczyk92.data.service.UserService;
import io.mkolodziejczyk92.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@PageTitle("Users")
@Route(value = "Users/:usersID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class UserView extends Div implements BeforeEnterObserver {

    private final String USERS_ID = "usersID";

    private final Grid<User> grid = new Grid<>(User.class, false);

    private User user;

    private final UserService userService;

    public UserView(UserService userService) {
        this.userService = userService;
        addClassNames("user-view");

        VerticalLayout verticalLayout = new VerticalLayout();

        createGridLayout();

        grid.addColumn("username").setAutoWidth(true);
        grid.addColumn("firstName").setAutoWidth(true);
        grid.addColumn("lastName").setAutoWidth(true);
        grid.addColumn("email").setAutoWidth(true);
        grid.setItems(query -> userService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        GridContextMenu<User> menu = grid.addContextMenu();

        menu.addItem("View", event -> {
        });
        menu.addItem("Edit", event -> {
        });
        menu.addItem("Delete", event -> {
        });

        add(verticalLayout);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> usersId = event.getRouteParameters().get(USERS_ID).map(Long::parseLong);
        if (usersId.isPresent()) {
            Optional<User> usersFromBackend = userService.get(usersId.get());
            if (usersFromBackend.isPresent()) {
                populateForm(usersFromBackend.get());
            } else {
                Notification.show(String.format("The requested users was not found, ID = %d", usersId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                refreshGrid();
                event.forwardTo(UserView.class);
            }
        }
    }

    private void createGridLayout() {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        add(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void populateForm(User value) {
        this.user = value;
        String topic = null;
        if (this.user != null && this.user.getId() != null) {
            topic = "users/" + this.user.getId();
        }

    }
}
