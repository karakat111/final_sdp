import java.util.Collections;
import java.util.List;

public final class TravelPackage {

    private final String destination, hotel;
    private final int nights;
    private final List<String> activities;
    private final double totalPrice;

    TravelPackage(String destination, int nights, String hotel, List<String> activities, double totalPrice) {
        this.destination = destination;
        this.nights = nights;
        this.hotel = hotel;
        this.activities = Collections.unmodifiableList(activities);
        this.totalPrice = totalPrice;
    }

    public String getDestination() { return destination; }
    public int getNights() { return nights; }
    public String getHotel() { return hotel; }
    public List<String> getActivities() { return activities; }
    public double getTotalPrice() { return totalPrice; }

    @Override
    public String toString() {
        return "TravelPackage[" + destination + ", " + nights + " nights, " + hotel + ", " + activities + ", $" + totalPrice + "]";
    }
}
