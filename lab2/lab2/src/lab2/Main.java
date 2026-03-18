package lab2;

public class Main {
    public static void main(String[] args) {
        Problem problem = new Problem();

        // 1. Cream locatii diferite folosind noile clase (City, Airport) in loc de LocationType
        City iasi = new City("Iasi", 10.0, 20.0, 300000);
        City bucuresti = new City("Bucuresti", 50.0, 100.0, 2000000);
        Airport otopeni = new Airport("Otopeni", 48.0, 98.0, 3);
        GasStation petrom = new GasStation("Petrom", 15.0, 25.0, 7.5);

        // 2. Le adaugam in problema
        problem.addLocation(iasi);
        problem.addLocation(bucuresti);
        problem.addLocation(otopeni);
        problem.addLocation(petrom);

        System.out.println("--- Testare Duplicate ---");
        // Ar trebui sa dea mesaj de eroare in consola, nu il va adauga
        problem.addLocation(new City("Iasi", 10.0, 20.0, 300000));
        problem.addLocation(new City("Cluj", 10.0, 20.0, 300000));
        problem.addLocation(new City("Cluj", 10.0, 20.0, 300000));



        // 3. Cream drumurile
        Road r1 = new Road(RoadType.HIGHWAY, 350.0, 130, iasi, bucuresti);
        Road r2 = new Road(RoadType.EXPRESS, 15.0, 100, bucuresti, otopeni);

        problem.addRoad(r1);
        problem.addRoad(r2);

        // 4. Testam algoritmul si validarea
        System.out.println("\n--- Rezultate Algoritm ---");
        System.out.println("Problema este valida? " + problem.isValid());
        System.out.println("Pot ajunge din Iasi la Otopeni? " + problem.canReach(iasi, otopeni));
        System.out.println("Pot ajunge din Iasi la Petrom? " + problem.canReach(iasi, petrom));
    }
}