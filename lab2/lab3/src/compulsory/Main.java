package compulsory;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Cream o lista de tip Profile care sa accepte atat Person cat si Company
        List<Profile> network = new ArrayList<>();

        network.add(new Person("P01", "Popescu Ion"));
        network.add(new Company("C01", "Tech Solutions SRL"));
        network.add(new Person("P02", "Alexandrescu Maria"));
        network.add(new Company("C02", "Alpha Corp"));
        network.add(new Person("P03", "Zaharia Andrei"));

        System.out.println("--- Lista inainte de sortare ---");
        for (Profile p : network) {
            System.out.println(p);
        }

        // Sortam lista folosind un Comparator pe baza interfetei Profile
        // Folosim metoda getName() impusa de interfata
        network.sort(Comparator.comparing(Profile::getName));

        System.out.println("\n--- Lista dupa sortare (alfabetic) ---");
        for (Profile p : network) {
            System.out.println(p);
        }
    }
}