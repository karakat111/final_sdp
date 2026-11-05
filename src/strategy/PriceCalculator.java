package strategy;

public class PriceCalculator {

    private PricingStrategy strategy;

    public PriceCalculator() {
        this(new RegularPricingStrategy());
    }

    public PriceCalculator(PricingStrategy strategy) {
        setStrategy(strategy);
    }

    public void setStrategy(PricingStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException(
                    "Pricing strategy cannot be null"
            );
        }
        this.strategy = strategy;
    }

    public PricingStrategy getStrategy() {
        return strategy;
    }

    public double calculate(double basePrice, int nights) {
        if (!Double.isFinite(basePrice) || basePrice < 0) {
            throw new IllegalArgumentException(
                    "Base price must be a valid non-negative number"
            );
        }

        if (nights <= 0) {
            throw new IllegalArgumentException(
                    "Number of nights must be positive"
            );
        }

        double finalPrice =
                strategy.calculatePrice(basePrice, nights);

        if (!Double.isFinite(finalPrice) || finalPrice < 0) {
            throw new IllegalStateException(
                    "Strategy returned an invalid price"
            );
        }

        return Math.round(finalPrice * 100.0) / 100.0;
    }
}