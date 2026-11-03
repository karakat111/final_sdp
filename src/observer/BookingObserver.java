package observer;

import model.TravelPackage;

public interface BookingObserver {
    void update(TravelPackage travelPackage);
}
