<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@include file="/WEB-INF/views/layout/header.jspf" %>
<style>
    <%@include file="/css/purchases.css" %>
</style>

<div class="container-sm mt-4">
    <div class="bg-white p-4 rounded shadow">
        <table class="table table-striped-columns">
            <tr>
                <th>№</th>
                <th>Назва предмета закупівлі</th>
                <th>Одиниця виміру</th>
                <th>Кількість</th>
                <th>Ціна за одиницю</th>
                <th>Сума по кошторису</th>
                <th>Сума Договору</th>

                <th data-sortable="true">Залишок</th>
                <th>Оплата до</th>
                <th data-sortable="true">Відповідальний виконавець</th>
            </tr>

            <tr class="black-borders">
                <th>2210</th>
                <th colspan="9" style="text-align: left">Предмети, матеріали, обладнання та інвентар, у т. ч. м'який
                    інвентар та обмундирування
                </th>
            </tr>
            <c:forEach var="project2210" items="${projectsForPurchases2210}" varStatus="projectStatus">
                <tr id="project-${project2210.id}">
                    <td><c:out value="${projectStatus.count}"/></td>
                    <td class="${project2210.informatization ? 'informatization-row' : ''}"
                        onclick="openActionModal(
                            ${project2210.id},
                                '${fn:escapeXml(project2210.nameProject)}',
                                '${fn:escapeXml(project2210.justification)}',
                                '${project2210.contractPrice != null ? project2210.contractPrice : 'null'}',
                            ${project2210.projectStatus != null ? project2210.projectStatus.dbValue : 'null'},
                                '${project2210.paymentTo != null ? fn:substring(project2210.paymentTo, 0, 10) : ''}'
                                )">
                        <c:out value="${project2210.dkCode}"/> - <c:out value="${project2210.nameProject}"/>
                    </td>
                    <td class="${project2210.projectStatus.cssClass}"><c:out value="${project2210.unitOfMeasure}"/></td>
                    <td class="${project2210.projectStatus.cssClass}"><fmt:formatNumber value="${project2210.quantity}"
                                                                                        pattern="#,##0"/></td>
                    <td class="${project2210.projectStatus.cssClass}"><fmt:formatNumber value="${project2210.price}"
                                                                                        pattern="#,##0.00"/></td>
                    <td class="${project2210.projectStatus.cssClass}"><fmt:formatNumber
                            value="${project2210.totalPrice}" pattern="#,##0.00"/></td>
                    <td class="${project2210.projectStatus.cssClass}">
                        <fmt:formatNumber value="${project2210.contractPrice}" pattern="#,##0.00"/>
                    </td>
                    <td><fmt:formatNumber value="${project2210.remainingBalance}" pattern="#,##0.00"/></td>
                    <td><c:out value="${project2210.paymentTo}"/></td>
                    <td class="${project2210.projectStatus.cssClass}">
                        <c:out value="${project2210.responsibleExecutor != null ? project2210.responsibleExecutor.shortName : ''}"/>
                    </td>
                </tr>
            </c:forEach>

            <tr>
                <th></th>
                <th style="text-align: right">ВСЬОГО ЗА 2210</th>
                <th></th>
                <th><fmt:formatNumber value="${totalQuantity2210}" pattern="#,##0"/></th>
                <th></th>
                <th><fmt:formatNumber value="${totalPriceSum2210}" pattern="#,##0.00"/></th>
                <th></th>
                <th><fmt:formatNumber value="${totalRemainingBalance2210}" pattern="#,##0.00"/></th>
                <th></th>
                <th></th>
            </tr>

            <tr class="black-borders">
                <th>2240</th>
                <th colspan="9" style="text-align: left">Оплата послуг (крім комунальних)
                </th>
            </tr>
            <c:forEach var="project2240" items="${projectsForPurchases2240}" varStatus="projectStatus">
                <tr id="project-${project2240.id}">
                    <td><c:out value="${projectStatus.count}"/></td>
                    <td class="${project2240.informatization ? 'informatization-row' : ''}"
                        onclick="openActionModal(
                            ${project2240.id},
                                '${fn:escapeXml(project2240.nameProject)}',
                                '${fn:escapeXml(project2240.justification)}',
                                '${project2240.contractPrice != null ? project2240.contractPrice : 'null'}',
                            ${project2240.projectStatus != null ? project2240.projectStatus.dbValue : 'null'},
                                '${project2240.paymentTo != null ? fn:substring(project2240.paymentTo, 0, 10) : ''}'
                                )">
                        <c:out value="${project2240.dkCode}"/> - <c:out value="${project2240.nameProject}"/>
                    </td>
                    <td class="${project2240.projectStatus.cssClass}"><c:out value="${project2240.unitOfMeasure}"/></td>
                    <td class="${project2240.projectStatus.cssClass}"><fmt:formatNumber value="${project2240.quantity}"
                                                                                        pattern="#,##0"/></td>
                    <td class="${project2240.projectStatus.cssClass}"><fmt:formatNumber value="${project2240.price}"
                                                                                        pattern="#,##0.00"/></td>
                    <td class="${project2240.projectStatus.cssClass}"><fmt:formatNumber
                            value="${project2240.totalPrice}" pattern="#,##0.00"/></td>
                    <td class="${project2240.projectStatus.cssClass}">
                        <fmt:formatNumber value="${project2240.contractPrice}" pattern="#,##0.00"/>
                    </td>
                    <td><fmt:formatNumber value="${project2240.remainingBalance}" pattern="#,##0.00"/></td>
                    <td><c:out value="${project2240.paymentTo}"/></td>
                    <td class="${project2240.projectStatus.cssClass}">
                        <c:out value="${project2240.responsibleExecutor != null ? project2240.responsibleExecutor.shortName : ''}"/>
                    </td>
                </tr>
            </c:forEach>

            <tr>
                <th></th>
                <th style="text-align: right">ВСЬОГО ЗА 2240</th>
                <th></th>
                <th><fmt:formatNumber value="${totalQuantity2240}" pattern="#,##0"/></th>
                <th></th>
                <th><fmt:formatNumber value="${totalPriceSum2240}" pattern="#,##0.00"/></th>
                <th></th>
                <th><fmt:formatNumber value="${totalRemainingBalance2240}" pattern="#,##0.00"/></th>
                <th></th>
                <th></th>
            </tr>

            <tr class="black-borders">
                <th>3110</th>
                <th colspan="9" style="text-align: left">Придбання обладнання і предметів довгострокового користування
                </th>
            </tr>
            <c:forEach var="project3110" items="${projectsForPurchases3110}" varStatus="projectStatus">
                <tr id="project-${project3110.id}">
                    <td><c:out value="${projectStatus.count}"/></td>
                    <td class="${project3110.informatization ? 'informatization-row' : ''}"
                        onclick="openActionModal(
                            ${project3110.id},
                                '${fn:escapeXml(project3110.nameProject)}',
                                '${fn:escapeXml(project3110.justification)}',
                                '${project3110.contractPrice != null ? project3110.contractPrice : 'null'}',
                            ${project3110.projectStatus != null ? project3110.projectStatus.dbValue : 'null'},
                                '${project3110.paymentTo != null ? fn:substring(project3110.paymentTo, 0, 10) : ''}'
                                )">
                        <c:out value="${project3110.dkCode}"/> - <c:out value="${project3110.nameProject}"/>
                    </td>
                    <td class="${project3110.projectStatus.cssClass}"><c:out value="${project3110.unitOfMeasure}"/></td>
                    <td class="${project3110.projectStatus.cssClass}"><fmt:formatNumber value="${project3110.quantity}"
                                                                                        pattern="#,##0"/></td>
                    <td class="${project3110.projectStatus.cssClass}"><fmt:formatNumber value="${project3110.price}"
                                                                                        pattern="#,##0.00"/></td>
                    <td class="${project3110.projectStatus.cssClass}"><fmt:formatNumber
                            value="${project3110.totalPrice}" pattern="#,##0.00"/></td>
                    <td class="${project3110.projectStatus.cssClass}">
                        <fmt:formatNumber value="${project3110.contractPrice}" pattern="#,##0.00"/>
                    </td>
                    <td><fmt:formatNumber value="${project3110.remainingBalance}" pattern="#,##0.00"/></td>
                    <td><c:out value="${project3110.paymentTo}"/></td>
                    <td class="${project3110.projectStatus.cssClass}">
                        <c:out value="${project3110.responsibleExecutor != null ? project3110.responsibleExecutor.shortName : ''}"/>
                    </td>
                </tr>
            </c:forEach>

            <tr>
                <th></th>
                <th style="text-align: right">ВСЬОГО ЗА 3110</th>
                <th></th>
                <th><fmt:formatNumber value="${totalQuantity3110}" pattern="#,##0"/></th>
                <th></th>
                <th><fmt:formatNumber value="${totalPriceSum3110}" pattern="#,##0.00"/></th>
                <th></th>
                <th><fmt:formatNumber value="${totalRemainingBalance3110}" pattern="#,##0.00"/></th>
                <th></th>
                <th></th>
            </tr>
        </table>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="actionModal" tabindex="-1" aria-labelledby="actionModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-xl">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="actionModalLabel"></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body" id="actionBody">
                <div class="row">
                    <div class="col-6 border-end">
                        <strong>Обґрунтування/Примітки:</strong>
                        <textarea
                                id="justificationText"
                                class="form-control mb-3"
                                rows="5">
                        </textarea>

                        <p><strong>Відповідальний виконавець:</strong>
                            <select id="executorSelectModal" class="executor-select-clean"></select>
                        </p>
                    </div>
                    <div class="col-6">
                        <strong>Статус закупівлі:</strong>
                        <select id="projectStatusSelect" class="executor-select-clean"></select>

                        <div class="row g-3" style="max-width: 420px">

                            <div class="row g-3" style="max-width: 420px">

                                <!-- Заголовки -->
                                <div class="col-6 mb-1">
                                    <strong>Сума Договору:</strong>
                                </div>
                                <div class="col-6 mb-1">
                                    <strong>Оплата до:</strong>
                                </div>

                                <!-- Поля -->
                                <div class="col-6 mt-0">
                                    <input
                                            id="contractPriceInput"
                                            type="text"
                                            class="form-control"
                                            style="max-width: 180px"
                                    />
                                </div>
                                <div class="col-6 mt-0">
                                    <div class="input-group" style="max-width: 180px">
                                        <span class="input-group-text">
                                            <i class="fa-solid fa-calendar-days"></i>
                                        </span>
                                        <input
                                                type="date"
                                                id="paymentDueDateInput"
                                                class="form-control"
                                        />
                                    </div>
                                </div>
                            </div>

                            <p><strong>Документи:</strong></p>
                        </div>
                    </div>

                    <p class="mt-3">
                        <a id="editButton" href="#" class="btn btn-primary me-2">Редагувати</a>
                        <button type="button" class="btn btn-danger" onclick="confirmDelete()">Видалити</button>
                    </p>
                </div>

                <div class="modal-body d-none" id="confirmDeleteBody">
                    <p>Ви впевнені, що хочете видалити проєкт <strong id="projectNameConfirmPlaceholder"></strong>?</p>
                    <a id="confirmDeleteButton" href="#" class="btn btn-danger me-2">Так, видалити</a>
                    <button type="button" class="btn btn-secondary" onclick="cancelDelete()">Скасувати</button>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/views/layout/footer.jspf" %>

<script>
    document.addEventListener("DOMContentLoaded", function () {

        const actionModalEl = document.getElementById('actionModal');
        const actionModal = new bootstrap.Modal(actionModalEl);
        const modalTitle = document.getElementById("actionModalLabel");
        const actionBody = document.getElementById("actionBody");
        const confirmDeleteBody = document.getElementById("confirmDeleteBody");
        const editButton = document.getElementById("editButton");
        const executorSelectModal = document.getElementById("executorSelectModal");

        const projectStatusSelect = document.getElementById("projectStatusSelect")

        const projectNameConfirmPlaceholder = document.getElementById("projectNameConfirmPlaceholder");
        const confirmDeleteButton = document.getElementById("confirmDeleteButton");

        let currentProjectId = null;
        let currentProjectName = '';
        let executorWasChanged = false;
        let projectStatusChanged = false;

        let originalJustification = "";
        let justificationWasChanged = false;

        let originalContractPrice = "";
        let contractPriceWasChanged = false;

        let originalPaymentDueDate = "";
        let paymentDueDateWasChanged = false;

        window.openActionModal = function (projectId, projectName, justification, contractPrice, projectStatus, paymentDueDate) {
            currentProjectId = projectId;
            currentProjectName = projectName;

            actionBody.classList.remove('d-none');
            confirmDeleteBody.classList.add('d-none');

            modalTitle.textContent = projectName;
            const justificationField = document.getElementById('justificationText');
            justificationField.value = justification || "";
            originalJustification = justification || "";
            justificationWasChanged = false;

            // === Статус закупівлі ===
            const statusSelect = document.getElementById("projectStatusSelect");
            statusSelect.innerHTML = "";

            // Заповнення ENUM статусів (генерується на стороні JSP)
            <c:forEach var="st" items="${projectStatuses}">
            (function () {
                const opt = document.createElement("option");
                opt.value = "${st.dbValue}";
                opt.textContent = "${fn:escapeXml(st.displayName)}";
                statusSelect.appendChild(opt);
            })();
            </c:forEach>

            // Встановлюємо поточний статус
            statusSelect.value = projectStatus !== "null" ? projectStatus : "";
            if (statusSelect.value !== projectStatus) {
                for (let i = 0; i < statusSelect.options.length; i++) {
                    if (String(statusSelect.options[i].value) === String(projectStatus)) {
                        statusSelect.options[i].selected = true;
                        break;
                    }
                }
            }

            // === Відплвідальний виконавець ===
            // --- 1. Селектор у модалці та очищаємо його ---
            const select = document.getElementById("executorSelectModal");
            select.innerHTML = "";

            // --- 2. Опція "не вибрано" ---
            const emptyOpt = document.createElement("option");
            emptyOpt.value = "";
            emptyOpt.textContent = "-- не вибрано --";
            select.appendChild(emptyOpt);

            // --- 3. ВСІ опції користувачів (вставлено JSP-генерацію опцій) ---
            <%-- JSP: згенерувати опції користувачів --%>
            <c:forEach var="u" items="${users}">
            (function () {
                const opt = document.createElement("option");
                opt.value = "${u.id}";
                opt.textContent = "${fn:escapeXml(u.shortName)}";
                select.appendChild(opt);
            })();
            </c:forEach>

            // --- 4. Отримуємо виконавця з сервера і встановлюємо у select ---
            fetch("/contractwork/executor?projectId=" + projectId)
                .then(r => r.json())
                .then(data => {
                    const serverId = data && data.executorId != null ? String(data.executorId) : "";

                    // встановлюємо отримане або пусте значення
                    select.value = serverId;

                    // якщо select.value НЕ збігається з тим, що ми поставили — ставимо пустий варіант
                    if (select.value !== serverId) {
                        select.value = "";
                    }
                })
                .catch(err => {
                    console.warn("executor fetch failed:", err);
                    select.value = "";
                });

            // === Сума Договору ===
            const priceInput = document.getElementById("contractPriceInput");
            const formattedPrice = formatPriceForInput(contractPrice);

            priceInput.value = formattedPrice;
            originalContractPrice = formattedPrice;
            contractPriceWasChanged = false;

            // === Оплата до ===
            const paymentInput = document.getElementById("paymentDueDateInput");
            paymentInput.value = paymentDueDate || "";
            originalPaymentDueDate = paymentInput.value;
            paymentDueDateWasChanged = false;

            //
            editButton.href = "${pageContext.request.contextPath}/purchases/update-project?id=" + projectId;

            actionModal.show();
        };

        window.cancelDelete = function () {
            actionBody.classList.remove('d-none');
            confirmDeleteBody.classList.add('d-none');
            modalTitle.textContent = currentProjectName;
        };

        actionModalEl.addEventListener('hidden.bs.modal', () => {
            if (justificationWasChanged && currentProjectId !== null) {

                const newJustification = document.getElementById("justificationText").value;

                // ЗБЕРІГАЄМО ID ДО fetch — гарантовано
                localStorage.setItem("updatedJustificationProjectId", currentProjectId);

                fetch("/contractwork/update-justification", {
                    method: "POST",
                    headers: {"Content-Type": "application/x-www-form-urlencoded"},
                    body: "projectId=" + currentProjectId + "&justification=" + encodeURIComponent(newJustification)
                })
                    .then(() => {
                        location.reload();
                    });
            }

            if (contractPriceWasChanged && currentProjectId !== null) {
                const newPrice = document.getElementById("contractPriceInput").value
                    .replace(/\s/g, '')
                    .replace(",", ".");

                localStorage.setItem("updatedContractPriceProjectId", currentProjectId);

                fetch("/contractwork/update-contract-price", {
                    method: "POST",
                    headers: {"Content-Type": "application/x-www-form-urlencoded"},
                    body: "projectId=" + currentProjectId + "&contractPrice=" + encodeURIComponent(newPrice)
                }).then(() => {
                    location.reload();
                });
            }

            if (executorWasChanged) {
                location.reload();
            }

            if (projectStatusChanged) {
                location.reload();
            }

            if (paymentDueDateWasChanged && currentProjectId !== null) {

                const newDateRaw = document.getElementById("paymentDueDateInput").value;
                const newDate = newDateRaw === "" ? null : newDateRaw;

                localStorage.setItem("updatedPaymentDueDateProjectId", currentProjectId);

                fetch("/contractwork/update-payment-date", {
                    method: "POST",
                    headers: {"Content-Type": "application/x-www-form-urlencoded"},
                    body:
                        "projectId=" + currentProjectId +
                        "&paymentDueDate=" + encodeURIComponent(newDate ?? "")
                }).then(() => {
                    location.reload();
                });
            }

            currentProjectId = null;
            currentProjectName = '';
        });

        // Зміна виконавця
        executorSelectModal.addEventListener("change", function () {
            const projectId = currentProjectId;
            const userId = this.value || "";
            fetch("/contractwork/executor", {
                method: "POST",
                headers: {"Content-Type": "application/x-www-form-urlencoded"},
                body: "projectId=" + projectId + "&userId=" + userId
            }).then(() => {
                executorWasChanged = true;
                localStorage.setItem("updatedExecutorProjectId", projectId);
            });
        });

        // Зміна статусу закупівлі
        projectStatusSelect.addEventListener("change", function () {
            const projectId = currentProjectId;
            const statusValue = this.value || "";

            fetch("/contractwork/update-status", {
                method: "POST",
                headers: {"Content-Type": "application/x-www-form-urlencoded"},
                body: "projectId=" + projectId + "&status=" + statusValue
            }).then(() => {
                projectStatusChanged = true;
                localStorage.setItem("updatedStatusProjectId", projectId);
            });
        });

        // Зміна обґрунтування
        document.getElementById("justificationText").addEventListener("input", function () {
            justificationWasChanged = (this.value.trim() !== originalJustification.trim());
        });

        // Зміна суми Договору
        document.getElementById("contractPriceInput").addEventListener("input", function () {
            contractPriceWasChanged = (this.value.trim() !== originalContractPrice.trim());
        });

        // Зміна "Оплата до"
        document.getElementById("paymentDueDateInput")
            .addEventListener("input", function () {
                paymentDueDateWasChanged = (this.value !== originalPaymentDueDate);
            });


        // === СКРОЛ/ПІДСВІТКА ===
        function centerAndHighlight(row) {
            if (!row) return;

            const rect = row.getBoundingClientRect();
            const absoluteTop = window.scrollY + rect.top;
            const targetTop = Math.max(0, Math.floor(
                absoluteTop - (window.innerHeight - rect.height) / 2
            ));

            window.scrollTo({top: targetTop, behavior: "smooth"});

            // 🔹 тільки перша клітинка (№)
            const firstCell = row.querySelector("td:first-child");

            if (firstCell) {
                firstCell.classList.add("highlight-edited");
                setTimeout(() => {
                    firstCell.classList.remove("highlight-edited");
                }, 5000);
            }
        }

        // --- 1) Відновлення позиції скролу (тільки якщо немає hash і немає updatedId) ---
        const savedScroll = localStorage.getItem("scrollPositionEstimate");
        const updatedId = localStorage.getItem("updatedExecutorProjectId");
        const updatedJustId = localStorage.getItem("updatedJustificationProjectId");

        const hasHash = !!window.location.hash;

        if (!hasHash && !updatedId && savedScroll !== null) {
            window.scrollTo({top: parseInt(savedScroll, 10) || 0, behavior: "instant"});
            localStorage.removeItem("scrollPositionEstimate");
        }

        // --- 2) Підсвітка за хешом або за оновленням (має пріоритет над відновленням) ---
        if (hasHash) {
            const id = window.location.hash.slice(1);
            const row = document.getElementById(id);
            // дати браузеру закінчити layout перш ніж скролити
            requestAnimationFrame(() => centerAndHighlight(row));
        } else if (updatedId) {
            const rid = "project-" + updatedId;
            const row = document.getElementById(rid);
            requestAnimationFrame(() => {
                centerAndHighlight(row);
                localStorage.removeItem("updatedExecutorProjectId");
            });
        } else if (updatedJustId) {
            const rid = "project-" + updatedJustId;
            const row = document.getElementById(rid);
            requestAnimationFrame(() => {
                centerAndHighlight(row);
                localStorage.removeItem("updatedJustificationProjectId");
            });
        }
        // Підсвітка після оновлення проєкту
        const updatedStatusId = localStorage.getItem("updatedStatusProjectId");
        if (updatedStatusId) {
            const rid = "project-" + updatedStatusId;
            const row = document.getElementById(rid);
            requestAnimationFrame(() => {
                centerAndHighlight(row);
                localStorage.removeItem("updatedStatusProjectId");
            });
        }

        // Підсвітка після оновлення ціни
        const updatedContractPriceId = localStorage.getItem("updatedContractPriceProjectId");
        if (updatedContractPriceId) {
            const rid = "project-" + updatedContractPriceId;
            const row = document.getElementById(rid);
            requestAnimationFrame(() => {
                centerAndHighlight(row);
                localStorage.removeItem("updatedContractPriceProjectId");
            });
        }

        // Підсвітка після оновлення дати
        const updatedPaymentDateId = localStorage.getItem("updatedPaymentDueDateProjectId");
        if (updatedPaymentDateId) {
            const rid = "project-" + updatedPaymentDateId;
            const row = document.getElementById(rid);
            requestAnimationFrame(() => {
                centerAndHighlight(row);
                localStorage.removeItem("updatedPaymentDueDateProjectId");
            });
        }

        if (savedScroll !== null) {
            localStorage.removeItem("scrollPositionEstimate");
        }

        // Збереження позиції скролу перед переходом
        window.addEventListener("beforeunload", () => {
            localStorage.setItem("scrollPositionEstimate", window.scrollY);
        });
    });

    function formatPriceForInput(value) {
        if (value === null || value === undefined || value === "" || value === "null") {
            return "";
        }

        const num = Number(value);

        if (isNaN(num)) return "";

        return num.toLocaleString("uk-UA", {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
        });
    }
</script>
