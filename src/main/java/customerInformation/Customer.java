/**
 * @author <a href="mailto:sabrinee.ayachi@gmail.com">sayachi</a>
 * @since 10 nov. 2019
 */
public class Customer {
    private int unixTimestamp;

    private int customerId;

    private String station;

    public int getUnixTimestamp() {
        return unixTimestamp;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getStation() {
        return station;
    }

    public Customer(CustomerInput input) {
        this.unixTimestamp = input.getUnixTimestamp();
        this.customerId = input.getCustomerId();
        this.station = input.getStation();
    }

    public void setUnixTimestamp(int unixTimestamp) {
        this.unixTimestamp = unixTimestamp;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
