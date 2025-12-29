package ua.gov.court.supreme.contractwork.servlet.purchases;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ua.gov.court.supreme.contractwork.enums.ProjectStatus;
import ua.gov.court.supreme.contractwork.servlet.BaseWorkServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@WebServlet("/purchases/update-project-fields")
public class UpdatePurchasesFieldsServlet extends BaseWorkServlet {
    private final ObjectMapper mapper;

    public UpdatePurchasesFieldsServlet() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonNode node = mapper.readTree(req.getInputStream());

        long projectId = node.get("id").asLong();

        // 1. Обґрунтування
        if (node.has("justification")) {
            JsonNode j = node.get("justification");
            workInspector.updateJustification(
                    projectId,
                    j.isNull() ? null : j.asText()
            );
        }

        // 2. Статус закупівлі
        if (node.has("projectStatus")) {
            JsonNode s = node.get("projectStatus");
            if (!s.isNull()) {
                workInspector.updateProjectStatus(projectId, ProjectStatus.fromInt(s.asInt()));
            }
        }

        // 3. Відповідальний виконавець
        if (node.has("executorId")) {
            JsonNode e = node.get("executorId");
            workInspector.updateProjectExecutor(
                    projectId,
                    e.isNull() ? null : e.asLong()
            );
        }

        // 4. Сума договору
        if (node.has("contractPrice")) {
            JsonNode p = node.get("contractPrice");
            if (!p.isNull()) {
                BigDecimal contractPrice = BigDecimal.valueOf(p.asDouble());
                workInspector.updateContractPrice(
                        projectId,
                        contractPrice
                );
            }
        }

        // 5. Оплата до
        if (node.has("paymentTo")) {
            JsonNode d = node.get("paymentTo");

            if (d.isNull()) {
                workInspector.updatePaymentDueDate(projectId, null);
            } else {
                LocalDate date = LocalDate.parse(d.asText());
                workInspector.updatePaymentDueDate(projectId, date);
            }
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
