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

    public Estimate getProjectById(long id) {
        Estimate projectFromEstimate = null;
        String query = "SELECT * FROM estimate WHERE id = ?";

        try (Connection connection = postgresConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    projectFromEstimate = new Estimate(
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
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return projectFromEstimate;
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

    public void updateProjectToEstimate(Estimate updatedProject) {
        String query = """
                UPDATE estimate SET kekv=?, dk_code=?, name_project=?, unit_of_measure=?, quantity=?,
                price=?, total_price=?, special_fund=?, general_fund=?, justification=?
                WHERE id=?
                """;

        try (Connection connection = postgresConnector.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, updatedProject.getKekv());
            preparedStatement.setString(2, updatedProject.getDkCode());
            preparedStatement.setString(3, updatedProject.getNameProject());
            preparedStatement.setString(4, updatedProject.getUnitOfMeasure());
            preparedStatement.setDouble(5, updatedProject.getQuantity());
            preparedStatement.setDouble(6, updatedProject.getPrice());
            preparedStatement.setDouble(7, updatedProject.getTotalPrice());
            preparedStatement.setDouble(8, updatedProject.getSpecialFund());
            preparedStatement.setDouble(9, updatedProject.getGeneralFund());
            preparedStatement.setString(10, updatedProject.getJustification());
            preparedStatement.setLong(11, updatedProject.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteProjectFromEstimateById(long projectId) {
        String query = "DELETE FROM estimate WHERE id = ?";

        try (Connection connection = postgresConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, projectId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Project with ID " + projectId + " deleted successfully.");
            } else {
                System.out.println("Project with ID " + projectId + " not found or not deleted.");
            }
        } catch (SQLException e) {
            System.err.println("SQL error during deletion: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
