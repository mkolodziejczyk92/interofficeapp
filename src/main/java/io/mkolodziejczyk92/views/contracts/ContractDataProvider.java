package io.mkolodziejczyk92.views.contracts;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import io.mkolodziejczyk92.data.entity.Contract;
import io.mkolodziejczyk92.data.service.ContractService;
import io.mkolodziejczyk92.utils.BeanProvider;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class ContractDataProvider extends AbstractBackEndDataProvider<Contract, ContractFilter> {
    private final List<Contract> allContracts;

    public ContractDataProvider() {
        allContracts = BeanProvider.getBean(ContractService.class).allContracts();
    }

    @Override
    protected Stream<Contract> fetchFromBackEnd(Query<Contract, ContractFilter> query) {
        Stream<Contract> stream = allContracts.stream();

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
    protected int sizeInBackEnd(Query<Contract, ContractFilter> query) {
        return (int) fetchFromBackEnd(query).count();
    }

    private static Comparator<Contract> sortComparator(List<QuerySortOrder> sortOrders) {
        return sortOrders.stream().map(sortOrder -> {
            Comparator<Contract> comparator = addressFieldComparator(sortOrder.getSorted());

            if (sortOrder.getDirection() == SortDirection.DESCENDING) {
                comparator = comparator.reversed();
            }

            return comparator;
        }).reduce(Comparator::thenComparing).orElse((p1, p2) -> 0);
    }

    private static Comparator<Contract> addressFieldComparator(String sorted) {
        if (sorted.equals("clientFullName")) {
            return Comparator.comparing(contract -> contract.getClient().getFullName());
        } else if (sorted.equals("street")) {
            return Comparator.comparing(Contract::getNumber);
        }
        return (p1, p2) -> 0;
    }
}
