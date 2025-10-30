package ua.gov.court.supreme.contractwork.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnector {
    Connection getConnection() throws SQLException;
}
