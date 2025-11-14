package ua.gov.court.supreme.contractwork.dao;

import ua.gov.court.supreme.contractwork.db.PostgresConnector;
import ua.gov.court.supreme.contractwork.enums.ProjectStatus;
import ua.gov.court.supreme.contractwork.model.Purchases;
import ua.gov.court.supreme.contractwork.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
            case 2210 -> "SELECT p.*, u.* FROM purchases p " +
                    "LEFT JOIN users u ON p.responsible_executor_id = u.id WHERE p.kekv = '2210'";
            case 2240 -> "SELECT p.*, u.* FROM purchases p " +
                    "LEFT JOIN users u ON p.responsible_executor_id = u.id WHERE p.kekv = '2240'";
            case 3110 -> "SELECT p.*, u.* FROM purchases p " +
                    "LEFT JOIN users u ON p.responsible_executor_id = u.id WHERE p.kekv = '3110'";
            default -> "";
        };

        try (Connection connection = postgresConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("last_name"),
                        resultSet.getString("first_name"),
                        resultSet.getString("middle_name"),
                        resultSet.getString("short_name"),
                        resultSet.getString("position")
                );

                java.sql.Date sqlDatePaymentTo = resultSet.getDate("payment_to");
                LocalDate paymentTo = (sqlDatePaymentTo != null) ? sqlDatePaymentTo.toLocalDate() : null;

                int statusInt = resultSet.getInt("status");
                ProjectStatus projectStatus = ProjectStatus.fromInt(statusInt);

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
                        paymentTo,
                        resultSet.getDouble("special_fund"),
                        resultSet.getDouble("general_fund"),
                        resultSet.getString("justification"),
                        resultSet.getBoolean("informatization"),
                        user,
                        projectStatus
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return purchasesProjectsByKekv;
    }
}
