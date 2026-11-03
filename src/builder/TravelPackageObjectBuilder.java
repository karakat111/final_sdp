package builder;

import model.TravelPackage;

import java.util.ArrayList;
import java.util.List;

public class TravelPackageObjectBuilder implements TravelPackageBuilder {

    private static final int MIN_NIGHTS = 1;

    private String destination, hotel;
    private int nights;
    private final List<String> activities = new ArrayList<>();
    private double totalPrice;

    public TravelPackageObjectBuilder setDestination(String destination) { this.destination = destination; return this; }
    public TravelPackageObjectBuilder setNights(int nights) { this.nights = nights; return this; }
    public TravelPackageObjectBuilder setHotel(String hotel) { this.hotel = hotel; return this; }
    public TravelPackageObjectBuilder addActivity(String activity) { activities.add(activity); return this; }
    public TravelPackageObjectBuilder setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; return this; }

    public TravelPackage getResult() {
        validate();
        return new TravelPackage(destination, nights, hotel, activities, totalPrice);
    }

    private void validate() {
        if (destination == null || destination.isBlank())
            throw new IllegalStateException("Destination is required");
        if (nights < MIN_NIGHTS)
            throw new IllegalStateException("Nights must be at least " + MIN_NIGHTS);
        if (hotel == null || hotel.isBlank())
            throw new IllegalStateException("Hotel is required");
        if (totalPrice < 0)
            throw new IllegalStateException("Total price cannot be negative");
    }
}
