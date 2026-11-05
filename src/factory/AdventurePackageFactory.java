package factory;

import builder.TravelPackageObjectBuilder;
import model.TravelPackage;
import strategy.PriceCalculator;

public class AdventurePackageFactory extends TravelPackageFactory {

    @Override
    public TravelPackage createPackage(PriceCalculator calculator) {
        validateCalculator(calculator);

        TravelPackageObjectBuilder builder =
                new TravelPackageObjectBuilder();

        director.makeAdventureTrip(builder, calculator);

        return builder.getResult();
    }
}