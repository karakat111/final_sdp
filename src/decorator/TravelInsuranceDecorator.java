package decorator;

/** Adds travel insurance to a trip for a fixed additional price. */
public final class TravelInsuranceDecorator extends TripDecorator {
    private static final double PRICE = 50.0;

    public TravelInsuranceDecorator(model.Trip trip) {
        super(trip);
    }

    @Override
    public double getTotalPrice() {
        return trip.getTotalPrice() + PRICE;
    }

    @Override
    public String getDescription() {
        return trip.getDescription() + "; travel insurance (+$" + PRICE + ")";
    }
}
