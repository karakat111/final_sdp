package observer;

import java.util.ArrayList;
import java.util.List;
import model.Trip;

public class BookingNotifier {
    private final List<BookingObserver> observers = new ArrayList<>();

    public void subscribe(BookingObserver observer) {
        observers.add(observer);
    }

    public void unsubscribe(BookingObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Trip trip) {
        for (BookingObserver observer : observers) {
            observer.update(trip);
        }
    }
}

