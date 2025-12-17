public class Flight {
    private Aeroport departure;
    private Aeroport arrival;
    private String aircraftType;
    private String flightNumber;

    public Flight(Aeroport departure, Aeroport arrival, String aircraftType, String flightNumber) {
        this.departure = departure;
        this.arrival = arrival;
        this.aircraftType = aircraftType;
        this.flightNumber = flightNumber;
    }

    public Aeroport getDeparture() {
        return departure;
    }

    public Aeroport getArrival() {
        return arrival;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightNumber='" + flightNumber + '\'' +
                ", departure=" + (departure != null ? departure.getIATA() : "null") +
                ", arrival=" + (arrival != null ? arrival.getIATA() : "null") +
                ", aircraftType='" + aircraftType + '\'' +
                '}';
    }
}



