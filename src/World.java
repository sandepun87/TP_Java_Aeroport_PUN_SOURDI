import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class World {
    private ArrayList<Aeroport> aeroports;

    public World() {
        aeroports = new ArrayList<>();
        System.out.println("Chargement des aéroports...");

        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/airport-codes_no_comma.csv"));
            String line;
            boolean firstLine = true;
            int lineCount = 0;

            while ((line = reader.readLine()) != null) {
                lineCount++;
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip header
                }

                // Nettoyer la ligne
                line = line.trim();
                if (line.isEmpty()) continue;

                // Séparer les champs
                String[] fields = line.split(",", -1); // -1 pour garder les champs vides

                if (fields.length >= 12) {
                    try {
                        String ident = fields[0].trim();
                        String type = fields[1].trim();
                        String name = fields[2].trim();

                        // Élévation (peut être vide)
                        int elevation = 0;
                        if (!fields[3].trim().isEmpty()) {
                            try {
                                elevation = Integer.parseInt(fields[3].trim());
                            } catch (NumberFormatException e) {
                                elevation = 0;
                            }
                        }

                        String continent = fields[4].trim();
                        String country = fields[5].trim();
                        String region = fields[6].trim();
                        String municipality = fields[7].trim();
                        String gpsCode = fields[8].trim();
                        String iataCode = fields[9].trim();
                        String localCode = fields[10].trim();

                        // Coordonnées
                        String coords = fields[11].trim();
                        coords = coords.replace("\"", "").replace(" ", "");
                        String[] coordParts = coords.split(",");

                        double longitude = 0.0;
                        double latitude = 0.0;

                        if (coordParts.length >= 2) {
                            try {
                                longitude = Double.parseDouble(coordParts[0]);
                                latitude = Double.parseDouble(coordParts[1]);
                            } catch (NumberFormatException e) {
                                // Coordonnées invalides, on continue
                            }
                        }

                        Aeroport aeroport = new Aeroport(ident, type, name, elevation,
                                continent, country, region, municipality,
                                gpsCode, iataCode, localCode,
                                longitude, latitude);
                        aeroports.add(aeroport);

                    } catch (Exception e) {
                        System.out.println("Erreur ligne " + lineCount + ": " + e.getMessage());
                    }
                }
            }
            reader.close();
            System.out.println("Chargement terminé. " + aeroports.size() + " aéroports trouvés.");

        } catch (Exception e) {
            System.out.println("Erreur de lecture du fichier: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Aeroport findByCode(String code) {
        for (Aeroport aeroport : aeroports) {
            if (code.equals(aeroport.getIataCode()) || code.equals(aeroport.getIdent())) {
                return aeroport;
            }
        }
        return null;
    }

    public Aeroport findNearestAirport(double lat, double lon) {
        if (aeroports.isEmpty()) return null;

        Aeroport nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Aeroport aeroport : aeroports) {
            // Filtrer seulement les large_airport
            if ("large_airport".equals(aeroport.getType())) {
                double dist = distance(lat, lon, aeroport.getLatitude(), aeroport.getLongitude());
                if (dist < minDistance) {
                    minDistance = dist;
                    nearest = aeroport;
                }
            }
        }
        return nearest;
    }

    public List<Aeroport> getAeroports(int start, int count) {
        int end = Math.min(start + count, aeroports.size());
        return aeroports.subList(start, end);
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // Rayon de la Terre en km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }
}