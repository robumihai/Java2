package compulsory;
public class Person implements Profile, Comparable<Person> {
    private String id;
    private String name;

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    // Ordonarea naturala a obiectelor de tip Person, bazata pe nume
    @Override
    public int compareTo(Person other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return "Person {id='" + id + "', name='" + name + "'}";
    }
}