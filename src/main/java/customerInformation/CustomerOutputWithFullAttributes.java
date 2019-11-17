package customerInformation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author <a href="mailto:sabrinee.ayachi@gmail.com">sayachi</a>
 * @since 10 nov. 2019
 */
@JsonPropertyOrder({ "customerId", "totalCostInCents", "trips" })
public class CustomerOutputWithFullAttributes extends CustomerOutput{
    @JsonProperty("customerId")
    private int costumerId;

    @JsonProperty("totalCostInCents")
    private int totalCostInCents;


    public int getCostumerId() {
        return costumerId;
    }

    public void setCostumerId(int costumerId) {
        this.costumerId = costumerId;
    }

    public int getTotalCostInCents() {
        return totalCostInCents;
    }

    public void setTotalCostInCents(int totalCostInCents) {
        this.totalCostInCents = totalCostInCents;
    }
}
