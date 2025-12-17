public class Aeroport {
    private String name;
    private String iata;
    private String country;
    private double latitude;
    private double longitude;

    public Aeroport(String iata, String name, String country, double latitude, double longitude) {
        this.iata = iata;
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getIATA() {
        return iata;
    }

    public String getCountry() {
        return country;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Aeroport{" +
                "name='" + name + '\'' +
                ", iata='" + iata + '\'' +
                ", country='" + country + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}