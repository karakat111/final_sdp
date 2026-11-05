package decorator;

import model.Trip;

/** Base decorator that forwards the basic trip information. */
public abstract class TripDecorator implements Trip {
    protected final Trip trip;

    protected TripDecorator(Trip trip) {
        this.trip = trip;
    }

    @Override
    public String getDestination() {
        return trip.getDestination();
    }

    @Override
    public double getTotalPrice() {
        return trip.getTotalPrice();
    }

    @Override
    public String getDescription() {
        return trip.getDescription();
    }
}
