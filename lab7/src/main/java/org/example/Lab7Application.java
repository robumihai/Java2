package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * clasa principala de start a aplicatiei spring boot.
 */
@SpringBootApplication
public class Lab7Application {
    public static void main(String[] args) {
        SpringApplication.run(Lab7Application.class, args);
        System.out.println("serverul a pornit pe http://localhost:8080 !");
    }
}