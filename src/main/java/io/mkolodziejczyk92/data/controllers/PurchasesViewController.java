package io.mkolodziejczyk92.data.controllers;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import io.mkolodziejczyk92.data.entity.Purchase;
import io.mkolodziejczyk92.data.service.PurchaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
public class PurchasesViewController {

    private final PurchaseService purchaseService;

    public PurchasesViewController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    public List<Purchase> allPurchases() {
        return purchaseService.allPurchases();
    }

    public void editPurchaseInformation(Purchase purchase) {
        if (purchaseService.isExist(purchase.getId())) {
            UI.getCurrent().navigate("new-purchase/" + purchase.getId());
        } else {
            Notification.show(purchase.getClient().getFullName()
                    + "'s purchase does not exist in the database.");
        }
    }

    public void createNewPurchaseForClient(String clientId) {
        if (Objects.isNull(clientId)) {
            UI.getCurrent().navigate("new-purchase");
        } else {
            UI.getCurrent().navigate("new-purchase/" + clientId);
        }
    }

    public void deletePurchase(Purchase purchase) {
        try {
            purchaseService.delete(purchase.getId());
        } catch (DataIntegrityViolationException e) {
            Notification.show("Purchase "
                    + purchase.getId()
                    + " cannot be deleted because it has connections in the database.");
            return;
        }
        Notification.show("Purchase " + purchase.getId() + " deleted.");
    }

    public List<Purchase> clientPurchases(Long clientId) {
        return purchaseService.clientPurchases(clientId);
    }

    public List<Purchase> purchasesSentToSupplier(Long supplierId) {
        return purchaseService.allPurchaseForSupplier(supplierId);
    }

    public List<Purchase> purchasesSentToManufacturer(Long manufacturerId) {
        return purchaseService.allPurchasesForManufacturer(manufacturerId);
    }
}

