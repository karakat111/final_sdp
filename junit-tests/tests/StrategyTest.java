package tests;

import strategy.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static tests.TestSupport.*;

public class StrategyTest {
    @Test
    @DisplayName("strategy regular")
    void test01() {
        Runnable check = () -> near(1450, new PriceCalculator().calculate(1450, 7));
        check.run();
    }

    @Test
    @DisplayName("strategy seasonal 15%")
    void test02() {
        Runnable check = () -> near(1232.50, new PriceCalculator(new SeasonalDiscountStrategy(15)).calculate(1450, 7));
        check.run();
    }

    @Test
    @DisplayName("strategy seasonal 0%")
    void test03() {
        Runnable check = () -> near(1450, new PriceCalculator(new SeasonalDiscountStrategy(0)).calculate(1450, 7));
        check.run();
    }

    @Test
    @DisplayName("strategy seasonal 100%")
    void test04() {
        Runnable check = () -> near(0, new PriceCalculator(new SeasonalDiscountStrategy(100)).calculate(1450, 7));
        check.run();
    }

    @Test
    @DisplayName("strategy long stay 6 nights")
    void test05() {
        Runnable check = () -> near(1450, new PriceCalculator(new LongStayDiscountStrategy()).calculate(1450, 6));
        check.run();
    }

    @Test
    @DisplayName("strategy long stay 7 nights")
    void test06() {
        Runnable check = () -> near(1305, new PriceCalculator(new LongStayDiscountStrategy()).calculate(1450, 7));
        check.run();
    }

    @Test
    @DisplayName("strategy long stay 8 nights")
    void test07() {
        Runnable check = () -> near(1305, new PriceCalculator(new LongStayDiscountStrategy()).calculate(1450, 8));
        check.run();
    }

    @Test
    @DisplayName("strategy last-minute")
    void test08() {
        Runnable check = () -> near(1740, new PriceCalculator(new LastMinuteSurchargeStrategy()).calculate(1450, 7));
        check.run();
    }

    @Test
    @DisplayName("strategy switching at runtime")
    void test09() {
        Runnable check = () -> { PriceCalculator p = new PriceCalculator(); p.setStrategy(new LastMinuteSurchargeStrategy()); near(120, p.calculate(100, 1)); p.setStrategy(new RegularPricingStrategy()); near(100, p.calculate(100, 1)); };
        check.run();
    }

    @Test
    @DisplayName("strategy rounds cents")
    void test10() {
        Runnable check = () -> near(0.67, new PriceCalculator(new SeasonalDiscountStrategy(33)).calculate(1, 1));
        check.run();
    }

    @Test
    @DisplayName("strategy zero base price")
    void test11() {
        Runnable check = () -> near(0, new PriceCalculator().calculate(0, 1));
        check.run();
    }

    @Test
    @DisplayName("strategy rejects null")
    void test12() {
        Runnable check = () -> throwsType(IllegalArgumentException.class, () -> new PriceCalculator(null));
        check.run();
    }

    @Test
    @DisplayName("strategy rejects null setter")
    void test13() {
        Runnable check = () -> throwsType(IllegalArgumentException.class, () -> new PriceCalculator().setStrategy(null));
        check.run();
    }

    @Test
    @DisplayName("strategy rejects negative price")
    void test14() {
        Runnable check = () -> throwsType(IllegalArgumentException.class, () -> new PriceCalculator().calculate(-1, 2));
        check.run();
    }

    @Test
    @DisplayName("strategy rejects NaN price")
    void test15() {
        Runnable check = () -> throwsType(IllegalArgumentException.class, () -> new PriceCalculator().calculate(Double.NaN, 2));
        check.run();
    }

    @Test
    @DisplayName("strategy rejects infinite price")
    void test16() {
        Runnable check = () -> throwsType(IllegalArgumentException.class, () -> new PriceCalculator().calculate(Double.POSITIVE_INFINITY, 2));
        check.run();
    }

    @Test
    @DisplayName("strategy rejects zero nights")
    void test17() {
        Runnable check = () -> throwsType(IllegalArgumentException.class, () -> new PriceCalculator().calculate(100, 0));
        check.run();
    }

    @Test
    @DisplayName("strategy rejects negative nights")
    void test18() {
        Runnable check = () -> throwsType(IllegalArgumentException.class, () -> new PriceCalculator().calculate(100, -1));
        check.run();
    }

    @Test
    @DisplayName("strategy rejects discount below 0")
    void test19() {
        Runnable check = () -> throwsType(IllegalArgumentException.class, () -> new SeasonalDiscountStrategy(-1));
        check.run();
    }

    @Test
    @DisplayName("strategy rejects discount above 100")
    void test20() {
        Runnable check = () -> throwsType(IllegalArgumentException.class, () -> new SeasonalDiscountStrategy(101));
        check.run();
    }

    @Test
    @DisplayName("strategy rejects NaN discount")
    void test21() {
        Runnable check = () -> throwsType(IllegalArgumentException.class, () -> new SeasonalDiscountStrategy(Double.NaN));
        check.run();
    }

    @Test
    @DisplayName("strategy rejects invalid strategy result")
    void test22() {
        Runnable check = () -> throwsType(IllegalStateException.class, () -> new PriceCalculator(new PricingStrategy() { public double calculatePrice(double p, int n) { return -5; } public String getName() { return "bad"; } }).calculate(100, 1));
        check.run();
    }

}
