package customerInformation;

/**
 * @author <a href="mailto:sabrinee.ayachi@gmail.com">sayachi</a>
 * @since 10 nov. 2019
 */
public class Trip {
    private String stationStart;

    private String stationEnd;

    private int startedJourneyAt;

    private int costInCents;

    private int zoneFrom;

    private int zoneTo;

    public void setStationEnd(String stationEnd) {

        this.stationEnd = stationEnd;
    }

    public void setStartedJourneyAt(int startedJourneyAt) {

        this.startedJourneyAt = startedJourneyAt;
    }

    public void setCostInCents(int costInCents) {

        this.costInCents = costInCents;
    }

    public void setZoneFrom(int zoneFrom) {
        this.zoneFrom = zoneFrom;
    }

    public void setZoneTo(int zoneTo) {

        this.zoneTo = zoneTo;
    }

    public void setStationStart(String stationStart) {

        this.stationStart = stationStart;
    }

    public String getStationStart() {
        return stationStart;
    }

    public String getStationEnd() {
        return stationEnd;
    }

    public int getStartedJourneyAt() {
        return startedJourneyAt;
    }

    public int getCostInCents() {
        return costInCents;
    }

    public int getZoneFrom() {
        return zoneFrom;
    }

    public int getZoneTo() {
        return zoneTo;
    }
}
