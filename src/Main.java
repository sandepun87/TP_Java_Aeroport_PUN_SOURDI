import java.util.List;

public class Main {
    public static void main(String[] args) {
        World world = new World();

        // Test CDG
        Aeroport cdg = world.findByCode("CDG");
        if (cdg != null) {
            System.out.println("CDG trouvé: " + cdg.getName());
        } else {
            System.out.println("CDG non trouvé");
        }

        // Aéroport le plus proche de Paris (large_airport seulement)
        Aeroport nearest = world.findNearestAirport(48.8566, 2.3522);
        if (nearest != null) {
            System.out.println("Aéroport le plus proche de Paris: " + nearest.getName());
        } else {
            System.out.println("Aucun aéroport trouvé près de Paris");
        }

        // 5 premiers aéroports
        System.out.println("\n5 premiers aéroports:");
        List<Aeroport> firstAeroports = world.getAeroports(0, 5);
        for (Aeroport aeroport : firstAeroports) {
            System.out.println(aeroport.getName() + " (" + aeroport.getIataCode() + ")");
        }
    }
}