package observer;

import model.TravelPackage;

public class BookingCounterObserver implements BookingObserver {
    private int bookingCount;

    @Override
    public void update(TravelPackage travelPackage) {
        bookingCount++;
        System.out.println("Bookings received: " + bookingCount);
    }

    public int getBookingCount() {
        return bookingCount;
    }
}
