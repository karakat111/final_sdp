package observer;

import model.Trip;

public class BookingConfirmationObserver implements BookingObserver {
    @Override
    public void update(Trip trip) {
        System.out.println("Booking confirmed: " + trip.getDescription()
                + ", total price: $" + trip.getTotalPrice());
    }
}
