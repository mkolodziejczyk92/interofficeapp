package io.mkolodziejczyk92.data.controllers;

import io.mkolodziejczyk92.data.entity.Purchase;
import io.mkolodziejczyk92.data.service.PurchaseService;
import io.mkolodziejczyk92.views.address.ClientAddressesView;
import io.mkolodziejczyk92.views.purchase.ClientPurchasesView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class ClientPurchasesViewController {

    private final PurchaseService purchaseService;
    private ClientPurchasesView clientPurchasesView;


    public ClientPurchasesViewController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }
    public void initView(ClientPurchasesView clientPurchasesView) {
        this.clientPurchasesView = clientPurchasesView;
    }
    public List<Purchase> clientPurchases(Long clientId) {
        return purchaseService.clientPurchases(clientId);
    }
}
