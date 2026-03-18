package lab2;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Reprezinta instanta problemei noastre (un graf de locatii si drumuri).
 */
public class Problem {
    // Folosim interfata List din Java API pentru a stoca datele
    private List<Location> locations = new ArrayList<>();
    private List<Road> roads = new ArrayList<>();

    /**
     * Adauga o locatie noua in problema
     * @param loc Locatia de adaugat
     */
    public void addLocation(Location loc) {
        if (!locations.contains(loc)) {
            locations.add(loc);
            System.out.println("Locatia " + loc.getName() + " a fost adaugata.");
        } else {
            System.out.println("EROARE: Locatia " + loc.getName() + " exista deja! Nu o adaugam.");
        }
    }

    /**
     * Adauga un drum nou in problema.
     * @param road Drumul de adaugat.
     */
    public void addRoad(Road road) {
        if (!roads.contains(road)) {
            roads.add(road);
        }
    }

    /**
     * Verifica daca problema definita este valid
     * @return true daca toate drumurile leaga locatii existente in lista noastra
     */
    public boolean isValid() {
        for (Road r : roads) {
            if (!locations.contains(r.getLocationA()) || !locations.contains(r.getLocationB())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica daca exista o ruta intre doua locatii folosind algoritmul BFS
     * @param start Locatia de plecare
     * @param destination Locatia dorita
     * @return true daca destinatia poate fi atinsa, false altfel
     */
    public boolean canReach(Location start, Location destination) {
        if (!locations.contains(start) || !locations.contains(destination)) {
            return false;
        }

        // Folosim o coada pentru a parcurge locatiile
        Queue<Location> queue = new LinkedList<>();
        // Folosim un set pentru a memora pe unde am fost si a evita buclele infinite
        Set<Location> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Location current = queue.poll();

            if (current.equals(destination)) {
                return true;
            }

            // Verificam toate drumurile ca sa vedem cu cine se invecineaza locatia curenta
            for (Road road : roads) {
                Location neighbor = null;

                if (road.getLocationA().equals(current)) {
                    neighbor = road.getLocationB();
                } else if (road.getLocationB().equals(current)) {
                    neighbor = road.getLocationA();
                }

                if (neighbor != null && !visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return false;
    }
}