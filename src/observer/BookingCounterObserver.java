package observer;

import model.Trip;

public class BookingCounterObserver implements BookingObserver {
    private int bookingCount;

    @Override
    public void update(Trip trip) {
        bookingCount++;
        System.out.println("Bookings received: " + bookingCount);
    }

    public int getBookingCount() {
        return bookingCount;
    }
}

