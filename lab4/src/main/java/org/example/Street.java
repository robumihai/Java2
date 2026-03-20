package org.example;

import java.util.Objects;

/**
 * Clasa care defineste o strada (muchia din graf).
 * Are o lungime si uneste doua intersectii.
 */
public class Street implements Comparable<Street> {
    private String name;
    private int length;
    private Intersection u;
    private Intersection v;

    /**
     * Constructor pentru a crea o strada noua.
     * * @param name Numele strazii generate
     * @param length Lungimea strazii (costul cablului)
     * @param u Prima intersectie
     * @param v A doua intersectie
     */
    public Street(String name, int length, Intersection u, Intersection v) {
        this.name = name;
        this.length = length;
        this.u = u;
        this.v = v;
    }

    public String getName() { return name; }
    public int getLength() { return length; }
    public Intersection getU() { return u; }
    public Intersection getV() { return v; }

    @Override
    public int compareTo(Street other) {
        // Folosit pentru a sorta usor strazile dupa lungime, conform cerintei
        return Integer.compare(this.length, other.length);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Street street = (Street) o;

        // O strada de la A la B este aceeasi cu o strada de la B la A (graf neorientat)
        return (Objects.equals(u, street.u) && Objects.equals(v, street.v)) ||
                (Objects.equals(u, street.v) && Objects.equals(v, street.u));
    }

    @Override
    public int hashCode() {
        // Ne asiguram ca hash-ul este acelasi indiferent de ordinea capetelor u si v
        return Objects.hash(
                Math.min(u.hashCode(), v.hashCode()),
                Math.max(u.hashCode(), v.hashCode())
        );
    }

    @Override
    public String toString() {
        return name + " (" + length + "m) [" + u.getName() + " - " + v.getName() + "]";
    }
}