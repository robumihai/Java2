package org.example;

import com.github.javafaker.Faker;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Clasa City gestioneaza generarea datelor orasului si interogarile pe retea.
 */
public class City {
    // Folosim HashSet pentru intersectii si LinkedList pentru strazi conform cerintei
    private Set<Intersection> intersections = new HashSet<>();
    private List<Street> streets = new LinkedList<>();

    public Set<Intersection> getIntersections() { return intersections; }
    public List<Street> getStreets() { return streets; }

    /**
     * Genereaza date false pentru oras folosind biblioteca JavaFaker.
     * * @param numIntersections Numarul dorit de intersectii
     * @param numStreets Numarul dorit de strazi
     */
    public void generateFakeData(int numIntersections, int numStreets) {
        Faker faker = new Faker();

        // Cream intersectii folosind Java Stream API
        intersections = IntStream.range(0, numIntersections)
                .mapToObj(i -> new Intersection(faker.address().streetName() + " Crossing"))
                .collect(Collectors.toSet());

        List<Intersection> list = new ArrayList<>(intersections);
        Random random = new Random();

        // Cream strazi random intre intersectiile generate mai sus
        while (streets.size() < numStreets) {
            Intersection u = list.get(random.nextInt(list.size()));
            Intersection v = list.get(random.nextInt(list.size()));

            // Ne asiguram ca strada nu uneste aceeasi intersectie cu ea insasi
            if (!u.equals(v)) {
                int length = faker.number().numberBetween(10, 100);
                Street newStreet = new Street(faker.address().streetName(), length, u, v);

                // Evitam adaugarea de strazi duplicate
                if (!streets.contains(newStreet)) {
                    streets.add(newStreet);
                }
            }
        }

        // Sortam lista de strazi folosind metoda de referinta (lambda) pentru comparator
        streets.sort(Comparator.comparingInt(Street::getLength));
    }

    /**
     * Query cu Java Stream API pentru a afisa strazile mai lungi de o valoare specificata
     * care unesc cel putin 3 alte strazi.
     * * @param minLength Lungimea minima a strazii
     */
    public void printSpecificStreets(int minLength) {
        // Mapam fiecare intersectie la numarul de strazi conectate la ea (gradul nodului)
        Map<Intersection, Long> degreeMap = streets.stream()
                .flatMap(s -> Stream.of(s.getU(), s.getV()))
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));

        System.out.println("\n--- Strazi mai lungi de " + minLength + "m care unesc >= 3 alte strazi ---");

        streets.stream()
                .filter(s -> s.getLength() > minLength)
                .filter(s -> {
                    // Calculam numarul de strazi adiacente excluzand strada in sine (-2)
                    long adjacentStreets = degreeMap.get(s.getU()) + degreeMap.get(s.getV()) - 2;
                    return adjacentStreets >= 3;
                })
                .forEach(System.out::println);
    }
}