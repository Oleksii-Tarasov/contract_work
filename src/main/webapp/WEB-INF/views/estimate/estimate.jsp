<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/layout/header.jspf" %>

<style>
    <%@include file="/css/estimate.css" %>
</style>

<div class="container-sm mt-4 custom-width">
    <div class="bg-white p-4 rounded shadow">
        <table class="table table-striped-columns">
            <tr>
                <th>№ з/п</th>
                <th>Найменування</th>
                <th>Одиниця виміру</th>
                <th>Кількість</th>
                <th>Ціна, грн</th>
                <th>Сума, грн</th>
                <th>ЗФ</th>
                <th>СФ</th>
                <th>Розрахунок та обґрунтування щодо необхідності зазначеного придбання</th>
            </tr>
            <tr class="black-borders">
                <th>2210</th>
                <th colspan="8" style="text-align: left">Предмети, матеріали, обладнання та інвентар, у т. ч. м'який
                    інвентар та обмундирування
                </th>
            </tr>
            <c:forEach var="project2210" items="${projectsForEstimate2210}" varStatus="projectStatus">
                <tr id="project-${project2210.id}">
                    <td><c:out value="${projectStatus.count}"/></td>
                        <%-- informatization == true --%>
                    <td class="${project2210.informatization ? 'informatization-row' : ''}"
                        onclick="openActionModal(${project2210.id}, '${project2210.projectName}')">
                        <c:out value="${project2210.dkCode}"/> - <c:out value="${project2210.projectName}"/>
                    </td>
                    <td><c:out value="${project2210.unitOfMeasure}"/></td>
                    <td><fmt:formatNumber value="${project2210.quantity}" pattern="#,##0"/></td>
                    <td><fmt:formatNumber value="${project2210.price}" pattern="#,##0.00"/></td>
                    <td><fmt:formatNumber value="${project2210.totalPrice}" pattern="#,##0.00"/></td>
                    <td><fmt:formatNumber value="${project2210.generalFund}" pattern="#,##0.00"/></td>
                    <td><fmt:formatNumber value="${project2210.specialFund}" pattern="#,##0.00"/></td>
                    <td><c:out value="${project2210.justification}"/></td>
                </tr>
            </c:forEach>
            <tr>
                <th></th>
                <th style="text-align: right">ВСЬОГО ЗА 2210</th>
                <th></th>
                <th><fmt:formatNumber value="${totalQuantity2210}" pattern="#,##0"/></th>
                <th></th>
                <th><fmt:formatNumber value="${totalPriceSum2210}" pattern="#,##0.00"/></th>
                <th><fmt:formatNumber value="${totalGeneralFund2210}" pattern="#,##0.00"/></th>
                <th><fmt:formatNumber value="${totalSpecialFund2210}" pattern="#,##0.00"/></th>
                <th></th>
            </tr>
            <tr class="black-borders">
                <th>2240</th>
                <th colspan="8" style="text-align: left">Оплата послуг (крім комунальних)</th>
            </tr>
            <c:forEach var="project2240" items="${projectsForEstimate2240}" varStatus="projectStatus">
                <tr id="project-${project2240.id}">
                    <td><c:out value="${projectStatus.count}"/></td>
                    <td class="${project2240.informatization ? 'informatization-row' : ''}"
                        onclick="openActionModal(${project2240.id}, '${project2240.projectName}')">
                        <c:out value="${project2240.dkCode}"/> - <c:out value="${project2240.projectName}"/>
                    </td>
                    <td><c:out value="${project2240.unitOfMeasure}"/></td>
                    <td><fmt:formatNumber value="${project2240.quantity}" pattern="#,##0"/></td>
                    <td><fmt:formatNumber value="${project2240.price}" pattern="#,##0.00"/></td>
                    <td><fmt:formatNumber value="${project2240.totalPrice}" pattern="#,##0.00"/></td>
                    <td><fmt:formatNumber value="${project2240.generalFund}" pattern="#,##0.00"/></td>
                    <td><fmt:formatNumber value="${project2240.specialFund}" pattern="#,##0.00"/></td>
                    <td><c:out value="${project2240.justification}"/></td>
                </tr>
            </c:forEach>
            <tr>
                <th></th>
                <th style="text-align: right">ВСЬОГО ЗА 2240</th>
                <th></th>
                <th><fmt:formatNumber value="${totalQuantity2240}" pattern="#,##0"/></th>
                <th></th>
                <th><fmt:formatNumber value="${totalPriceSum2240}" pattern="#,##0.00"/></th>
                <th><fmt:formatNumber value="${totalGeneralFund2240}" pattern="#,##0.00"/></th>
                <th><fmt:formatNumber value="${totalSpecialFund2240}" pattern="#,##0.00"/></th>
                <th></th>
            </tr>
            <tr class="black-borders">
                <th>3110</th>
                <th colspan="8" style="text-align: left">Придбання обладнання і предметів довгострокового користування
                </th>
            </tr>
            <c:forEach var="project3110" items="${projectsForEstimate3110}" varStatus="projectStatus">
                <tr id="project-${project3110.id}">
                    <td><c:out value="${projectStatus.count}"/></td>
                    <td class="${project3110.informatization ? 'informatization-row' : ''}"
                        onclick="openActionModal(${project3110.id}, '${project3110.projectName}')">
                        <c:out value="${project3110.dkCode}"/> - <c:out value="${project3110.projectName}"/>
                    </td>
                    <td><c:out value="${project3110.unitOfMeasure}"/></td>
                    <td><fmt:formatNumber value="${project3110.quantity}" pattern="#,##0"/></td>
                    <td><fmt:formatNumber value="${project3110.price}" pattern="#,##0.00"/></td>
                    <td><fmt:formatNumber value="${project3110.totalPrice}" pattern="#,##0.00"/></td>
                    <td><fmt:formatNumber value="${project3110.generalFund}" pattern="#,##0.00"/></td>
                    <td><fmt:formatNumber value="${project3110.specialFund}" pattern="#,##0.00"/></td>
                    <td><c:out value="${project3110.justification}"/></td>
                </tr>
            </c:forEach>
            <tr>
                <th></th>
                <th style="text-align: right">ВСЬОГО ЗА 3110</th>
                <th></th>
                <th><fmt:formatNumber value="${totalQuantity3110}" pattern="#,##0"/></th>
                <th></th>
                <th><fmt:formatNumber value="${totalPriceSum3110}" pattern="#,##0.00"/></th>
                <th><fmt:formatNumber value="${totalGeneralFund3110}" pattern="#,##0.00"/></th>
                <th><fmt:formatNumber value="${totalSpecialFund3110}" pattern="#,##0.00"/></th>
                <th></th>
            </tr>
        </table>
    </div>
    <div class="mt-3 mb-4 d-flex justify-content-between align-items-center">
        <a href="${pageContext.request.contextPath}/generate-estimate-excel" class="btn btn-success btn-sm px-2 shadow-sm">
            <i class="fa-solid fa-file-excel me-1"></i>
            Конвертувати в Excel
        </a>
        <a href="${pageContext.request.contextPath}/generate-purchases-table" class="btn btn-primary btn-sm px-2 shadow-sm">
            <i class="fa-solid fa-file-excel me-1"></i>
            Створити проєкт закупівель на основі кошторису
        </a>
    </div>
</div>

<!-- Modal Action Window (Редагувати/Видалити) -->
<div class="modal fade" id="actionModal" tabindex="-1" aria-labelledby="actionModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="actionModalLabel">Вибраний проєкт: <strong id="projectNamePlaceholder"></strong></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <!-- Тіло 1: Вибір дії -->
            <div class="modal-body" id="actionBody">
                <p class="mt-3">
                    <a id="editButton" href="#" class="btn btn-primary me-2">Редагувати</a>
                    <!-- Змінюємо onclick на виклик нової функції confirmDelete() -->
                    <button type="button" class="btn btn-danger" onclick="confirmDelete()">Видалити</button>
                </p>
            </div>

            <!-- Тіло 2: Підтвердження видалення (сховане за замовчуванням) -->
            <div class="modal-body d-none" id="confirmDeleteBody">
                <p>Ви впевнені, що хочете видалити проєкт <strong id="projectNameConfirmPlaceholder"></strong>?</p>
                <!-- Кнопка, яка веде на сервлет видалення -->
                <a id="confirmDeleteButton" href="#" class="btn btn-danger me-2">Так, видалити</a>
                <!-- Кнопка скасування, що повертає до першого тіла -->
                <button type="button" class="btn btn-secondary" onclick="cancelDelete()">Скасувати</button>
            </div>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/views/layout/footer.jspf" %>
<%@include file="/WEB-INF/views/layout/tableCommonScripts.jspf" %>

<%--Функціонал модального вікна--%>
<script>
    var actionModalEl = document.getElementById('actionModal');
    var actionModal = new bootstrap.Modal(actionModalEl);

    // Елемент заголовка модального вікна
    var modalTitle = document.getElementById("actionModalLabel");

    // Елементи першого тіла
    var actionBody = document.getElementById("actionBody");
    var editButton = document.getElementById("editButton");

    // Елементи другого тіла (підтвердження)
    var confirmDeleteBody = document.getElementById("confirmDeleteBody");
    var projectNameConfirmPlaceholder = document.getElementById("projectNameConfirmPlaceholder");
    var confirmDeleteButton = document.getElementById("confirmDeleteButton");

    let currentProjectId = null;
    let currentProjectName = '';

    function openActionModal(projectId, projectName) {
        currentProjectId = projectId;
        currentProjectName = projectName;

        // Переконуємося, що завжди показуємо спочатку тіло вибору дії
        actionBody.classList.remove('d-none');
        confirmDeleteBody.classList.add('d-none');

        // ВСТАНОВЛЮЄМО ЗАГОЛОВОК ТУТ:
        modalTitle.textContent = projectName;

        editButton.href = "${pageContext.request.contextPath}/estimate/update-project?id=" + projectId;
        // editButton.href = contextPath +"/update-project?id=" + projectId;

        actionModal.show();
    }

    // Функція переходу до вікна підтвердження
    function confirmDelete() {
        // Ховаємо перше тіло і показуємо друге
        actionBody.classList.add('d-none');
        confirmDeleteBody.classList.remove('d-none');

        // ОНОВЛЮЄМО ЗАГОЛОВОК ДЛЯ ПІДТВЕРДЖЕННЯ
        modalTitle.textContent = 'Підтвердження видалення';
        projectNameConfirmPlaceholder.textContent = currentProjectName;

        confirmDeleteButton.onclick = function (event) {
            event.preventDefault();
            if (!currentProjectId) return;

            fetch("${pageContext.request.contextPath}/estimate/delete-project", {
                // fetch(contextPath + "/delete-project", {
                method: "POST",
                headers: { "Content-Type": "application/x-www-form-urlencoded" },
                body: "id=" + encodeURIComponent(currentProjectId)
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        // === ПЛАВНЕ ВИДАЛЕННЯ РЯДКА ===
                        const row = document.getElementById("project-" + data.deletedId);
                        if (row && row.tagName === "TR") {
                            row.style.transition = "background-color 0.5s ease, opacity 0.5s ease";
                            row.style.backgroundColor = "#f8d7da";
                            row.style.opacity = "0";
                            setTimeout(() => row.remove(), 500);
                        }

                        // Закриваємо модалку
                        const modal = bootstrap.Modal.getInstance(document.getElementById('actionModal'));
                        modal.hide();
                    } else {
                        alert("Помилка: " + (data.message || "Не вдалося видалити проєкт."));
                    }
                })
                .catch(err => {
                    console.error(err);
                    alert("Виникла помилка при видаленні проєкту.");
                });
        };
    }

    // Функція скасування видалення (повернення до першого тіла)
    function cancelDelete() {
        actionBody.classList.remove('d-none');
        confirmDeleteBody.classList.add('d-none');

        // ПОВЕРТАЄМО ПОПЕРЕДНІЙ ЗАГОЛОВОК ПРИ СКАСУВАННІ
        // Використовуємо збережену назву проєкту
        modalTitle.textContent = currentProjectName;
    }

    // Скидання стану модалки при її закритті будь-яким способом (хрестик, клік поза)
    actionModalEl.addEventListener('hidden.bs.modal', function (event) {
        // Ми не викликаємо cancelDelete() тут напряму, щоб уникнути
        // можливих конфліктів життєвого циклу модалки Bootstrap.
        // Достатньо того, що openActionModal() скидає стан при наступному відкритті.
        currentProjectId = null;
        currentProjectName = '';
    });
</script>
