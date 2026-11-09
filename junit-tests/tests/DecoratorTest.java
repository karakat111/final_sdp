package tests;

import decorator.*;
import model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static tests.TestSupport.*;

public class DecoratorTest {
    @Test
    @DisplayName("decorator insurance 50")
    void test01() {
        Runnable check = () -> near(150, new TravelInsuranceDecorator(sample()).getTotalPrice());
        check.run();
    }

    @Test
    @DisplayName("decorator extra luggage 30")
    void test02() {
        Runnable check = () -> near(130, new ExtraLuggageDecorator(sample()).getTotalPrice());
        check.run();
    }

    @Test
    @DisplayName("decorator insurance 89")
    void test03() {
        Runnable check = () -> near(189, new InsuranceDecorator(sample()).getTotalPrice());
        check.run();
    }

    @Test
    @DisplayName("decorator luggage 45")
    void test04() {
        Runnable check = () -> near(145, new LuggageDecorator(sample()).getTotalPrice());
        check.run();
    }

    @Test
    @DisplayName("decorator transfer 60")
    void test05() {
        Runnable check = () -> near(160, new TransferDecorator(sample()).getTotalPrice());
        check.run();
    }

    @Test
    @DisplayName("decorator stacking")
    void test06() {
        Runnable check = () -> near(180, new ExtraLuggageDecorator(new TravelInsuranceDecorator(sample())).getTotalPrice());
        check.run();
    }

    @Test
    @DisplayName("decorator mixed stacking")
    void test07() {
        Runnable check = () -> near(294, new TransferDecorator(new LuggageDecorator(new InsuranceDecorator(sample()))).getTotalPrice());
        check.run();
    }

    @Test
    @DisplayName("decorator preserves destination")
    void test08() {
        Runnable check = () -> eq("Test City", new TransferDecorator(new InsuranceDecorator(sample())).getDestination());
        check.run();
    }

    @Test
    @DisplayName("decorator extends description")
    void test09() {
        Runnable check = () -> { String s = new ExtraLuggageDecorator(new TravelInsuranceDecorator(sample())).getDescription(); yes(s.contains("insurance") && s.contains("luggage")); };
        check.run();
    }

    @Test
    @DisplayName("decorator leaves original unchanged")
    void test10() {
        Runnable check = () -> { TravelPackage original = sample(); new TravelInsuranceDecorator(original); near(100, original.getTotalPrice()); };
        check.run();
    }

}
