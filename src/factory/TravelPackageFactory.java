package factory;

import builder.TravelDirector;
import model.TravelPackage;
import strategy.PriceCalculator;

public abstract class TravelPackageFactory {

    protected final TravelDirector director = new TravelDirector();

    // Factory Method
    public abstract TravelPackage createPackage(
            PriceCalculator calculator
    );

    public TravelPackage createPackage() {
        return createPackage(new PriceCalculator());
    }

    protected void validateCalculator(PriceCalculator calculator) {
        if (calculator == null) {
            throw new IllegalArgumentException(
                    "Price calculator cannot be null"
            );
        }
    }
}