package observer;

import java.util.ArrayList;
import java.util.List;
import model.TravelPackage;

public class BookingNotifier {
    private final List<BookingObserver> observers = new ArrayList<>();

    public void subscribe(BookingObserver observer) {
        observers.add(observer);
    }

    public void unsubscribe(BookingObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(TravelPackage travelPackage) {
        for (BookingObserver observer : observers) {
            observer.update(travelPackage);
        }
    }
}
