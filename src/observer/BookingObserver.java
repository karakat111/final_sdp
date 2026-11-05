package observer;

import model.Trip;

public interface BookingObserver {
    void update(Trip trip);
}

