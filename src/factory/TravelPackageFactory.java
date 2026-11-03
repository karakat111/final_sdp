package factory;

import builder.TravelDirector;
import model.TravelPackage;
import strategy.PriceCalculator;

/** Factory Method: creator. Subclasses decide which kind of TravelPackage is created. */
public abstract class TravelPackageFactory {

    protected final TravelDirector director = new TravelDirector();

    public abstract TravelPackage createPackage(PriceCalculator calculator);

    /** Convenience overload: regular pricing. */
    public TravelPackage createPackage() {
        return createPackage(new PriceCalculator());
    }
}
