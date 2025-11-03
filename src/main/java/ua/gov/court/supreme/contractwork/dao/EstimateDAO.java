package ua.gov.court.supreme.contractwork.dao;

import ua.gov.court.supreme.contractwork.db.PostgresConnector;
import ua.gov.court.supreme.contractwork.model.Estimate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstimateDAO {
    private final PostgresConnector postgresConnector;

    public EstimateDAO() {
        this.postgresConnector = new PostgresConnector();
    }

    private static final String INSERT_SQL = """
            INSERT INTO estimate (kekv, dk_code, name_project, unit_of_measure,
            quantity, price, total_price, special_fund, general_fund, justification)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String SELECT_KEKV2210_SQL = """
                    SELECT * FROM estimate WHERE kekv = '2210';
            """;

    public void insertProject(Estimate newProject) {
        try (Connection connection = postgresConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            preparedStatement.setString(1, newProject.getKekv());
            preparedStatement.setString(2, newProject.getDkCode());
            preparedStatement.setString(3, newProject.getNameProject());
            preparedStatement.setString(4, newProject.getUnitOfMeasure());
            preparedStatement.setDouble(5, newProject.getQuantity());
            preparedStatement.setDouble(6, newProject.getPrice());
            preparedStatement.setDouble(7, newProject.getTotalPrice());
            preparedStatement.setDouble(8, newProject.getSpecialFund());
            preparedStatement.setDouble(9, newProject.getGeneralFund());
            preparedStatement.setString(10, newProject.getJustification());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Estimate> getProjectsByKekv(int kekv) {
        List<Estimate> estimateProjectsByKekv = new ArrayList<>();

        String query = switch (kekv) {
            case 2210 -> "SELECT * FROM estimate WHERE kekv = '2210'";
            case 2240 -> "SELECT * FROM estimate WHERE kekv = '2240'";
            case 3110 -> "SELECT * FROM estimate WHERE kekv = '3110'";
            default -> "";
        };

        try (Connection connection = postgresConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery();) {

            while (resultSet.next()) {
                estimateProjectsByKekv.add(new Estimate(
                        resultSet.getLong("id"),
                        resultSet.getString("kekv"),
                        resultSet.getString("dk_code"),
                        resultSet.getString("name_project"),
                        resultSet.getString("unit_of_measure"),
                        resultSet.getDouble("quantity"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("total_price"),
                        resultSet.getDouble("special_fund"),
                        resultSet.getDouble("general_fund"),
                        resultSet.getString("justification")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return estimateProjectsByKekv;
    }
}
