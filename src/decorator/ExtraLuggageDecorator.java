package decorator;

/** Adds extra luggage to a trip for a fixed additional price. */
public final class ExtraLuggageDecorator extends TripDecorator {
    private static final double PRICE = 30.0;

    public ExtraLuggageDecorator(model.Trip trip) {
        super(trip);
    }

    @Override
    public double getTotalPrice() {
        return trip.getTotalPrice() + PRICE;
    }

    @Override
    public String getDescription() {
        return trip.getDescription() + "; extra luggage (+$" + PRICE + ")";
    }
}
