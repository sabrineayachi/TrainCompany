import org.json.simple.JSONArray;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:sabrinee.ayachi@gmail.com">sayachi</a>
 * @since 10 nov. 2019
 */
class JsonCustomerInput implements CustomerInput {
    private int  unixTimestamp;

    private int  customerId;

    private String station;

    static private List<Customer> customerList = new ArrayList<>();

    public JsonCustomerInput() {

    }

    public int getUnixTimestamp() {
        return unixTimestamp;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getStation() {
        return station;
    }

    public JsonCustomerInput(JSONObject data)  {
        unixTimestamp = ((Long)data.get("unixTimestamp")).intValue();
        customerId = ((Long)data.get("customerId")).intValue();
        station = (String) data.get("station");
    }

    /**
     * Returns the zone of the station
     *
     * @param station
     * @return zone
     */
    private static int getZone(String station) {
        int zone=0;

        switch(station) {
            case "A" : case "B" :
                zone = 1;
                break;
            case "C" : case "D" :
                zone = 2;
                break;
            case "E"  : case "F" :
                zone = 3;
                break;
            case "G"  : case "H" : case "I" :
                zone = 4;
                break;
        }

        return zone;
    }

    private static void parseTestData(JSONObject tap) {
        Customer customer = new Customer(new JsonCustomerInput(tap));
        customerList.add(customer);
    }

    /**
     * Returns the price per trip
     *
     * @param zoneFrom
     * @param zoneTo
     * @return price
     */
    private static int getPricingPerTrip(int zoneFrom, int zoneTo) {
        int pricingPerTrip=0;

        if(zoneFrom == 3 && (zoneTo == 1 || zoneTo ==2)) {
            pricingPerTrip=280;
        } else if (zoneFrom == 4 && (zoneTo == 1 || zoneTo ==2)){
            pricingPerTrip=300;
        } else if (zoneTo == 3 && (zoneFrom == 1 || zoneFrom ==2)){
            pricingPerTrip=280;
        } else if (zoneTo == 4 && (zoneFrom == 1 || zoneFrom ==2)){
            pricingPerTrip=300;
        } else if (zoneTo == 1 && zoneFrom==1 || zoneFrom==2 && zoneTo==2 || zoneTo == 1 && zoneFrom==2
                || zoneTo == 2 && zoneFrom==1) {
            pricingPerTrip = 240;
        } else if (zoneTo==4 && zoneFrom==4 || zoneFrom==3 && zoneTo==3 || zoneTo==4 && zoneFrom==3
                || zoneTo==3 && zoneFrom==4) {
            pricingPerTrip = 200;
        }

        return pricingPerTrip;
    }

    /**
     * Returns the customer output list
     *
     * @param customerId
     * @param totalCostInCents
     * @param customerTrips
     * @param customerListOutput
     */
    private static void fillCustomerListOutput(int customerId, int totalCostInCents, List<Trip> customerTrips, List<CustomerOutput> customerListOutput) {
        CustomerOutput customerOutput =  new CustomerOutputWithFullAttributes();

        ((CustomerOutputWithFullAttributes) customerOutput).setCostumerId((int) customerId);
        ((CustomerOutputWithFullAttributes) customerOutput).setTotalCostInCents(totalCostInCents);
        customerOutput.setTrips(customerTrips);

        customerListOutput.add(customerOutput);
    }

    /**
     * Populates the customer output list with all informations
     *
     * @param numberOfCustomerId
     * @param groupByIdCustomers
     * @param customerListOutput
     */
    private static void populateCustomerListOutput(int numberOfCustomerId, Map<Integer, List<Customer>> groupByIdCustomers, List<CustomerOutput> customerListOutput) {
        for(int i=1; i<=numberOfCustomerId; i++) {
            List<Trip> customerTrips = new ArrayList<>();
            Trip trip = new Trip();
            String stationEnd = "";
            String stationStart = "";
            int startedJourneyAt = 0;
            int totalCostInCents = 0;
            int tripSizeByCustomerId = groupByIdCustomers.get(i).size();

            for(int j=0; j<tripSizeByCustomerId; j++) {
                int customerId = groupByIdCustomers.get(i).get(j).getCustomerId();
                int unixTimestamp = groupByIdCustomers.get(i).get(j).getUnixTimestamp();
                String station = groupByIdCustomers.get(i).get(j).getStation();

                if(j%2!=0 && j!= 0) {
                    stationEnd = station;
                    trip.setStationStart(stationStart);
                    trip.setStationEnd(stationEnd);
                    trip.setZoneFrom(getZone(stationStart));
                    trip.setZoneTo(getZone(stationEnd));
                    trip.setStartedJourneyAt(startedJourneyAt);
                    trip.setCostInCents( getPricingPerTrip(getZone(stationStart), getZone(stationEnd)));
                    totalCostInCents = totalCostInCents + getPricingPerTrip(getZone(stationStart), getZone(stationEnd));
                    customerTrips.add(trip);

                    if(j == tripSizeByCustomerId-1) {
                        fillCustomerListOutput(customerId, totalCostInCents, customerTrips, customerListOutput);
                        customerTrips = new ArrayList<Trip>();
                    }

                    stationEnd = "";
                    stationStart = "";
                    startedJourneyAt = 0;
                    trip = new Trip();
                } else {
                    stationStart = station;
                    startedJourneyAt = groupByIdCustomers.get(i).get(j).getUnixTimestamp();
                }
            }
        }
    }

    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        List<CustomerOutput> customerListOutput = new ArrayList<>();

        try {
            /** get input json file **/
            File jsonFile = new File(args[0]);

            //File jsonFile = new File("C:\\Users\\A694009\\Documents\\TrainCompany\\src\\main\\java\\inputFile.json");
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(jsonFile));

            JSONArray name = (JSONArray) jsonObject.get("taps");

            for(Object object : name) {
                parseTestData((JSONObject) object);
            }

            /** group customer informations by id **/
            Map<Integer, List<Customer>> groupByIdCustomers =
                    customerList.stream().collect(Collectors.groupingBy(Customer::getCustomerId));

            int numberOfCustomerId = groupByIdCustomers.size();

            populateCustomerListOutput(numberOfCustomerId, groupByIdCustomers, customerListOutput);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*** output json file **/
        ObjectMapper mapper = new ObjectMapper();

        try {
            CustomerSummary customerSummary = new CustomerSummary();
            customerSummary.setCustomerSummaries(customerListOutput);

            // Java objects to JSON file
            //mapper.writeValue(new File("src\\main\\java\\outputFile.json"), customerSummary);
            mapper.writeValue(new File(args[1]), customerSummary);

            // Java objects to JSON string - compact-print
            String jsonString = mapper.writeValueAsString(customerSummary);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
