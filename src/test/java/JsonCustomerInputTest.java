import org.junit.Assert;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:sabrinee.ayachi@gmail.com">sayachi</a>
 * @since 18 nov. 2019
 */
public class JsonCustomerInputTest {

    private JsonCustomerInput jsonCustomerInput = new JsonCustomerInput();

    @Test
    public void testGetZone() throws Exception {
        String stationA = "A";
        int zoneA = Whitebox.invokeMethod(jsonCustomerInput, "getZone", stationA);
        Assert.assertEquals(1, zoneA);

        String stationB = "B";
        int zoneB = Whitebox.invokeMethod(jsonCustomerInput, "getZone", stationB);
        Assert.assertEquals(1, zoneB);

        String stationC = "C";
        int zoneC = Whitebox.invokeMethod(jsonCustomerInput, "getZone", stationC);
        Assert.assertEquals(2, zoneC);

        String stationD = "D";
        int zoneD = Whitebox.invokeMethod(jsonCustomerInput, "getZone", stationD);
        Assert.assertEquals(2, zoneD);

        String stationE = "E";
        int zoneE = Whitebox.invokeMethod(jsonCustomerInput, "getZone", stationE);
        Assert.assertEquals(3, zoneE);

        String stationF = "F";
        int zoneF = Whitebox.invokeMethod(jsonCustomerInput, "getZone", stationF);
        Assert.assertEquals(3, zoneF);

        String stationG = "G";
        int zoneG = Whitebox.invokeMethod(jsonCustomerInput, "getZone", stationG);
        Assert.assertEquals(4, zoneG);

        String stationH = "H";
        int zoneH = Whitebox.invokeMethod(jsonCustomerInput, "getZone", stationH);
        Assert.assertEquals(4, zoneH);

        String stationI = "I";
        int zoneI = Whitebox.invokeMethod(jsonCustomerInput, "getZone", stationI);
        Assert.assertEquals(4, zoneI);
    }

    @Test
    public void testGetPricingPerTrip() throws Exception {
        int pricingPerTrip1 =  Whitebox.invokeMethod(jsonCustomerInput, "getPricingPerTrip", 3, 1);
        Assert.assertEquals(280, pricingPerTrip1);

        int pricingPerTrip2 =  Whitebox.invokeMethod(jsonCustomerInput, "getPricingPerTrip", 3, 2);
        Assert.assertEquals(280, pricingPerTrip2);

        int pricingPerTrip3 =  Whitebox.invokeMethod(jsonCustomerInput, "getPricingPerTrip", 4, 1);
        Assert.assertEquals(300, pricingPerTrip3);

        int pricingPerTrip4 =  Whitebox.invokeMethod(jsonCustomerInput, "getPricingPerTrip", 4, 2);
        Assert.assertEquals(300, pricingPerTrip4);

        int pricingPerTrip5 =  Whitebox.invokeMethod(jsonCustomerInput, "getPricingPerTrip", 1, 3);
        Assert.assertEquals(280, pricingPerTrip5);

        int pricingPerTrip6 =  Whitebox.invokeMethod(jsonCustomerInput, "getPricingPerTrip", 2, 3);
        Assert.assertEquals(280, pricingPerTrip6);

        int pricingPerTrip7 =  Whitebox.invokeMethod(jsonCustomerInput, "getPricingPerTrip", 1, 4);
        Assert.assertEquals(300, pricingPerTrip7);

        int pricingPerTrip8 =  Whitebox.invokeMethod(jsonCustomerInput, "getPricingPerTrip", 2, 4);
        Assert.assertEquals(300, pricingPerTrip8);

        int pricingPerTrip9 =  Whitebox.invokeMethod(jsonCustomerInput, "getPricingPerTrip", 1, 1);
        Assert.assertEquals(240, pricingPerTrip9);

        int pricingPerTrip10 =  Whitebox.invokeMethod(jsonCustomerInput, "getPricingPerTrip", 2, 2);
        Assert.assertEquals(240, pricingPerTrip10);

        int pricingPerTrip11 =  Whitebox.invokeMethod(jsonCustomerInput, "getPricingPerTrip", 2, 1);
        Assert.assertEquals(240, pricingPerTrip11);

        int pricingPerTrip12 =  Whitebox.invokeMethod(jsonCustomerInput, "getPricingPerTrip", 1, 2);
        Assert.assertEquals(240, pricingPerTrip12);

        int pricingPerTrip13 =  Whitebox.invokeMethod(jsonCustomerInput, "getPricingPerTrip", 3, 3);
        Assert.assertEquals(200, pricingPerTrip13);

        int pricingPerTrip14 =  Whitebox.invokeMethod(jsonCustomerInput, "getPricingPerTrip", 4, 4);
        Assert.assertEquals(200, pricingPerTrip14);

        int pricingPerTrip15 =  Whitebox.invokeMethod(jsonCustomerInput, "getPricingPerTrip", 3, 4);
        Assert.assertEquals(200, pricingPerTrip15);

        int pricingPerTrip16 =  Whitebox.invokeMethod(jsonCustomerInput, "getPricingPerTrip", 4, 3);
        Assert.assertEquals(200, pricingPerTrip16);
    }

    @Test
    public void testFillCustomerListOutput() throws Exception {
        List<Trip> customerTrips = new ArrayList<Trip>();

        Trip trip = new Trip();
        trip.setZoneFrom(1);
        trip.setZoneTo(2);
        trip.setCostInCents(240);
        trip.setStartedJourneyAt(1);
        trip.setStationStart("A");
        trip.setStationEnd("D");

        customerTrips.add(trip);
        List<CustomerOutput> customerListOutput = new ArrayList<CustomerOutput>();

        Whitebox.invokeMethod(jsonCustomerInput, "fillCustomerListOutput", 1, 240, customerTrips, customerListOutput);

        Assert.assertEquals(1, customerListOutput.size());
        Assert.assertEquals(1, ((CustomerOutputWithFullAttributes)customerListOutput.get(0)).getCostumerId());
        Assert.assertEquals(240, ((CustomerOutputWithFullAttributes)customerListOutput.get(0)).getTotalCostInCents());
        Assert.assertEquals(customerTrips, ((CustomerOutputWithFullAttributes)customerListOutput.get(0)).getTrips());
    }
}
