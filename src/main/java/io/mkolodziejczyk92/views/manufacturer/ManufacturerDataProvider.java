package io.mkolodziejczyk92.views.manufacturer;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import io.mkolodziejczyk92.data.controllers.ManufacturersViewController;
import io.mkolodziejczyk92.data.entity.Manufacturer;
import io.mkolodziejczyk92.utils.BeanProvider;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class ManufacturerDataProvider extends AbstractBackEndDataProvider<Manufacturer, ManufacturerFilter> {

    private final List<Manufacturer> allManufacturers;

    public ManufacturerDataProvider() {
        allManufacturers = BeanProvider.getBean(ManufacturersViewController.class).allManufacturers();
    }

    @Override
    protected Stream<Manufacturer> fetchFromBackEnd(Query<Manufacturer, ManufacturerFilter> query) {
        Stream<Manufacturer> stream = allManufacturers.stream();

        // Filtering
        if (query.getFilter().isPresent()) {
            stream = stream.filter(manufacturer -> query.getFilter().get().test(manufacturer));
        }

        // Sorting
        if (query.getSortOrders().size() > 0) {
            stream = stream.sorted(sortComparator(query.getSortOrders()));
        }

        // Pagination
        return stream.skip(query.getOffset()).limit(query.getLimit());
    }

    @Override
    protected int sizeInBackEnd(Query<Manufacturer, ManufacturerFilter> query) {
        return (int) fetchFromBackEnd(query).count();
    }

    private static Comparator<Manufacturer> sortComparator(List<QuerySortOrder> sortOrders) {
        return sortOrders.stream().map(sortOrder -> {
            Comparator<Manufacturer> comparator = supplierFieldComparator(sortOrder.getSorted());

            if (sortOrder.getDirection() == SortDirection.DESCENDING) {
                comparator = comparator.reversed();
            }

            return comparator;
        }).reduce(Comparator::thenComparing).orElse((p1, p2) -> 0);
    }

    private static Comparator<Manufacturer> supplierFieldComparator(String sorted) {
        if (sorted.equals("nameOfCompany")) {
            return Comparator.comparing(Manufacturer::getNameOfCompany);
        } else if (sorted.equals("nip")) {
            return Comparator.comparing(Manufacturer::getNip);
        }
        return (p1, p2) -> 0;
    }

    public void removeManufacturerFromGrid(Manufacturer manufacturer) {
        allManufacturers.remove(manufacturer);
    }
}
