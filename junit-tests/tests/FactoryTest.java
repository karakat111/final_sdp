package tests;

import factory.*;
import model.*;
import strategy.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static tests.TestSupport.*;

public class FactoryTest {
    @Test
    @DisplayName("factory beach destination and hotel")
    void test01() {
        Runnable check = () -> { TravelPackage t = new BeachPackageFactory().createPackage(); eq("Nice, France", t.getDestination()); eq("Riviera Grand Hotel", t.getHotel()); };
        check.run();
    }

    @Test
    @DisplayName("factory beach nights and activities")
    void test02() {
        Runnable check = () -> { TravelPackage t = new BeachPackageFactory().createPackage(); eq(7, t.getNights()); eq(2, t.getActivities().size()); };
        check.run();
    }

    @Test
    @DisplayName("factory beach default price")
    void test03() {
        Runnable check = () -> near(1450, new BeachPackageFactory().createPackage().getTotalPrice());
        check.run();
    }

    @Test
    @DisplayName("factory adventure destination and hotel")
    void test04() {
        Runnable check = () -> { TravelPackage t = new AdventurePackageFactory().createPackage(); eq("Kyoto, Japan", t.getDestination()); eq("Mountain View Ryokan", t.getHotel()); };
        check.run();
    }

    @Test
    @DisplayName("factory adventure nights and activities")
    void test05() {
        Runnable check = () -> { TravelPackage t = new AdventurePackageFactory().createPackage(); eq(5, t.getNights()); eq(3, t.getActivities().size()); };
        check.run();
    }

    @Test
    @DisplayName("factory adventure default price")
    void test06() {
        Runnable check = () -> near(2100, new AdventurePackageFactory().createPackage().getTotalPrice());
        check.run();
    }

    @Test
    @DisplayName("factory beach seasonal integration")
    void test07() {
        Runnable check = () -> near(1232.5, new BeachPackageFactory().createPackage(new PriceCalculator(new SeasonalDiscountStrategy(15))).getTotalPrice());
        check.run();
    }

    @Test
    @DisplayName("factory adventure long stay not eligible")
    void test08() {
        Runnable check = () -> near(2100, new AdventurePackageFactory().createPackage(new PriceCalculator(new LongStayDiscountStrategy())).getTotalPrice());
        check.run();
    }

    @Test
    @DisplayName("factory beach rejects null calculator")
    void test09() {
        Runnable check = () -> throwsType(IllegalArgumentException.class, () -> new BeachPackageFactory().createPackage(null));
        check.run();
    }

    @Test
    @DisplayName("factory adventure rejects null calculator")
    void test10() {
        Runnable check = () -> throwsType(IllegalArgumentException.class, () -> new AdventurePackageFactory().createPackage(null));
        check.run();
    }

    @Test
    @DisplayName("factory returns distinct objects")
    void test11() {
        Runnable check = () -> { BeachPackageFactory f = new BeachPackageFactory(); yes(f.createPackage() != f.createPackage()); };
        check.run();
    }

}
