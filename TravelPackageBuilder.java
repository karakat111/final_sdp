public interface TravelPackageBuilder {
    TravelPackageBuilder setDestination(String destination);
    TravelPackageBuilder setNights(int nights);
    TravelPackageBuilder setHotel(String hotel);
    TravelPackageBuilder addActivity(String activity);
    TravelPackageBuilder setTotalPrice(double totalPrice);
}
