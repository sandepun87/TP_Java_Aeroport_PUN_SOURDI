import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class World {
    private ArrayList<Aeroport> list;

    public World(String filePath) {
        this.list = new ArrayList<>();
        loadAirportsFromFile(filePath);
    }

    private void loadAirportsFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = parseCSVLine(line);

                if (parts.length >= 12) {
                    try {
                        String type = parts[1].trim();

                        if (type.equals("large_airport")) {
                            String name = parts[2].trim();
                            String iata = parts[9].trim();
                            String coordonnees = parts[11].trim();
                            String country = parts[5].trim();

                            if (!iata.isEmpty() && !iata.equals("IATA")) {
                                String[] coords = coordonnees.split(",");
                                if (coords.length >= 2) {
                                    double longitude = Double.parseDouble(coords[0].trim());
                                    double latitude = Double.parseDouble(coords[1].trim());

                                    Aeroport aeroport = new Aeroport(iata, name, country, latitude, longitude);
                                    list.add(aeroport);
                                }
                            }
                        }
                    } catch (NumberFormatException e) {
                        // Ignorer les lignes mal formées
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }

        System.out.println("Aéroports chargés: " + list.size());
    }

    private static String[] parseCSVLine(String line) {
        ArrayList<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        result.add(current.toString());

        return result.toArray(new String[0]);
    }

    public Aeroport findByCode(String code) {
        for (Aeroport aeroport : list) {
            if (aeroport.getIATA().equalsIgnoreCase(code)) {
                return aeroport;
            }
        }
        return null;
    }

    public Aeroport findNearestAirport(double longitude, double latitude) {
        if (list.isEmpty()) {
            return null;
        }

        Aeroport nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Aeroport aeroport : list) {
            double dist = distance(longitude, latitude, aeroport.getLongitude(), aeroport.getLatitude());
            if (dist < minDistance) {
                minDistance = dist;
                nearest = aeroport;
            }
        }

        return nearest;
    }

    private double distance(double lon1, double lat1, double lon2, double lat2) {
        double theta1 = lat1;
        double phi1 = lon1;
        double theta2 = lat2;
        double phi2 = lon2;

        double part1 = Math.pow(theta2 - theta1, 2);
        double cosTerme = Math.cos(Math.toRadians((theta2 + theta1) / 2.0));
        double part2 = Math.pow((phi2 - phi1) * cosTerme, 2);

        return Math.sqrt(part1 + part2);
    }

    public ArrayList<Aeroport> getList() {
        return list;
    }
}
