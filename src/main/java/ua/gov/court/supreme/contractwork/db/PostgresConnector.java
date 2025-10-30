package ua.gov.court.supreme.contractwork.db;

import ua.gov.court.supreme.contractwork.service.PropertiesLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnector implements DatabaseConnector {
    public final PropertiesLoader propertiesLoader;

    public PostgresConnector() {
        this.propertiesLoader = PropertiesLoader.getInstance();

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                propertiesLoader.getPostgresUrl(),
                propertiesLoader.getPostgresUser(),
                propertiesLoader.getPostgresPassword()
        );
    }
}
