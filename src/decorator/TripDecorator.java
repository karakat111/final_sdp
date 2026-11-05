package src.decorator;

import model.Trip;

/** Decorator base: wraps a Trip and delegates to it; subclasses add an extra service. */
public abstract class TripDecorator implements Trip {

    protected final Trip wrapped;

    protected TripDecorator(Trip wrapped) {
        if (wrapped == null) throw new IllegalArgumentException("Trip is required");
        this.wrapped = wrapped;
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription();
    }

    @Override
    public double getTotalPrice() {
        return wrapped.getTotalPrice();
    }

    protected static double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
