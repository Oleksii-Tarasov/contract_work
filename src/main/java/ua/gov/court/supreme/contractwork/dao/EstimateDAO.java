package ua.gov.court.supreme.contractwork.dao;

import ua.gov.court.supreme.contractwork.db.PostgresConnector;
import ua.gov.court.supreme.contractwork.model.Estimate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
