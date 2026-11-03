package app;

import builder.ItineraryBuilder;
import builder.TravelDirector;
import builder.TravelPackageObjectBuilder;
import facade.TravelBookingFacade;
import model.TravelPackage;
import factory.AdventurePackageFactory;
import factory.BeachPackageFactory;
import factory.TravelPackageFactory;
import observer.BookingConfirmationObserver;
import observer.BookingCounterObserver;
import observer.BookingNotifier;
import strategy.LastMinuteSurchargeStrategy;
import strategy.LongStayDiscountStrategy;
import strategy.PriceCalculator;
import strategy.PricingStrategy;
import strategy.RegularPricingStrategy;
import strategy.SeasonalDiscountStrategy;

public class Main {
    public static void main(String[] args) {
        // --- Builder (existing) ---
        TravelDirector director = new TravelDirector();

        TravelPackageObjectBuilder objectBuilder = new TravelPackageObjectBuilder();
        director.makeBeachVacation(objectBuilder);
        System.out.println(objectBuilder.getResult());

        ItineraryBuilder itineraryBuilder = new ItineraryBuilder();
        director.makeBeachVacation(itineraryBuilder);
        System.out.println(itineraryBuilder.getResult());

        // --- Factory Method: creators decide which package is made ---
        System.out.println("--- Factory Method ---");
        TravelPackageFactory[] factories = { new BeachPackageFactory(), new AdventurePackageFactory() };
        for (TravelPackageFactory factory : factories) {
            System.out.println(factory.createPackage());
        }

        // --- Strategy: same package, different pricing algorithms ---
        System.out.println("--- Strategy ---");
        PricingStrategy[] strategies = {
                new RegularPricingStrategy(),
                new SeasonalDiscountStrategy(15),
                new LongStayDiscountStrategy(),
                new LastMinuteSurchargeStrategy()
        };
        PriceCalculator calculator = new PriceCalculator();
        TravelPackageFactory beach = new BeachPackageFactory();
        TravelPackageFactory adventure = new AdventurePackageFactory();
        for (PricingStrategy strategy : strategies) {
            calculator.setStrategy(strategy); // swapped at runtime
            System.out.println(strategy.getName());
            System.out.println("  Beach:     $" + beach.createPackage(calculator).getTotalPrice());
            System.out.println("  Adventure: $" + adventure.createPackage(calculator).getTotalPrice());
        }

        // --- Facade + Observer: one call books a trip and notifies listeners ---
        System.out.println("--- Facade + Observer ---");
        BookingNotifier notifier = new BookingNotifier();
        BookingConfirmationObserver confirmation = new BookingConfirmationObserver();
        BookingCounterObserver counter = new BookingCounterObserver();
        notifier.subscribe(confirmation);
        notifier.subscribe(counter);

        TravelBookingFacade bookingFacade = new TravelBookingFacade(notifier);
        TravelPackage bookedTrip = bookingFacade.bookBeachTrip(
                new PriceCalculator(new SeasonalDiscountStrategy(10)));
        System.out.println("Package returned to client: " + bookedTrip);
    }
}

