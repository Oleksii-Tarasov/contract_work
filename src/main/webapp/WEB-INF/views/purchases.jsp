<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@include file="/WEB-INF/views/header.jspf" %>
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
                                '${fn:escapeXml(project2210.projectStatus.displayName)}',
                                '${project2210.responsibleExecutor != null ? project2210.responsibleExecutor.id : ""}')">
                        <c:out value="${project2210.dkCode}"/> - <c:out value="${project2210.nameProject}"/>
                    </td>
                    <td><c:out value="${project2210.unitOfMeasure}"/></td>
                    <td><fmt:formatNumber value="${project2210.quantity}" pattern="#,##0"/></td>
                    <td><fmt:formatNumber value="${project2210.price}" pattern="#,##0.00"/></td>
                    <td><fmt:formatNumber value="${project2210.totalPrice}" pattern="#,##0.00"/></td>
                    <td class="${project2210.projectStatus.cssClass}">
                        <fmt:formatNumber value="${project2210.contractPrice}" pattern="#,##0.00"/>
                    </td>
                    <td></td>
                    <td></td>
                    <td>
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
                <th></th>
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
                        <p><strong>Обґрунтування:</strong></p>
<%--                        <p id="justificationText" class="mb-3 text-muted"></p>--%>
                        <textarea
                                id="justificationText"
                                class="form-control mb-3"
                                rows="5">
                        </textarea>


                        <p><strong>Відповідальний виконавець:</strong>
                            <select id="executorSelectModal" class="user-select"></select>
                        </p>
                    </div>
                    <div class="col-6">
                        <p><strong>Статус закупівлі:</strong>
                            <span id="projectStatus" class="mb-3 text-muted"></span>
                        </p>
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

<%@include file="/WEB-INF/views/footer.jspf" %>

<script>
    document.addEventListener("DOMContentLoaded", function() {

        const actionModalEl = document.getElementById('actionModal');
        const actionModal = new bootstrap.Modal(actionModalEl);
        const modalTitle = document.getElementById("actionModalLabel");
        const actionBody = document.getElementById("actionBody");
        const confirmDeleteBody = document.getElementById("confirmDeleteBody");
        const editButton = document.getElementById("editButton");
        const executorSelectModal = document.getElementById("executorSelectModal");
        const projectNameConfirmPlaceholder = document.getElementById("projectNameConfirmPlaceholder");
        const confirmDeleteButton = document.getElementById("confirmDeleteButton");

        let currentProjectId = null;
        let currentProjectName = '';
        let executorWasChanged = false;

        let originalJustification = "";
        let justificationWasChanged = false;

        window.openActionModal = function(projectId, projectName, justification, projectStatus, executorId) {
            currentProjectId = projectId;
            currentProjectName = projectName;

            actionBody.classList.remove('d-none');
            confirmDeleteBody.classList.add('d-none');

            modalTitle.textContent = projectName;
            const justificationField = document.getElementById('justificationText');
            justificationField.value = justification || "";
            originalJustification = justification || "";
            justificationWasChanged = false;

            document.getElementById('projectStatus').textContent = projectStatus;

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
            (function() {
                const opt = document.createElement("option");
                opt.value = "${u.id}";
                opt.textContent = "${fn:escapeXml(u.shortName)}";
                select.appendChild(opt);
            })();
            </c:forEach>

            // --- 4. Питаємо сервер, хто зараз виконавець і встановлюємо його (fallback: executorId) ---
            fetch("/contractwork/executor?projectId=" + projectId)
                .then(response => {
                    if (!response.ok) throw new Error("network");
                    return response.json();
                })
                .then(data => {
                    const serverExecutorId = data && data.executorId ? String(data.executorId) : "";
                    const desired = serverExecutorId || (executorId && executorId !== "null" ? String(executorId) : "");

                    if (desired !== "") {
                        select.value = desired;
                    } else {
                        select.value = "";
                    }

                    // fallback — якщо .value не спрацювало
                    if (String(select.value) !== String(desired)) {
                        let found = false;
                        for (let i = 0; i < select.options.length; i++) {
                            if (String(select.options[i].value).trim() === String(desired).trim()) {
                                select.options[i].selected = true;
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            const empty = Array.from(select.options).find(o => String(o.value).trim() === "");
                            if (empty) empty.selected = true;
                            else select.selectedIndex = 0;
                        }
                    }
                })
                .catch(err => {
                    console.warn("fetch executor failed:", err);
                    const fallback = executorId && executorId !== "null" ? String(executorId) : "";
                    if (fallback !== "") {
                        select.value = fallback;
                        if (String(select.value) !== String(fallback)) {
                            let found = false;
                            for (let i = 0; i < select.options.length; i++) {
                                if (String(select.options[i].value).trim() === String(fallback).trim()) {
                                    select.options[i].selected = true;
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                const empty = Array.from(select.options).find(o => String(o.value).trim() === "");
                                if (empty) empty.selected = true;
                            }
                        }
                    } else {
                        const empty = Array.from(select.options).find(o => String(o.value).trim() === "");
                        if (empty) empty.selected = true;
                    }
                });

            editButton.href = "${pageContext.request.contextPath}/update-project?id=" + projectId;

            actionModal.show();
        };

        window.cancelDelete = function() {
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
                    headers: { "Content-Type": "application/x-www-form-urlencoded" },
                    body: "projectId=" + currentProjectId + "&justification=" + encodeURIComponent(newJustification)
                })
                    .then(() => {
                        location.reload();
                    });
            }
            else if (executorWasChanged) {
                location.reload();
            }
            currentProjectId = null;
            currentProjectName = '';
        });

        // Зміна виконавця
        executorSelectModal.addEventListener("change", function() {
            const projectId = currentProjectId;
            const userId = this.value || "";
            fetch("/contractwork/executor", {
                method: "POST",
                headers: {"Content-Type":"application/x-www-form-urlencoded"},
                body: "projectId=" + projectId + "&userId=" + userId
            }).then(()=> { executorWasChanged = true; localStorage.setItem("updatedExecutorProjectId", projectId); });
        });

        // Зміна обґрунтування
        document.getElementById("justificationText").addEventListener("input", function () {
            justificationWasChanged = (this.value.trim() !== originalJustification.trim());
        });


        // === СКРОЛ/ПІДСВІТКА ===
        function centerAndHighlight(row) {
            if (!row) return;
            // обчислюємо top для центрованого показу
            const rect = row.getBoundingClientRect();
            const absoluteTop = window.scrollY + rect.top;
            const targetTop = Math.max(0, Math.floor(absoluteTop - (window.innerHeight - rect.height) / 2));
            window.scrollTo({ top: targetTop, behavior: "smooth" });
            row.classList.add("highlight-edited");
            setTimeout(() => row.classList.remove("highlight-edited"), 5000);
        }

        // --- 1) Відновлення позиції скролу (тільки якщо немає hash і немає updatedId) ---
        const savedScroll = localStorage.getItem("scrollPositionEstimate");
        const updatedId = localStorage.getItem("updatedExecutorProjectId");
        const updatedJustId = localStorage.getItem("updatedJustificationProjectId");


        const hasHash = !!window.location.hash;

        if (!hasHash && !updatedId && savedScroll !== null) {
            window.scrollTo({ top: parseInt(savedScroll, 10) || 0, behavior: "instant" });
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

        if (savedScroll !== null) {
            localStorage.removeItem("scrollPositionEstimate");
        }

        // Збереження позиції скролу перед переходом
        window.addEventListener("beforeunload", () => {
            localStorage.setItem("scrollPositionEstimate", window.scrollY);
        });
    });
</script>
