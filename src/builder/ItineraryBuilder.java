package builder;

public class ItineraryBuilder implements TravelPackageBuilder {

    private static final String HEADER = "=== Travel Itinerary ===";
    private static final String NO_ACTIVITIES = "  (no activities planned yet)";
    private static final int MIN_NIGHTS = 1;

    private String destination, hotel;
    private int nights;
    private final StringBuilder activityLines = new StringBuilder();
    private boolean hasActivity = false;
    private double totalPrice;
    private boolean priceSet = false;

    public ItineraryBuilder setDestination(String destination) { this.destination = destination; return this; }
    public ItineraryBuilder setNights(int nights) { this.nights = nights; return this; }
    public ItineraryBuilder setHotel(String hotel) { this.hotel = hotel; return this; }
    public ItineraryBuilder addActivity(String activity) {
        activityLines.append("  - ").append(activity).append(System.lineSeparator());
        hasActivity = true;
        return this;
    }
    public ItineraryBuilder setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; this.priceSet = true; return this; }

    public String getResult() {
        validate();
        return buildItineraryText();
    }

    private void validate() {
        if (destination == null || destination.isBlank())
            throw new IllegalStateException("Destination is required");
        if (nights < MIN_NIGHTS)
            throw new IllegalStateException("Nights must be at least " + MIN_NIGHTS);
        if (hotel == null || hotel.isBlank())
            throw new IllegalStateException("Hotel is required");
        if (!priceSet || totalPrice < 0)
            throw new IllegalStateException("A valid total price is required");
    }

    private String buildItineraryText() {
        StringBuilder sb = new StringBuilder();
        sb.append(HEADER).append(System.lineSeparator());
        sb.append("Destination: ").append(destination).append(System.lineSeparator());
        sb.append("Duration: ").append(nights).append(" nights").append(System.lineSeparator());
        sb.append("Hotel: ").append(hotel).append(System.lineSeparator());
        sb.append("Activities:").append(System.lineSeparator());
        sb.append(hasActivity ? activityLines : NO_ACTIVITIES + System.lineSeparator());
        sb.append("Total price: $").append(totalPrice).append(System.lineSeparator());
        return sb.toString();
    }
}
