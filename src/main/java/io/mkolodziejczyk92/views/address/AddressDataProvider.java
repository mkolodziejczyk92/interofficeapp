package io.mkolodziejczyk92.views.address;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import io.mkolodziejczyk92.data.entity.Address;
import io.mkolodziejczyk92.data.service.AddressService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class AddressDataProvider extends AbstractBackEndDataProvider<Address, AddressFilter> {

    final List<Address> DATABASE = new ArrayList<>(AddressService.allAddresses());

    @Override
    protected Stream<Address> fetchFromBackEnd(Query<Address, AddressFilter> query) {
        Stream<Address> stream = DATABASE.stream();

        // Filtering
        if (query.getFilter().isPresent()) {
            stream = stream.filter(address -> query.getFilter().get().test(address));
        }

        // Sorting
        if (query.getSortOrders().size() > 0) {
            stream = stream.sorted(sortComparator(query.getSortOrders()));
        }

        // Pagination
        return stream.skip(query.getOffset()).limit(query.getLimit());
    }

    @Override
    protected int sizeInBackEnd(Query<Address, AddressFilter> query) {
        return (int) fetchFromBackEnd(query).count();
    }

    private static Comparator<Address> sortComparator(List<QuerySortOrder> sortOrders) {
        return sortOrders.stream().map(sortOrder -> {
            Comparator<Address> comparator = addressFieldComparator(sortOrder.getSorted());

            if (sortOrder.getDirection() == SortDirection.DESCENDING) {
                comparator = comparator.reversed();
            }

            return comparator;
        }).reduce(Comparator::thenComparing).orElse((p1, p2) -> 0);
    }

    private static Comparator<Address> addressFieldComparator(String sorted) {
        if (sorted.equals("clientFullName")) {
            return Comparator.comparing(address -> address.getClient().getFullName());
        } else if (sorted.equals("street")) {
            return Comparator.comparing(Address::getStreet);
        } else if (sorted.equals("houseNumber")) {
            return Comparator.comparing(Address::getHouseNumber);
        } else if (sorted.equals("apartmentNumber")) {
            return Comparator.comparing(Address::getApartmentNumber);
        } else if (sorted.equals("zipCode")) {
            return Comparator.comparing(Address::getZipCode);
        } else if (sorted.equals("city")) {
            return Comparator.comparing(Address::getCity);
        } else if (sorted.equals("voivodeship")) {
            return Comparator.comparing(Address::getVoivodeship);
        } else if (sorted.equals("plotNumber")) {
            return Comparator.comparing(Address::getPlotNumber);
        }
        return (p1, p2) -> 0;
    }
}

