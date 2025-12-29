package ua.gov.court.supreme.contractwork.dao;

import ua.gov.court.supreme.contractwork.db.PostgresConnector;
import ua.gov.court.supreme.contractwork.model.Estimate;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstimateDAO {
    private final PostgresConnector postgresConnector;

    public EstimateDAO() {
        this.postgresConnector = new PostgresConnector();
    }

    public int insertProject(Estimate projectForEstimate) {
        int generatedId = -1;
        String query = """
                INSERT INTO estimate (kekv, dk_code, project_name, unit_of_measure,
                 quantity, price, total_price, special_fund, general_fund, justification)
                 VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                 """;

        try (Connection connection = postgresConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, projectForEstimate.getKekv());
            preparedStatement.setString(2, projectForEstimate.getDkCode());
            preparedStatement.setString(3, projectForEstimate.getProjectName());
            preparedStatement.setString(4, projectForEstimate.getUnitOfMeasure());
            preparedStatement.setInt(5, projectForEstimate.getQuantity());
            preparedStatement.setBigDecimal(6, projectForEstimate.getPrice());
            preparedStatement.setBigDecimal(7, projectForEstimate.getTotalPrice());
            preparedStatement.setBigDecimal(8, projectForEstimate.getSpecialFund());
            preparedStatement.setBigDecimal(9, projectForEstimate.getGeneralFund());
            preparedStatement.setString(10, projectForEstimate.getJustification());

            preparedStatement.executeUpdate();

            // Отримання згенерованого ID
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return generatedId;
    }

    public List<Estimate> getProjectsByKekv(int kekv) {
        List<Estimate> estimateProjectsByKekv = new ArrayList<>();

        String query = "";

        if (kekv == 2210) {
            query = "SELECT * FROM estimate WHERE kekv = '2210' ORDER BY id ASC";
        } else if (kekv == 2240) {
            query = "SELECT * FROM estimate WHERE kekv = '2240' ORDER BY id ASC";
        } else if (kekv == 3110) {
            query = "SELECT * FROM estimate WHERE kekv = '3110' ORDER BY id ASC";
        }

        if (!query.isEmpty()) {
            try (Connection connection = postgresConnector.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    estimateProjectsByKekv.add(Estimate.builder()
                            .id(resultSet.getLong("id"))
                            .kekv(resultSet.getString("kekv"))
                            .dkCode(resultSet.getString("dk_code"))
                            .projectName(resultSet.getString("project_name"))
                            .unitOfMeasure(resultSet.getString("unit_of_measure"))
                            .quantity(resultSet.getInt("quantity"))
                            .price(resultSet.getBigDecimal("price"))
                            .totalPrice(resultSet.getBigDecimal("total_price"))
                            .specialFund(resultSet.getBigDecimal("special_fund"))
                            .generalFund(resultSet.getBigDecimal("general_fund"))
                            .justification(resultSet.getString("justification"))
                            .build());
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return estimateProjectsByKekv;
    }

    public Estimate getProjectById(long id) {
        Estimate project = null;
        String query = "SELECT * FROM estimate WHERE id = ?";

        try (Connection connection = postgresConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    project = Estimate.builder()
                            .id(resultSet.getLong("id"))
                            .kekv(resultSet.getString("kekv"))
                            .dkCode(resultSet.getString("dk_code"))
                            .projectName(resultSet.getString("project_name"))
                            .unitOfMeasure(resultSet.getString("unit_of_measure"))
                            .quantity(resultSet.getInt("quantity"))
                            .price(resultSet.getBigDecimal("price"))
                            .totalPrice(resultSet.getBigDecimal("total_price"))
                            .specialFund(resultSet.getBigDecimal("special_fund"))
                            .generalFund(resultSet.getBigDecimal("general_fund"))
                            .justification(resultSet.getString("justification"))
                            .build();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return project;
    }

    public void updateProjectToEstimate(Estimate updatedProject) {
        String query = """
                UPDATE estimate SET 
                    kekv = ?, 
                    dk_code = ?, 
                    project_name = ?, 
                    unit_of_measure = ?, 
                    quantity = ?, 
                    price = ?, 
                    total_price = ?, 
                    special_fund = ?, 
                    general_fund = ?, 
                    justification = ? 
                WHERE id = ?
                """;

        try (Connection connection = postgresConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, updatedProject.getKekv());
            preparedStatement.setString(2, updatedProject.getDkCode());
            preparedStatement.setString(3, updatedProject.getProjectName());
            preparedStatement.setString(4, updatedProject.getUnitOfMeasure());
            preparedStatement.setInt(5, updatedProject.getQuantity());
            preparedStatement.setBigDecimal(6, updatedProject.getPrice());
            preparedStatement.setBigDecimal(7, updatedProject.getTotalPrice());
            preparedStatement.setBigDecimal(8, updatedProject.getSpecialFund());
            preparedStatement.setBigDecimal(9, updatedProject.getGeneralFund());
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
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
