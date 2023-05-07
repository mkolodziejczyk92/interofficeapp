package io.mkolodziejczyk92.views.purchase;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import io.mkolodziejczyk92.data.controllers.PurchasesViewController;
import io.mkolodziejczyk92.data.entity.Purchase;
import io.mkolodziejczyk92.data.service.PurchaseService;
import io.mkolodziejczyk92.utils.BeanProvider;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class PurchaseDataProvider extends AbstractBackEndDataProvider<Purchase, PurchaseFilter> {

    final List<Purchase> allPurchases;

    public PurchaseDataProvider() {
        allPurchases = BeanProvider.getBean(PurchasesViewController.class).allPurchases();
    }

    @Override
    protected Stream<Purchase> fetchFromBackEnd(Query<Purchase, PurchaseFilter> query) {
        Stream<Purchase> stream = allPurchases.stream();

        // Filtering
        if (query.getFilter().isPresent()) {
            stream = stream.filter(person -> query.getFilter().get().test(person));
        }

        // Sorting
        if (query.getSortOrders().size() > 0) {
            stream = stream.sorted(sortComparator(query.getSortOrders()));
        }

        // Pagination
        return stream.skip(query.getOffset()).limit(query.getLimit());
    }

    @Override
    protected int sizeInBackEnd(Query<Purchase, PurchaseFilter> query) {
        return (int) fetchFromBackEnd(query).count();
    }

    private static Comparator<Purchase> sortComparator(List<QuerySortOrder> sortOrders) {
        return sortOrders.stream().map(sortOrder -> {
            Comparator<Purchase> comparator = purchaseFieldComparator(sortOrder.getSorted());

            if (sortOrder.getDirection() == SortDirection.DESCENDING) {
                comparator = comparator.reversed();
            }

            return comparator;
        }).reduce(Comparator::thenComparing).orElse((p1, p2) -> 0);
    }

    private static Comparator<Purchase> purchaseFieldComparator(String sorted) {
        if (sorted.equals("name")) {
            return Comparator.comparing(purchase -> purchase.getClient().getFullName());
        } else if (sorted.equals("supplierName")) {
            return Comparator.comparing(purchase -> purchase.getSupplier().getNameOfCompany());
        } else if (sorted.equals("supplierPurchaseNumber")) {
            return Comparator.comparing(Purchase::getSupplierPurchaseNumber);
        } else if (sorted.equals("contractNumber")) {
            return Comparator.comparing(Purchase::getContractNumber);
        }
        return (p1, p2) -> 0;
    }
}
