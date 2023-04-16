package io.mkolodziejczyk92.views.client;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import io.mkolodziejczyk92.data.entity.Client;
import io.mkolodziejczyk92.data.service.ClientService;
import io.mkolodziejczyk92.utils.BeanProvider;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;


public class ClientDataProvider extends AbstractBackEndDataProvider<Client, ClientFilter> {


    final List<Client> allClients;

    public ClientDataProvider() {
        allClients = BeanProvider.getBean(ClientService.class).allClients();
    }

    @Override
    protected Stream<Client> fetchFromBackEnd(Query<Client, ClientFilter> query) {
        Stream<Client> stream = allClients.stream();

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
    protected int sizeInBackEnd(Query<Client, ClientFilter> query) {
        return (int) fetchFromBackEnd(query).count();
    }

    private static Comparator<Client> sortComparator(List<QuerySortOrder> sortOrders) {
        return sortOrders.stream().map(sortOrder -> {
            Comparator<Client> comparator = clientFieldComparator(sortOrder.getSorted());

            if (sortOrder.getDirection() == SortDirection.DESCENDING) {
                comparator = comparator.reversed();
            }

            return comparator;
        }).reduce(Comparator::thenComparing).orElse((p1, p2) -> 0);
    }

    private static Comparator<Client> clientFieldComparator(String sorted) {
        if (sorted.equals("name")) {
            return Comparator.comparing(Client::getFullName);
        } else if (sorted.equals("email")) {
            return Comparator.comparing(Client::getNip);
        } else if (sorted.equals("nip")) {
            return Comparator.comparing(Client::getEmail);
        } else if (sorted.equals("phoneNumber")) {
            return Comparator.comparing(Client::getPhoneNumber);
        }
        return (p1, p2) -> 0;
    }
}

