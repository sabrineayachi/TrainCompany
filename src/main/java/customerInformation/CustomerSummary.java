package customerInformation;

import java.util.List;

/**
 * @author <a href="mailto:sabrinee.ayachi@gmail.com">sayachi</a>
 * @since 10 nov. 2019
 */
public class CustomerSummary {
    private List<CustomerOutput> customerSummaries;

    public List<CustomerOutput> getCustomerSummaries() {
        return customerSummaries;
    }

    public void setCustomerSummaries(List<CustomerOutput> customerSummaries) {
        this.customerSummaries = customerSummaries;
    }
}
