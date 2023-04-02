package io.mkolodziejczyk92.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;
import io.mkolodziejczyk92.components.appnav.AppNav;
import io.mkolodziejczyk92.components.appnav.AppNavItem;
import io.mkolodziejczyk92.data.entity.User;
import io.mkolodziejczyk92.security.AuthenticatedUser;
import io.mkolodziejczyk92.views.address.AddressesView;
import io.mkolodziejczyk92.views.clients.ClientsView;
import io.mkolodziejczyk92.views.contracts.ContractsView;
import io.mkolodziejczyk92.views.invoice.InvoicesView;
import io.mkolodziejczyk92.views.orders.OrdersView;
import io.mkolodziejczyk92.views.users.UserView;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    private AuthenticatedUser authenticatedUser;
    private AccessAnnotationChecker accessChecker;

    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;

        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        Image image = new Image("/images/logo.png", "logo INTERDOM");
        H1 appName = new H1("interOfficeAPP");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);
        header.add(image);
        Scroller scroller = new Scroller(createNavigation());
        addToDrawer(header, scroller, createFooter());
    }

    private AppNav createNavigation() {
        // AppNav is not yet an official component.
        // For documentation, visit https://github.com/vaadin/vcf-nav#readme
        AppNav nav = new AppNav();

        if (accessChecker.hasAccess(ClientsView.class)) {
            nav.addItem(new AppNavItem("Clients", ClientsView.class, LineAwesomeIcon.USER.create()));

        }
        if (accessChecker.hasAccess(OrdersView.class)) {
            nav.addItem(new AppNavItem("Orders", OrdersView.class, LineAwesomeIcon.FILE_INVOICE_SOLID.create()));

        }
        if (accessChecker.hasAccess(ContractsView.class)) {
            nav.addItem(new AppNavItem("Contracts", ContractsView.class,
                    LineAwesomeIcon.FILE_INVOICE_DOLLAR_SOLID.create()));

        }
        if (accessChecker.hasAccess(InvoicesView.class)) {
            nav.addItem(new AppNavItem("Invoices", InvoicesView.class, LineAwesomeIcon.DOLLAR_SIGN_SOLID.create()));

        }
        if (accessChecker.hasAccess(UserView.class)) {
            nav.addItem(new AppNavItem("Users", UserView.class, LineAwesomeIcon.USER_CIRCLE_SOLID.create()));

        }
        if (accessChecker.hasAccess(AddressesView.class)) {
            nav.addItem(new AppNavItem("Addresses", AddressesView.class, LineAwesomeIcon.MAP_MARKER_SOLID.create()));

        }

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div div = new Div();
            div.add(user.getUsername());
            div.add(new Icon("lumo", "dropdown"));
            div.getElement().getStyle().set("display", "flex");
            div.getElement().getStyle().set("align-items", "center");
            div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
            userName.add(div);
            userName.getSubMenu().addItem("Log out", e -> {
                authenticatedUser.logout();
            });

            layout.add(userMenu);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
