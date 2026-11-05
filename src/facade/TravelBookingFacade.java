package facade;

import decorator.ExtraLuggageDecorator;
import decorator.TravelInsuranceDecorator;
import factory.AdventurePackageFactory;
import factory.BeachPackageFactory;
import model.Trip;
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

    public Trip bookBeachTrip(PriceCalculator calculator) {
        return bookBeachTrip(calculator, false, false);
    }

    public Trip bookBeachTrip(PriceCalculator calculator, boolean addInsurance, boolean addExtraLuggage) {
        Trip trip = addServices(beachFactory.createPackage(calculator), addInsurance, addExtraLuggage);
        bookingNotifier.notifyObservers(trip);
        return trip;
    }

    public Trip bookAdventureTrip(PriceCalculator calculator) {
        return bookAdventureTrip(calculator, false, false);
    }

    public Trip bookAdventureTrip(PriceCalculator calculator, boolean addInsurance, boolean addExtraLuggage) {
        Trip trip = addServices(adventureFactory.createPackage(calculator), addInsurance, addExtraLuggage);
        bookingNotifier.notifyObservers(trip);
        return trip;
    }

    private Trip addServices(TravelPackage packageTrip, boolean addInsurance, boolean addExtraLuggage) {
        Trip trip = packageTrip;
        if (addInsurance) {
            trip = new TravelInsuranceDecorator(trip);
        }
        if (addExtraLuggage) {
            trip = new ExtraLuggageDecorator(trip);
        }
        return trip;
    }
}
