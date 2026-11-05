package src.decorator;

import model.Trip;

/** Add-on: travel insurance (+$89). */
public class InsuranceDecorator extends TripDecorator {

    private static final double PRICE = 89.0;

    public InsuranceDecorator(Trip wrapped) {
        super(wrapped);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Travel insurance";
    }

    @Override
    public double getTotalPrice() {
        return round(super.getTotalPrice() + PRICE);
    }
}
