package tests;

import model.*;
import observer.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static tests.TestSupport.*;

public class ObserverTest {
    @Test
    @DisplayName("observer counts one booking")
    void test01() {
        Runnable check = () -> { BookingNotifier n = new BookingNotifier(); BookingCounterObserver c = new BookingCounterObserver(); n.subscribe(c); n.notifyObservers(sample()); eq(1, c.getBookingCount()); };
        check.run();
    }

    @Test
    @DisplayName("observer counts multiple bookings")
    void test02() {
        Runnable check = () -> { BookingNotifier n = new BookingNotifier(); BookingCounterObserver c = new BookingCounterObserver(); n.subscribe(c); n.notifyObservers(sample()); n.notifyObservers(sample()); eq(2, c.getBookingCount()); };
        check.run();
    }

    @Test
    @DisplayName("observer unsubscribes")
    void test03() {
        Runnable check = () -> { BookingNotifier n = new BookingNotifier(); BookingCounterObserver c = new BookingCounterObserver(); n.subscribe(c); n.notifyObservers(sample()); n.unsubscribe(c); n.notifyObservers(sample()); eq(1, c.getBookingCount()); };
        check.run();
    }

    @Test
    @DisplayName("observer supports multiple listeners")
    void test04() {
        Runnable check = () -> { BookingNotifier n = new BookingNotifier(); BookingCounterObserver a = new BookingCounterObserver(), b = new BookingCounterObserver(); n.subscribe(a); n.subscribe(b); n.notifyObservers(sample()); eq(1, a.getBookingCount()); eq(1, b.getBookingCount()); };
        check.run();
    }

    @Test
    @DisplayName("observer passes correct trip")
    void test05() {
        Runnable check = () -> { BookingNotifier n = new BookingNotifier(); final Trip[] received = {null}; n.subscribe(t -> received[0] = t); Trip trip = sample(); n.notifyObservers(trip); yes(received[0] == trip); };
        check.run();
    }

    @Test
    @DisplayName("observer with no subscribers")
    void test06() {
        Runnable check = () -> new BookingNotifier().notifyObservers(sample());
        check.run();
    }

}
