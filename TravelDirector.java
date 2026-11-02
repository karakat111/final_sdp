public class TravelDirector {

    public void makeBeachVacation(TravelPackageBuilder builder) {
        builder.setDestination("Nice, France")
                .setNights(7)
                .setHotel("Riviera Grand Hotel")
                .addActivity("Beach walk along the Promenade des Anglais")
                .addActivity("Sunset dinner cruise")
                .setTotalPrice(1450.0);
    }

    public void makeAdventureTrip(TravelPackageBuilder builder) {
        builder.setDestination("Kyoto, Japan")
                .setNights(5)
                .setHotel("Mountain View Ryokan")
                .addActivity("Mount Fuji hiking trail")
                .addActivity("Arashiyama bamboo forest trek")
                .addActivity("Kyoto temple bike tour")
                .setTotalPrice(2100.0);
    }
}
