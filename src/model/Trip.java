package model;

/** A trip that can be extended with optional services. */
public interface Trip {
    String getDestination();
    double getTotalPrice();
    String getDescription();
}
