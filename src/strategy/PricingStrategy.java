package strategy;

/** Strategy: an interchangeable algorithm for turning a base price into a final price. */
public interface PricingStrategy {
    double calculatePrice(double basePrice, int nights);
    String getName();
}
