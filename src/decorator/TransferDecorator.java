package decorator;

import model.Trip;

/** Add-on: airport transfer (+$60). */
public class TransferDecorator extends TripDecorator {

    private static final double PRICE = 60.0;

    public TransferDecorator(Trip wrapped) {
        super(wrapped);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Airport transfer";
    }

    @Override
    public double getTotalPrice() {
        return Math.round((super.getTotalPrice() + PRICE) * 100.0) / 100.0;
    }
}
