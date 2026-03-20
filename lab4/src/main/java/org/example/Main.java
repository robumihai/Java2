package org.example;

import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm.SpanningTree;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.*;

/**
 * Clasa principala unde testam problema de instalare a camerelor video.
 */
public class Main {
    public static void main(String[] args) {
        City city = new City();

        // Initializam 10 intersectii si 20 strazi
        city.generateFakeData(10, 20);

        // Verificam ca Set-ul nu contine duplicate adaugand un element deja existent
        Intersection randomIntersection = city.getIntersections().iterator().next();
        city.getIntersections().add(randomIntersection);
        System.out.println("Numarul de intersectii (ar trebui sa fie 10, fara duplicate): "
                + city.getIntersections().size());

        // Apelam query-ul din Homework
        city.printSpecificStreets(40);

        System.out.println("\n--- Calculare solutii de retea pentru cabluri (Homework) ---");
        findAlternativeSolutions(city, 3);
    }

    /**
     * Gaseste si afiseaza primele 'k' solutii pentru reteaua de cabluri, ordonate dupa cost.
     * Foloseste algoritmul Kruskal din libraria JGraphT.
     * * @param city Orasul curent generat
     * @param k Numarul de solutii dorite
     */
    private static void findAlternativeSolutions(City city, int k) {
        // Construim graful problemei cu JGraphT
        Graph<Intersection, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        for (Intersection i : city.getIntersections()) {
            graph.addVertex(i);
        }
        for (Street s : city.getStreets()) {
            DefaultWeightedEdge edge = graph.addEdge(s.getU(), s.getV());
            if (edge != null) {
                graph.setEdgeWeight(edge, s.getLength());
            }
        }

        // 1. Gasim solutia optima principala (Arborele Minim de Acoperire - MST)
        KruskalMinimumSpanningTree<Intersection, DefaultWeightedEdge> kruskal = new KruskalMinimumSpanningTree<>(graph);
        SpanningTree<DefaultWeightedEdge> bestMST = kruskal.getSpanningTree();

        if (bestMST == null || bestMST.getWeight() == Double.MAX_VALUE) {
            System.out.println("Graful nu este conex. Nu se pot conecta toate intersectiile!");
            return;
        }

        List<SpanningTree<DefaultWeightedEdge>> solutions = new ArrayList<>();
        solutions.add(bestMST);

        // 2. Cautam solutii alternative scotand temporar pe rand cate o muchie din solutia optima
        for (DefaultWeightedEdge edge : bestMST.getEdges()) {
            double weight = graph.getEdgeWeight(edge);
            Intersection u = graph.getEdgeSource(edge);
            Intersection v = graph.getEdgeTarget(edge);

            // Eliminam strada pentru a forta algoritmul sa gaseasca o alta ruta
            graph.removeEdge(edge);

            // Recalculam noul MST
            SpanningTree<DefaultWeightedEdge> alternative = new KruskalMinimumSpanningTree<>(graph).getSpanningTree();

            // Daca inca putem conecta toate nodurile, adaugam solutia
            if (alternative.getWeight() < Double.MAX_VALUE) {
                solutions.add(alternative);
            }

            // Punem muchia la loc pentru urmatoarea iteratie a for-ului
            DefaultWeightedEdge restored = graph.addEdge(u, v);
            graph.setEdgeWeight(restored, weight);
        }

        // 3. Sortam solutiile crescator dupa cost
        List<SpanningTree<DefaultWeightedEdge>> topSolutions = solutions.stream()
                .sorted(Comparator.comparingDouble(SpanningTree::getWeight))
                .limit(k)
                .toList();

        // 4. Afisam rezultatul dorit
        System.out.println("Top " + k + " variante posibile de retele (ordonate dupa costul minim):");
        for (int i = 0; i < topSolutions.size(); i++) {
            System.out.println("Varianta " + (i + 1) + " | Cost total: " + topSolutions.get(i).getWeight() + " unitati");
        }
    }
}