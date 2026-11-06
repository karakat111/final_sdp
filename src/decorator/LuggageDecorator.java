package decorator;

import model.Trip;

/** Add-on: extra checked luggage (+$45). */
public class LuggageDecorator extends TripDecorator {

    private static final double PRICE = 45.0;

    public LuggageDecorator(Trip wrapped) {
        super(wrapped);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Extra luggage";
    }

    @Override
    public double getTotalPrice() {
        return Math.round((super.getTotalPrice() + PRICE) * 100.0) / 100.0;
    }
}
