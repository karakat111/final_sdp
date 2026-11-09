package tests;

import builder.*;
import model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static tests.TestSupport.*;

public class BuilderTest {
    @Test
    @DisplayName("builder creates object")
    void test01() {
        Runnable check = () -> { TravelPackageObjectBuilder b = new TravelPackageObjectBuilder(); TravelPackage t = b.setDestination("Rome").setNights(2).setHotel("Inn").addActivity("Tour").setTotalPrice(120).getResult(); eq("Rome", t.getDestination()); near(120, t.getTotalPrice()); };
        check.run();
    }

    @Test
    @DisplayName("builder rejects missing destination")
    void test02() {
        Runnable check = () -> throwsType(IllegalStateException.class, () -> new TravelPackageObjectBuilder().setNights(2).setHotel("Inn").getResult());
        check.run();
    }

    @Test
    @DisplayName("builder rejects zero nights")
    void test03() {
        Runnable check = () -> throwsType(IllegalStateException.class, () -> new TravelPackageObjectBuilder().setDestination("Rome").setNights(0).setHotel("Inn").getResult());
        check.run();
    }

    @Test
    @DisplayName("builder rejects missing hotel")
    void test04() {
        Runnable check = () -> throwsType(IllegalStateException.class, () -> new TravelPackageObjectBuilder().setDestination("Rome").setNights(2).getResult());
        check.run();
    }

    @Test
    @DisplayName("builder rejects negative price")
    void test05() {
        Runnable check = () -> throwsType(IllegalStateException.class, () -> new TravelPackageObjectBuilder().setDestination("Rome").setNights(2).setHotel("Inn").setTotalPrice(-1).getResult());
        check.run();
    }

    @Test
    @DisplayName("builder itinerary text")
    void test06() {
        Runnable check = () -> { ItineraryBuilder b = new ItineraryBuilder(); String text = b.setDestination("Rome").setNights(2).setHotel("Inn").addActivity("Tour").setTotalPrice(120).getResult(); yes(text.contains("Rome") && text.contains("Tour") && text.contains("120")); };
        check.run();
    }

    @Test
    @DisplayName("builder itinerary rejects missing price")
    void test07() {
        Runnable check = () -> throwsType(IllegalStateException.class, () -> new ItineraryBuilder().setDestination("Rome").setNights(2).setHotel("Inn").getResult());
        check.run();
    }

    @Test
    @DisplayName("builder itinerary rejects zero nights")
    void test08() {
        Runnable check = () -> throwsType(IllegalStateException.class, () -> new ItineraryBuilder().setDestination("Rome").setNights(0).setHotel("Inn").setTotalPrice(1).getResult());
        check.run();
    }

    @Test
    @DisplayName("builder director creates beach itinerary")
    void test09() {
        Runnable check = () -> { ItineraryBuilder b = new ItineraryBuilder(); new TravelDirector().makeBeachVacation(b); yes(b.getResult().contains("Nice, France")); };
        check.run();
    }

    @Test
    @DisplayName("builder director creates adventure object")
    void test10() {
        Runnable check = () -> { TravelPackageObjectBuilder b = new TravelPackageObjectBuilder(); new TravelDirector().makeAdventureTrip(b); eq("Kyoto, Japan", b.getResult().getDestination()); };
        check.run();
    }

}
