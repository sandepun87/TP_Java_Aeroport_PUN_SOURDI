public class Aeroport {
    private String ident;
    private String type;
    private String name;
    private int elevation;
    private String continent;
    private String country;
    private String region;
    private String municipality;
    private String gpsCode;
    private String iataCode;
    private String localCode;
    private double longitude;
    private double latitude;

    public Aeroport(String ident, String type, String name, int elevation,
                    String continent, String country, String region, String municipality,
                    String gpsCode, String iataCode, String localCode,
                    double longitude, double latitude) {
        this.ident = ident;
        this.type = type;
        this.name = name;
        this.elevation = elevation;
        this.continent = continent;
        this.country = country;
        this.region = region;
        this.municipality = municipality;
        this.gpsCode = gpsCode;
        this.iataCode = iataCode;
        this.localCode = localCode;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    // Getters
    public String getIdent() { return ident; }
    public String getType() { return type; }
    public String getName() { return name; }
    public int getElevation() { return elevation; }
    public String getContinent() { return continent; }
    public String getCountry() { return country; }
    public String getRegion() { return region; }
    public String getMunicipality() { return municipality; }
    public String getGpsCode() { return gpsCode; }
    public String getIataCode() { return iataCode; }
    public String getLocalCode() { return localCode; }
    public double getLongitude() { return longitude; }
    public double getLatitude() { return latitude; }

    @Override
    public String toString() {
        return name + " (" + iataCode + ") - " + municipality + ", " + country;
    }
}