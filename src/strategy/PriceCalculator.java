package strategy;

/** Strategy context: holds the current PricingStrategy and can swap it at runtime. */
public class PriceCalculator {

    private PricingStrategy strategy;

    public PriceCalculator() {
        this(new RegularPricingStrategy());
    }

    public PriceCalculator(PricingStrategy strategy) {
        setStrategy(strategy);
    }

    public void setStrategy(PricingStrategy strategy) {
        if (strategy == null) throw new IllegalArgumentException("Strategy is required");
        this.strategy = strategy;
    }

    public PricingStrategy getStrategy() { return strategy; }

    public double calculate(double basePrice, int nights) {
        return Math.round(strategy.calculatePrice(basePrice, nights) * 100.0) / 100.0;
    }
}
