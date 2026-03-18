package lab2;
/**
 * Clasa care reprezinta un aeroport.
 */
public final class Airport extends Location {
    private int numberOfTerminals;

    public Airport(String name, double x, double y, int numberOfTerminals) {
        super(name, x, y);
        this.numberOfTerminals = numberOfTerminals;
    }

    public int getNumberOfTerminals() { return numberOfTerminals; }
    public void setNumberOfTerminals(int numberOfTerminals) { this.numberOfTerminals = numberOfTerminals; }

    @Override
    public String toString() {
        return "Airport{" + "name='" + getName() + "', terminals=" + numberOfTerminals + "}";
    }
}