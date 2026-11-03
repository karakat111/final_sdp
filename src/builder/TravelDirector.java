package builder;

import strategy.PriceCalculator;

public class TravelDirector {

    private static final double BEACH_BASE_PRICE = 1450.0;
    private static final double ADVENTURE_BASE_PRICE = 2100.0;

    public void makeBeachVacation(TravelPackageBuilder builder) {
        makeBeachVacation(builder, new PriceCalculator());
    }

    public void makeBeachVacation(TravelPackageBuilder builder, PriceCalculator calculator) {
        int nights = 7;
        builder.setDestination("Nice, France")
                .setNights(nights)
                .setHotel("Riviera Grand Hotel")
                .addActivity("Beach walk along the Promenade des Anglais")
                .addActivity("Sunset dinner cruise")
                .setTotalPrice(calculator.calculate(BEACH_BASE_PRICE, nights));
    }

    public void makeAdventureTrip(TravelPackageBuilder builder) {
        makeAdventureTrip(builder, new PriceCalculator());
    }

    public void makeAdventureTrip(TravelPackageBuilder builder, PriceCalculator calculator) {
        int nights = 5;
        builder.setDestination("Kyoto, Japan")
                .setNights(nights)
                .setHotel("Mountain View Ryokan")
                .addActivity("Mount Fuji hiking trail")
                .addActivity("Arashiyama bamboo forest trek")
                .addActivity("Kyoto temple bike tour")
                .setTotalPrice(calculator.calculate(ADVENTURE_BASE_PRICE, nights));
    }
}
