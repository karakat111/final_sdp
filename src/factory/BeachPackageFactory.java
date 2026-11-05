package factory;

import builder.TravelPackageObjectBuilder;
import model.TravelPackage;
import strategy.PriceCalculator;

public class BeachPackageFactory extends TravelPackageFactory {

    @Override
    public TravelPackage createPackage(PriceCalculator calculator) {
        validateCalculator(calculator);

        TravelPackageObjectBuilder builder =
                new TravelPackageObjectBuilder();

        director.makeBeachVacation(builder, calculator);

        return builder.getResult();
    }
}