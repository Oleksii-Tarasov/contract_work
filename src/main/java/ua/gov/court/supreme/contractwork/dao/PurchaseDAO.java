package ua.gov.court.supreme.contractwork.dao;

import ua.gov.court.supreme.contractwork.db.PostgresConnector;
import ua.gov.court.supreme.contractwork.dto.ProjectUpdateRequest;
import ua.gov.court.supreme.contractwork.enums.ProjectStatus;
import ua.gov.court.supreme.contractwork.model.Purchase;
import ua.gov.court.supreme.contractwork.model.User;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDAO {
    private final PostgresConnector postgresConnector;

    public PurchaseDAO() {
        this.postgresConnector = new PostgresConnector();
    }

    public void insertProjectsFromEstimate() {
        String query = """
                TRUNCATE TABLE purchases RESTART IDENTITY;
                INSERT INTO purchases (kekv, dk_code, project_name, unit_of_measure,
                quantity, price, total_price, remaining_balance, special_fund, general_fund, justification)
                SELECT kekv, dk_code, project_name, unit_of_measure,
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

    public List<Purchase> getProjectsByKekv(int kekv) {
        List<Purchase> purchasesProjectsByKekv = new ArrayList<>();

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
                User responsibleExecutor = null;

                long executorId = resultSet.getLong("responsible_executor_id");

                if (executorId > 0) {
                    responsibleExecutor = User.builder()
                            .id(executorId)
                            .lastName(resultSet.getString("last_name"))
                            .firstName(resultSet.getString("first_name"))
                            .middleName(resultSet.getString("middle_name"))
                            .shortName(resultSet.getString("short_name"))
                            .position(resultSet.getString("position"))
                            .build();
                }

                java.sql.Date sqlDatePaymentTo = resultSet.getDate("payment_to");
                LocalDate paymentTo = (sqlDatePaymentTo != null) ? sqlDatePaymentTo.toLocalDate() : null;

                int statusInt = resultSet.getInt("status");
                ProjectStatus projectStatus = ProjectStatus.fromInt(statusInt);

                purchasesProjectsByKekv.add(Purchase.builder()
                        .id(resultSet.getLong("id"))
                        .kekv(resultSet.getString("kekv"))
                        .dkCode(resultSet.getString("dk_code"))
                        .projectName(resultSet.getString("project_name"))
                        .unitOfMeasure(resultSet.getString("unit_of_measure"))
                        .quantity(resultSet.getDouble("quantity"))
                        .price(resultSet.getBigDecimal("price"))
                        .totalPrice(resultSet.getBigDecimal("total_price"))
                        .contractPrice(resultSet.getBigDecimal("contract_price"))
                        .remainingBalance(resultSet.getBigDecimal("remaining_balance"))
                        .paymentTo(paymentTo)
                        .specialFund(resultSet.getBigDecimal("special_fund"))
                        .generalFund(resultSet.getBigDecimal("general_fund"))
                        .justification(resultSet.getString("justification"))
                        .informatization(resultSet.getBoolean("informatization"))
                        .responsibleExecutor(responsibleExecutor)
                        .projectStatus(projectStatus)
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return purchasesProjectsByKekv;
    }

    public Purchase getProjectById(long id) {
        Purchase purchase = null;
        String query = "SELECT p.*, u.* FROM purchases p " +
                "LEFT JOIN users u ON p.responsible_executor_id = u.id WHERE p.id = ?";

        try (Connection connection = postgresConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User responsibleExecutor = null;

                    long executorId = resultSet.getLong("responsible_executor_id");

                    if (executorId > 0) {
                       responsibleExecutor = User.builder()
                                .id(executorId)
                                .lastName(resultSet.getString("last_name"))
                                .firstName(resultSet.getString("first_name"))
                                .middleName(resultSet.getString("middle_name"))
                                .shortName(resultSet.getString("short_name"))
                                .position(resultSet.getString("position"))
                                .build();
                    }

                    java.sql.Date sqlDatePaymentTo = resultSet.getDate("payment_to");
                    LocalDate paymentTo = (sqlDatePaymentTo != null) ? sqlDatePaymentTo.toLocalDate() : null;

                    int statusInt = resultSet.getInt("status");
                    ProjectStatus projectStatus = ProjectStatus.fromInt(statusInt);

                    purchase = Purchase.builder()
                            .id(resultSet.getLong("id"))
                            .kekv(resultSet.getString("kekv"))
                            .dkCode(resultSet.getString("dk_code"))
                            .projectName(resultSet.getString("project_name"))
                            .unitOfMeasure(resultSet.getString("unit_of_measure"))
                            .quantity(resultSet.getDouble("quantity"))
                            .price(resultSet.getBigDecimal("price"))
                            .totalPrice(resultSet.getBigDecimal("total_price"))
                            .contractPrice(resultSet.getBigDecimal("contract_price"))
                            .remainingBalance(resultSet.getBigDecimal("remaining_balance"))
                            .paymentTo(paymentTo)
                            .specialFund(resultSet.getBigDecimal("special_fund"))
                            .generalFund(resultSet.getBigDecimal("general_fund"))
                            .justification(resultSet.getString("justification"))
                            .informatization(resultSet.getBoolean("informatization"))
                            .responsibleExecutor(responsibleExecutor)
                            .projectStatus(projectStatus)
                            .build();
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

    public void updateContractPrice(long projectId, BigDecimal contractPrice, BigDecimal remainingBalance) {
        String query = "UPDATE purchases SET contract_price = ?, remaining_balance = ? WHERE id = ?";

        try (Connection connection = postgresConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBigDecimal(1, contractPrice);
            preparedStatement.setBigDecimal(2, remainingBalance);
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

    public void updatePurchasesFields(ProjectUpdateRequest dto) {

        long projectId = dto.getId();

        // 1. Обґрунтування
        if (dto.getJustification() != null) {
            updateJustification(projectId, dto.getJustification());
        }

        // 2. Статус
        if (dto.getProjectStatus() != null) {
            updateProjectStatus(projectId, dto.getProjectStatus());
        }

        // 3. Виконавець
        if (dto.getExecutorId() != null) {
            updateProjectExecutor(projectId, dto.getExecutorId());
        }

        // 4. Сума договору + залишок
        if (dto.getContractPrice() != null) {
            Purchase purchase = getProjectById(projectId);
            BigDecimal contractPrice = dto.getContractPrice();
            BigDecimal remainingBalance = purchase.getTotalPrice().subtract(contractPrice);
            updateContractPrice(projectId, contractPrice, remainingBalance);
        }

        // 5. Оплата до
        if (dto.getPaymentTo() != null || dto.getPaymentTo() == null) {
            // null теж свідоме оновлення
            updatePaymentDueDate(projectId, dto.getPaymentTo());
        }
    }


    public void updateProjectToPurchases(Purchase updatedProject) {
        String query = """
                UPDATE purchases SET 
                    kekv = ?, 
                    dk_code = ?, 
                    project_name = ?, 
                    justification = ?, 
                    unit_of_measure = ?, 
                    quantity = ?, 
                    price = ?, 
                    total_price = ?, 
                    special_fund = ?, 
                    general_fund = ?, 
                    status = ?, 
                    responsible_executor_id = ?, 
                    contract_price = ?, 
                    payment_to = ?, 
                    remaining_balance = ? 
                WHERE id = ?
                """;

        try (Connection connection = postgresConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, updatedProject.getKekv());
            ps.setString(2, updatedProject.getDkCode());
            ps.setString(3, updatedProject.getProjectName());
            ps.setString(4, updatedProject.getJustification());
            ps.setString(5, updatedProject.getUnitOfMeasure());
            ps.setDouble(6, updatedProject.getQuantity());
            ps.setBigDecimal(7, updatedProject.getPrice());
            ps.setBigDecimal(8, updatedProject.getTotalPrice());
            ps.setBigDecimal(9, updatedProject.getSpecialFund());
            ps.setBigDecimal(10, updatedProject.getGeneralFund());

            // Статус (int)
            ps.setInt(11, updatedProject.getProjectStatus().getDbValue());

            // Виконавець (Long або NULL)
            if (updatedProject.getResponsibleExecutor() != null && updatedProject.getResponsibleExecutor().getId() > 0) {
                ps.setLong(12, updatedProject.getResponsibleExecutor().getId());
            } else {
                ps.setNull(12, Types.BIGINT);
            }

            // Сума договору
            ps.setBigDecimal(13, updatedProject.getContractPrice());

            // Дата оплати (Date або NULL)
            if (updatedProject.getPaymentTo() != null) {
                ps.setDate(14, Date.valueOf(updatedProject.getPaymentTo()));
            } else {
                ps.setNull(14, Types.DATE);
            }

            // Залишок (розраховується в сервлеті)
            ps.setBigDecimal(15, updatedProject.getRemainingBalance());

            // ID для WHERE
            ps.setLong(16, updatedProject.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Помилка при оновленні проєкту: " + e.getMessage(), e);
        }
    }
}
