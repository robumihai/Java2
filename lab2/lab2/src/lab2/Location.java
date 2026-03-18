package lab2;
import java.util.Objects;

/**
 * Clasa abstracta de baza pentru locatii.
 * Este 'sealed' pentru a permite mostenirea doar de catre clasele cunoscute de noi.
 */
public abstract sealed class Location permits City, Airport, GasStation {
    private String name;
    private double x;
    private double y;

    public Location(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    @Override
    public String toString() {
        return "Location{name='" + name + "', x=" + x + ", y=" + y + "}";
    }

    /**
     * Doua locatii sunt considerate egale daca au acelasi nume.
     * Folosim clasa Objects din API-ul standard Java pentru a evita NullPointerException.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Location location = (Location) obj;
        return Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}