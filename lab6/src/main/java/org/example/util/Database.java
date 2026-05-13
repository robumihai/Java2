package org.example.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

// clasa singleton pentru gestionarea pool-ului de conexiuni
public class Database {
    private static HikariDataSource dataSource;

    // bloc static pentru a initializa hikaricp o singura data
    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/movies_db");
        config.setUsername("postgres");
        config.setPassword("1234");

        // setari specifice pentru pool-ul de conexiuni
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);

        dataSource = new HikariDataSource(config);
    }

    private Database() {}

    // metoda care extrage o conexiune disponibila din pool
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // inchidem tot pool-ul la finalul aplicatiei
    public static void closePool() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}