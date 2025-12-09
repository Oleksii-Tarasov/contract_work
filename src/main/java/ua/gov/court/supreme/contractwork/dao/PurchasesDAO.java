package ua.gov.court.supreme.contractwork.dao;

import ua.gov.court.supreme.contractwork.db.PostgresConnector;
import ua.gov.court.supreme.contractwork.enums.ProjectStatus;
import ua.gov.court.supreme.contractwork.model.Purchases;
import ua.gov.court.supreme.contractwork.model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchasesDAO {
    private final PostgresConnector postgresConnector;

    public PurchasesDAO() {
        this.postgresConnector = new PostgresConnector();
    }

    public void insertProjectsFromEstimate() {
        String query = """
                TRUNCATE TABLE purchases RESTART IDENTITY;
                INSERT INTO purchases (kekv, dk_code, name_project, unit_of_measure,
                quantity, price, total_price, remaining_balance, special_fund, general_fund, justification)
                SELECT kekv, dk_code, name_project, unit_of_measure,
                quantity, price, total_price, total_price, special_fund, general_fund, justification
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
                    "LEFT JOIN users u ON p.responsible_executor_id = u.id WHERE p.kekv = '2210' ORDER BY p.id ASC";
            case 2240 -> "SELECT p.*, u.* FROM purchases p " +
                    "LEFT JOIN users u ON p.responsible_executor_id = u.id WHERE p.kekv = '2240' ORDER BY p.id ASC";
            case 3110 -> "SELECT p.*, u.* FROM purchases p " +
                    "LEFT JOIN users u ON p.responsible_executor_id = u.id WHERE p.kekv = '3110' ORDER BY p.id ASC";
            default -> "";
        };

        try (Connection connection = postgresConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                User responsibleExecutor = new User(
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
                        responsibleExecutor,
                        projectStatus
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return purchasesProjectsByKekv;
    }

    public Purchases getProjectById(long id) {
        Purchases purchase = null;
        String query = "SELECT p.*, u.* FROM purchases p " +
                "LEFT JOIN users u ON p.responsible_executor_id = u.id WHERE p.id = ?";

        try (Connection connection = postgresConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User responsibleExecutor = new User(
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

                    purchase = new Purchases(
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
                            responsibleExecutor,
                            projectStatus
                    );
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return purchase;
    }

    public Long getExecutorIdFromProject(long projectId) {
        String query = "SELECT responsible_executor_id FROM purchases WHERE id = ?";
        try (Connection connection = postgresConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                return resultSet.wasNull() ? null : id;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateProjectExecutor(long projectId, Long executorId) {
        String query = "UPDATE purchases SET responsible_executor_id = ? WHERE id = ?";

        try (Connection connection = postgresConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (executorId == null) {
                preparedStatement.setNull(1, Types.BIGINT);
            } else {
                preparedStatement.setLong(1, executorId);
            }
            preparedStatement.setLong(2, projectId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateJustification(long projectId, String justification) {
        String query = "UPDATE purchases SET justification = ? WHERE id = ?";

        try (Connection connection = postgresConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (justification == null || justification.trim().isEmpty()) {
                preparedStatement.setNull(1, Types.VARCHAR);
            } else {
                preparedStatement.setString(1, justification.trim());
            }
            preparedStatement.setLong(2, projectId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProjectStatus(long projectId, ProjectStatus status) {
        String query = "UPDATE purchases SET status = ? WHERE id = ?";

        try (Connection connection = postgresConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (status == null) {
                preparedStatement.setNull(1, Types.INTEGER);
            } else {
                preparedStatement.setInt(1, status.getDbValue());
            }

            preparedStatement.setLong(2, projectId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateContractPrice(long projectId, double contractPrice, double remainingBalance) {
        String query = "UPDATE purchases SET contract_price = ?, remaining_balance = ? WHERE id = ?";

        try (Connection connection = postgresConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, contractPrice);
            preparedStatement.setDouble(2, remainingBalance);
            preparedStatement.setLong(3, projectId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePaymentDueDate(long projectId, LocalDate paymentDate) {
        String query = "UPDATE purchases SET payment_to = ? WHERE id = ?";

        try (Connection connection = postgresConnector.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (paymentDate != null) {
                preparedStatement.setDate(1, Date.valueOf(paymentDate));
            } else {
                preparedStatement.setNull(1, Types.DATE);
            }

            preparedStatement.setLong(2, projectId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
