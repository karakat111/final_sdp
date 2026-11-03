package facade;

import factory.AdventurePackageFactory;
import factory.BeachPackageFactory;
import model.TravelPackage;
import observer.BookingNotifier;
import strategy.PriceCalculator;

public class TravelBookingFacade {
    private final BeachPackageFactory beachFactory = new BeachPackageFactory();
    private final AdventurePackageFactory adventureFactory = new AdventurePackageFactory();
    private final BookingNotifier bookingNotifier;

    public TravelBookingFacade(BookingNotifier bookingNotifier) {
        this.bookingNotifier = bookingNotifier;
    }

    public TravelPackage bookBeachTrip(PriceCalculator calculator) {
        TravelPackage travelPackage = beachFactory.createPackage(calculator);
        bookingNotifier.notifyObservers(travelPackage);
        return travelPackage;
    }

    public TravelPackage bookAdventureTrip(PriceCalculator calculator) {
        TravelPackage travelPackage = adventureFactory.createPackage(calculator);
        bookingNotifier.notifyObservers(travelPackage);
        return travelPackage;
    }
}
