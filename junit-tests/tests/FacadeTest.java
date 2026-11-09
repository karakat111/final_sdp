package tests;

import facade.TravelBookingFacade;
import model.*;
import observer.*;
import strategy.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static tests.TestSupport.*;

public class FacadeTest {
    @Test
    @DisplayName("facade beach no extras")
    void test01() {
        Runnable check = () -> { BookingNotifier n = new BookingNotifier(); BookingCounterObserver c = new BookingCounterObserver(); n.subscribe(c); Trip t = new TravelBookingFacade(n).bookBeachTrip(new PriceCalculator()); near(1450, t.getTotalPrice()); eq(1, c.getBookingCount()); };
        check.run();
    }

    @Test
    @DisplayName("facade adventure no extras")
    void test02() {
        Runnable check = () -> near(2100, new TravelBookingFacade(new BookingNotifier()).bookAdventureTrip(new PriceCalculator()).getTotalPrice());
        check.run();
    }

    @Test
    @DisplayName("facade beach with both extras")
    void test03() {
        Runnable check = () -> near(1530, new TravelBookingFacade(new BookingNotifier()).bookBeachTrip(new PriceCalculator(), true, true).getTotalPrice());
        check.run();
    }

    @Test
    @DisplayName("facade adventure with both extras")
    void test04() {
        Runnable check = () -> near(2180, new TravelBookingFacade(new BookingNotifier()).bookAdventureTrip(new PriceCalculator(), true, true).getTotalPrice());
        check.run();
    }

    @Test
    @DisplayName("facade beach seasonal with both extras")
    void test05() {
        Runnable check = () -> near(1385, new TravelBookingFacade(new BookingNotifier()).bookBeachTrip(new PriceCalculator(new SeasonalDiscountStrategy(10)), true, true).getTotalPrice());
        check.run();
    }

    @Test
    @DisplayName("facade insurance only")
    void test06() {
        Runnable check = () -> near(1500, new TravelBookingFacade(new BookingNotifier()).bookBeachTrip(new PriceCalculator(), true, false).getTotalPrice());
        check.run();
    }

    @Test
    @DisplayName("facade luggage only")
    void test07() {
        Runnable check = () -> near(1480, new TravelBookingFacade(new BookingNotifier()).bookBeachTrip(new PriceCalculator(), false, true).getTotalPrice());
        check.run();
    }

    @Test
    @DisplayName("facade notifies with decorated trip")
    void test08() {
        Runnable check = () -> { BookingNotifier n = new BookingNotifier(); final Trip[] seen = {null}; n.subscribe(t -> seen[0] = t); Trip result = new TravelBookingFacade(n).bookBeachTrip(new PriceCalculator(), true, true); yes(seen[0] == result); };
        check.run();
    }

    @Test
    @DisplayName("facade two bookings notify twice")
    void test09() {
        Runnable check = () -> { BookingNotifier n = new BookingNotifier(); BookingCounterObserver c = new BookingCounterObserver(); n.subscribe(c); TravelBookingFacade f = new TravelBookingFacade(n); f.bookBeachTrip(new PriceCalculator()); f.bookAdventureTrip(new PriceCalculator()); eq(2, c.getBookingCount()); };
        check.run();
    }

    @Test
    @DisplayName("facade preserves destination")
    void test10() {
        Runnable check = () -> eq("Kyoto, Japan", new TravelBookingFacade(new BookingNotifier()).bookAdventureTrip(new PriceCalculator(), true, true).getDestination());
        check.run();
    }

}
