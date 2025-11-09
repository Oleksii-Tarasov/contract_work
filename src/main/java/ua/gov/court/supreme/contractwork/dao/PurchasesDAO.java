package ua.gov.court.supreme.contractwork.dao;

import ua.gov.court.supreme.contractwork.db.PostgresConnector;
import ua.gov.court.supreme.contractwork.model.Purchases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchasesDAO {
    private final PostgresConnector postgresConnector;

    public PurchasesDAO() {
        this.postgresConnector = new  PostgresConnector();
    }

    public void insertProjectsFromEstimate() {
        String query = """
            TRUNCATE TABLE purchases RESTART IDENTITY;
            INSERT INTO purchases (kekv, dk_code, name_project, unit_of_measure,
            quantity, price, total_price, special_fund, general_fund, justification)
            SELECT kekv, dk_code, name_project, unit_of_measure,
            quantity, price, total_price, special_fund, general_fund, justification
            FROM estimate
            """;

        try (Connection connection = postgresConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Purchases> getProjectsByKekv(int kekv) {
        List<Purchases> purchasesProjectsByKekv = new ArrayList<>();

        String query = switch (kekv) {
            case 2210 -> "SELECT * FROM purchases WHERE kekv = '2210'";
            case 2240 -> "SELECT * FROM purchases WHERE kekv = '2240'";
            case 3110 -> "SELECT * FROM purchases WHERE kekv = '3110'";
            default -> "";
        };

        try (Connection connection = postgresConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                purchasesProjectsByKekv.add(new Purchases(
                        resultSet.getLong("id"),
                        resultSet.getString("kekv"),
                        resultSet.getString("dk_code"),
                        resultSet.getString("name_project"),
                        resultSet.getString("unit_of_measure"),
                        resultSet.getDouble("quantity"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("total_price"),
                        resultSet.getDouble("contract_price"),
                        resultSet.getDouble("remaining_balance"),
                        resultSet.getDouble("special_fund"),
                        resultSet.getDouble("general_fund"),
                        resultSet.getString("justification"),
                        resultSet.getBoolean("informatization")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return purchasesProjectsByKekv;
    }
}
