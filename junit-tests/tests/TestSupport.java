package tests;

import model.TravelPackage;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Shared assertions and fixtures preserved from the original AllTests.java. */
final class TestSupport {
    private TestSupport() {}
    static void eq(Object expected, Object actual) { assertEquals(expected, actual); }
    static void near(double expected, double actual) {
        assertTrue(Double.isFinite(actual), "Result must be finite");
        assertEquals(expected, actual, 0.000001);
    }
    static void yes(boolean condition) { assertTrue(condition); }
    static void throwsType(Class<? extends Throwable> expected, Runnable action) {
        assertThrows(expected, action::run);
    }
    static TravelPackage sample() {
        return new TravelPackage("Test City", 3, "Test Hotel", List.of("Museum"), 100.0);
    }
}
