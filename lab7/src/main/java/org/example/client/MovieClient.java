package org.example.client;

import org.springframework.web.client.RestTemplate;

// aplicatie simpla care invoca serviciile noastre rest ca un client extern
public class MovieClient {
    public static void main(String[] args) {
        // atentie: serverul principal (Lab7Application) trebuie sa fie pornit inainte sa rulezi asta!

        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "http://localhost:8080/api/movies";

        System.out.println("apelam get pentru a vedea filmele...");
        String result = restTemplate.getForObject(baseUrl, String.class);
        System.out.println(result);

        System.out.println("\nclientul a rulat cu succes. in mod normal aici poti adauga si teste pentru post/put/delete.");
    }
}