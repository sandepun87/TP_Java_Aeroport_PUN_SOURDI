import javax.json.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class JsonFlightFiller {
    private ArrayList<Flight> list = new ArrayList<>();

    public JsonFlightFiller(String jsonString, World w) {
        try {
            InputStream is = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));
            JsonReader rdr = Json.createReader(is);
            JsonObject obj = rdr.readObject();
            JsonArray results = obj.getJsonArray("data");

            if (results != null) {
                for (JsonObject result : results.getValuesAs(JsonObject.class)) {
                    try {
                        // Récupérer les informations du vol
                        String flightNumber = "";
                        if (result.containsKey("flight") && !result.isNull("flight")) {
                            JsonObject flight = result.getJsonObject("flight");
                            if (flight.containsKey("iata")) {
                                flightNumber = flight.getString("iata", "");
                            }
                        }

                        // Récupérer les aéroports de départ et d'arrivée
                        String departureIata = "";
                        String arrivalIata = "";
                        String aircraftType = "";

                        // Départ
                        if (result.containsKey("departure") && !result.isNull("departure")) {
                            JsonObject departure = result.getJsonObject("departure");
                            if (departure.containsKey("iata")) {
                                departureIata = departure.getString("iata", "");
                            }
                        }

                        // Arrivée
                        if (result.containsKey("arrival") && !result.isNull("arrival")) {
                            JsonObject arrival = result.getJsonObject("arrival");
                            if (arrival.containsKey("iata")) {
                                arrivalIata = arrival.getString("iata", "");
                            }
                        }

                        // Type d'avion
                        if (result.containsKey("aircraft") && !result.isNull("aircraft")) {
                            JsonObject aircraft = result.getJsonObject("aircraft");
                            if (aircraft.containsKey("iata")) {
                                aircraftType = aircraft.getString("iata", "");
                            }
                        }

                        // Chercher les aéroports dans la liste World
                        Aeroport depAirport = w.findByCode(departureIata);
                        Aeroport arrAirport = w.findByCode(arrivalIata);

                        // Créer le vol seulement si on trouve les deux aéroports
                        if (depAirport != null && arrAirport != null && !flightNumber.isEmpty()) {
                            Flight flight = new Flight(depAirport, arrAirport, aircraftType, flightNumber);
                            list.add(flight);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Flight> getList() {
        return list;
    }

    public static void main(String[] args) {
        try {
            World w = new World("./data/airport-codes_no_comma.csv");
            java.io.BufferedReader br = new java.io.BufferedReader(
                    new java.io.FileReader("data/test.txt"));
            String test = br.readLine();
            br.close();

            JsonFlightFiller jSonFlightFiller = new JsonFlightFiller(test, w);
            System.out.println("Nombre de vols trouvés: " + jSonFlightFiller.getList().size());
            for (Flight f : jSonFlightFiller.getList()) {
                System.out.println(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}