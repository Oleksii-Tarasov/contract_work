package ua.gov.court.supreme.contractwork.service;

import java.io.IOException;
import java.util.Properties;


public class PropertiesLoader {
    private static final Properties properties = new Properties();
    private static PropertiesLoader instance;

    public PropertiesLoader() {
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized PropertiesLoader getInstance() {
        if (instance == null) {
            instance = new PropertiesLoader();
        }
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    //    Postgres DB
    public String getPostgresUrl() {
        return getProperty("postgres.db.url");
    }

    public String getPostgresUser() {
        return getProperty("postgres.db.user");
    }

    public String getPostgresPassword() {
        return getProperty("postgres.db.password");
    }
}
