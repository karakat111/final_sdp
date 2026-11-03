package strategy;

/** Peak-season / last-minute booking: +20% on top of the base price. */
public class LastMinuteSurchargeStrategy implements PricingStrategy {

    private static final double SURCHARGE = 0.20;

    @Override
    public double calculatePrice(double basePrice, int nights) {
        return basePrice * (1 + SURCHARGE);
    }

    @Override
    public String getName() { return "Last-minute surcharge (+20%)"; }
}
