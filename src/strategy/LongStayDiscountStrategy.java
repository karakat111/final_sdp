package strategy;

/** 10% off from 7 nights, otherwise the regular price. */
public class LongStayDiscountStrategy implements PricingStrategy {

    private static final int MIN_NIGHTS = 7;
    private static final double DISCOUNT = 0.10;

    @Override
    public double calculatePrice(double basePrice, int nights) {
        return nights >= MIN_NIGHTS ? basePrice * (1 - DISCOUNT) : basePrice;
    }

    @Override
    public String getName() { return "Long stay discount (10% from " + MIN_NIGHTS + " nights)"; }
}
