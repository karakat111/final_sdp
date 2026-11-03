package strategy;

public class RegularPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(double basePrice, int nights) {
        return basePrice;
    }

    @Override
    public String getName() { return "Regular (no discount)"; }
}
