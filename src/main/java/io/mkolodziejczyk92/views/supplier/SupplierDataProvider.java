package io.mkolodziejczyk92.views.supplier;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import io.mkolodziejczyk92.data.controllers.SuppliersViewController;
import io.mkolodziejczyk92.data.entity.Supplier;
import io.mkolodziejczyk92.utils.BeanProvider;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class SupplierDataProvider extends AbstractBackEndDataProvider<Supplier, SupplierFilter> {

    private final List<Supplier> allSuppliers;

    public SupplierDataProvider() {
        allSuppliers = BeanProvider.getBean(SuppliersViewController.class).allSuppliers();
    }

    @Override
    protected Stream<Supplier> fetchFromBackEnd(Query<Supplier, SupplierFilter> query) {
        Stream<Supplier> stream = allSuppliers.stream();

        // Filtering
        if (query.getFilter().isPresent()) {
            stream = stream.filter(supplier -> query.getFilter().get().test(supplier));
        }

        // Sorting
        if (query.getSortOrders().size() > 0) {
            stream = stream.sorted(sortComparator(query.getSortOrders()));
        }

        // Pagination
        return stream.skip(query.getOffset()).limit(query.getLimit());
    }

    @Override
    protected int sizeInBackEnd(Query<Supplier, SupplierFilter> query) {
        return (int) fetchFromBackEnd(query).count();
    }

    private static Comparator<Supplier> sortComparator(List<QuerySortOrder> sortOrders) {
        return sortOrders.stream().map(sortOrder -> {
            Comparator<Supplier> comparator = supplierFieldComparator(sortOrder.getSorted());

            if (sortOrder.getDirection() == SortDirection.DESCENDING) {
                comparator = comparator.reversed();
            }

            return comparator;
        }).reduce(Comparator::thenComparing).orElse((p1, p2) -> 0);
    }

    private static Comparator<Supplier> supplierFieldComparator(String sorted) {
        if (sorted.equals("nameOfCompany")) {
            return Comparator.comparing(Supplier::getNameOfCompany);
        } else if (sorted.equals("nip")) {
            return Comparator.comparing(Supplier::getNip);
        }
        return (p1, p2) -> 0;
    }
}
