package observer;

import model.TravelPackage;

public class BookingConfirmationObserver implements BookingObserver {
    @Override
    public void update(TravelPackage travelPackage) {
        System.out.println("Booking confirmed: " + travelPackage.getDestination()
                + ", total price: $" + travelPackage.getTotalPrice());
    }
}
