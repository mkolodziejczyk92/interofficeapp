package io.mkolodziejczyk92.views.invoice;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import io.mkolodziejczyk92.data.controllers.InvoicesViewController;
import io.mkolodziejczyk92.data.entity.Invoice;
import io.mkolodziejczyk92.utils.BeanProvider;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class InvoiceDataProvider extends AbstractBackEndDataProvider<Invoice, InvoiceFilter> {


    private final List<Invoice> allInvoices;

    public InvoiceDataProvider() {
        allInvoices = BeanProvider.getBean(InvoicesViewController.class).allInvoices();
    }

    @Override
    protected Stream<Invoice> fetchFromBackEnd(Query<Invoice, InvoiceFilter> query) {
        Stream<Invoice> stream = allInvoices.stream();

        // Filtering
        if (query.getFilter().isPresent()) {
            stream = stream.filter(invoice -> query.getFilter().get().test(invoice));
        }

        // Sorting
        if (query.getSortOrders().size() > 0) {
            stream = stream.sorted(sortComparator(query.getSortOrders()));
        }

        // Pagination
        return stream.skip(query.getOffset()).limit(query.getLimit());
    }

    @Override
    protected int sizeInBackEnd(Query<Invoice, InvoiceFilter> query) {
        return (int) fetchFromBackEnd(query).count();
    }

    private static Comparator<Invoice> sortComparator(List<QuerySortOrder> sortOrders) {
        return sortOrders.stream().map(sortOrder -> {
            Comparator<Invoice> comparator = invoiceFieldComparator(sortOrder.getSorted());

            if (sortOrder.getDirection() == SortDirection.DESCENDING) {
                comparator = comparator.reversed();
            }

            return comparator;
        }).reduce(Comparator::thenComparing).orElse((p1, p2) -> 0);
    }

    private static Comparator<Invoice> invoiceFieldComparator(String sorted) {
        if (sorted.equals("clientFullName")) {
            return Comparator.comparing(invoice -> invoice.getClient().getFullName());
        } else if (sorted.equals("invoiceNumber")) {
            return Comparator.comparing(Invoice::getNumber);
        }
        return (p1, p2) -> 0;
    }
}
