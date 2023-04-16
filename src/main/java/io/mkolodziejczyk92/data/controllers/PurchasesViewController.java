package io.mkolodziejczyk92.data.controllers;

import io.mkolodziejczyk92.data.entity.Purchase;
import io.mkolodziejczyk92.data.service.PurchaseService;
import io.mkolodziejczyk92.views.purchase.PurchasesView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class PurchasesViewController {

    private final PurchaseService purchaseService;

    private PurchasesView purchasesView;

    public PurchasesViewController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    public void initView(PurchasesView purchasesView) {
        this.purchasesView = purchasesView;
    }

    public List<Purchase> allPurchases(){
        return purchaseService.allPurchases();
    }
}
