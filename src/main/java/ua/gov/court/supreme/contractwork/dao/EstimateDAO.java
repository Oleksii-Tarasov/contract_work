package ua.gov.court.supreme.contractwork.dao;

import ua.gov.court.supreme.contractwork.db.PostgresConnector;
import ua.gov.court.supreme.contractwork.model.Estimate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstimateDAO {
    private final PostgresConnector postgresConnector;

    public EstimateDAO() {
        this.postgresConnector = new PostgresConnector();
    }

    public int save(Estimate project) {
        int generatedId = -1;
        String query = """
                INSERT INTO estimate (kekv, dk_code, project_name, unit_of_measure,
                 quantity, price, total_price, special_fund, general_fund, justification)
                 VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = postgresConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, project.getKekv());
            preparedStatement.setString(2, project.getDkCode());
            preparedStatement.setString(3, project.getProjectName());
            preparedStatement.setString(4, project.getUnitOfMeasure());
            preparedStatement.setInt(5, project.getQuantity());
            preparedStatement.setBigDecimal(6, project.getPrice());
            preparedStatement.setBigDecimal(7, project.getTotalPrice());
            preparedStatement.setBigDecimal(8, project.getSpecialFund());
            preparedStatement.setBigDecimal(9, project.getGeneralFund());
            preparedStatement.setString(10, project.getJustification());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                generatedId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return generatedId;
    }

    public List<Estimate> findAllByKekv(int kekv) {
        List<Estimate> estimateProjectsByKekv = new ArrayList<>();

        String query = switch (kekv) {
            case 2210 -> "SELECT * FROM estimate WHERE kekv = '2210' ORDER BY id ASC";
            case 2240 -> "SELECT * FROM estimate WHERE kekv = '2240' ORDER BY id ASC";
            case 3110 -> "SELECT * FROM estimate WHERE kekv = '3110' ORDER BY id ASC";
            default -> "";
        };

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

    public Estimate findById(long id) {
        Estimate project = null;
        String query = """
                SELECT * FROM estimate WHERE id = ?
                """;

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

    public void update(Estimate project) {
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

            preparedStatement.setString(1, project.getKekv());
            preparedStatement.setString(2, project.getDkCode());
            preparedStatement.setString(3, project.getProjectName());
            preparedStatement.setString(4, project.getUnitOfMeasure());
            preparedStatement.setInt(5, project.getQuantity());
            preparedStatement.setBigDecimal(6, project.getPrice());
            preparedStatement.setBigDecimal(7, project.getTotalPrice());
            preparedStatement.setBigDecimal(8, project.getSpecialFund());
            preparedStatement.setBigDecimal(9, project.getGeneralFund());
            preparedStatement.setString(10, project.getJustification());
            preparedStatement.setLong(11, project.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(long id) {
        String query = """
                DELETE FROM estimate WHERE id = ?
                """;

        try (Connection connection = postgresConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
