package lab2;
import java.util.Objects;

/**
 * Clasa care descrie un drum intre doua locatii.
 */
public class Road {
    private RoadType type;
    private double length;
    private int speedLimit;
    private Location locationA;
    private Location locationB;

    /**
     * Constructor pentru drum. Include validarea distantei euclidiene.
     */
    public Road(RoadType type, double length, int speedLimit, Location locationA, Location locationB) {
        this.type = type;
        this.speedLimit = speedLimit;
        this.locationA = locationA;
        this.locationB = locationB;

        // Validam daca lungimea drumului este realista conform coordonatelor (folosind Math API)
        double dx = locationA.getX() - locationB.getX();
        double dy = locationA.getY() - locationB.getY();
        double minDistance = Math.sqrt(dx * dx + dy * dy);

        if (length < minDistance) {
            this.length = minDistance; // fortam distanta sa fie macar in linie dreapta
        } else {
            this.length = length;
        }
    }

    public RoadType getType() { return type; }
    public double getLength() { return length; }
    public int getSpeedLimit() { return speedLimit; }
    public Location getLocationA() { return locationA; }
    public Location getLocationB() { return locationB; }

    @Override
    public String toString() {
        return "Road{type=" + type + ", length=" + length +
                ", connects=" + locationA.getName() + "-" + locationB.getName() + "}";
    }

    /**
     * Un drum este identic cu altul daca are acelasi tip si leaga aceleasi locatii,
     * indiferent de sensul de parcurgere.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Road road = (Road) obj;

        // Verificam ambele sensuri posibile
        boolean sameDirect = locationA.equals(road.locationA) && locationB.equals(road.locationB);
        boolean sameReversed = locationA.equals(road.locationB) && locationB.equals(road.locationA);

        return type == road.type && (sameDirect || sameReversed);
    }

    @Override
    public int hashCode() {
        // Adunarea asigura ca hash-ul ramane la fel chiar daca inversam locatiile A si B
        return Objects.hash(type) + locationA.hashCode() + locationB.hashCode();
    }
}