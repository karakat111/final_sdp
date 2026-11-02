public class Main {
    public static void main(String[] args) {
        TravelDirector director = new TravelDirector();

        TravelPackageObjectBuilder objectBuilder = new TravelPackageObjectBuilder();
        director.makeBeachVacation(objectBuilder);
        TravelPackage beachTrip = objectBuilder.getResult();
        System.out.println(beachTrip);

        ItineraryBuilder itineraryBuilder = new ItineraryBuilder();
        director.makeBeachVacation(itineraryBuilder);
        String itineraryText = itineraryBuilder.getResult();
        System.out.println(itineraryText);
    }
}
