package strategy;

public class SeasonalDiscountStrategy implements PricingStrategy {

    private final double percent;

    public SeasonalDiscountStrategy(double percent) {
        if (percent < 0 || percent > 100)
            throw new IllegalArgumentException("Percent must be between 0 and 100");
        this.percent = percent;
    }

    @Override
    public double calculatePrice(double basePrice, int nights) {
        return basePrice * (1 - percent / 100.0);
    }

    @Override
    public String getName() { return "Seasonal discount (" + percent + "%)"; }
}
