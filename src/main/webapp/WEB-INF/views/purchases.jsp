<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@include file="/WEB-INF/views/header.jspf" %>
<style>
    <%@include file="/css/purchases.css" %>
</style>

<!-- Estimate table output -->
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
                        <%-- informatization == true --%>
                    <td class="${project2210.informatization ? 'informatization-row' : ''}"
                        onclick="openActionModal(${project2210.id}, '${fn:escapeXml(project2210.nameProject)}',
                                '${fn:escapeXml(project2210.justification)}', '${fn:escapeXml(project2210.projectStatus.displayName)}',
                                '${fn:escapeXml(project2210.contractPrice)}')">
                        <c:out value="${project2210.dkCode}"/> - <c:out value="${project2210.nameProject}"/>
                    </td>
                    <td><c:out value="${project2210.unitOfMeasure}"/></td>
                    <td><fmt:formatNumber value="${project2210.quantity}" pattern="#,##0"/></td>
                    <td><fmt:formatNumber value="${project2210.price}" pattern="#,##0.00"/></td>
                    <td><fmt:formatNumber value="${project2210.totalPrice}" pattern="#,##0.00"/></td>
                    <td class="${project2210.projectStatus.cssClass}">
                        <fmt:formatNumber value="${project2210.contractPrice}" pattern="#,##0.00"/></td>
                    <td></td>
                    <td></td>
                    <td><c:out value="${project2210.responsibleExecutor.shortName}"/></td>
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

<!-- Modal Action Window (Редагувати/Видалити) -->
<div class="modal fade" id="actionModal" tabindex="-1" aria-labelledby="actionModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-xl">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="actionModalLabel">Вибраний проєкт: <strong id="projectNamePlaceholder"></strong></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <!-- Тіло 1: Вибір дії -->
            <div class="modal-body" id="actionBody">

                <div class="row">
                    <div class="col-6 border-end">
                        <!-- Ліва частина -->
                        <div id="leftInfo">
                            <p><strong>Обґрунтування:</strong></p>
                            <p id="justificationText" class="mb-3 text-muted"></p>

                            <p><strong>Відповідальний виконавець:</strong>
                        </div>
                    </div>

                    <div class="col-6">
                        <!-- Права частина -->
                        <div id="rightInfo">
                            <p><strong>Статус закупівлі:</strong>
                            <span id="projectStatus" class="mb-3 text-muted"></span></p>

                            <p><strong>Сума Договору:</strong>
                            <span id="contractPrice" class="mb-3 text-muted"></span></p>

                            <p><strong>Документи:</strong></p>
                        </div>
                    </div>
                </div>

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

<%@include file="/WEB-INF/views/footer.jspf" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
    let currentProjectJustification = '';
    let currentProjectStatus = '';
    let currentContractPrice = 0;

    function openActionModal(projectId, projectName, justification, status, contractPrice) {
        currentProjectId = projectId;
        currentProjectName = projectName;
        currentProjectJustification = justification;
        currentProjectStatus = status;
        currentContractPrice = contractPrice;

        // Тіло вибору дії
        actionBody.classList.remove('d-none');
        confirmDeleteBody.classList.add('d-none');

        // Заголовок:
        modalTitle.textContent = projectName;

        // Обґрунтування
        const justificationElement = document.getElementById('justificationText');
        justificationElement.textContent = justification || '—';

        // Статус закупівлі
        const statusElement = document.getElementById('projectStatus');
        statusElement.textContent = status;

        // Сума Договору
        const contractPriceElement = document.getElementById('contractPrice');
        contractPriceElement.textContent = contractPrice;

        editButton.href = "${pageContext.request.contextPath}/update-project?id=" + projectId;

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

            fetch("${pageContext.request.contextPath}/delete-project", {
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

<%--Скрол до запису що був редагован + підсвітка--%>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        if (window.location.hash) {
            const targetCell = document.querySelector(window.location.hash);
            if (targetCell) {

                const row = targetCell.closest("tr");

                row.scrollIntoView({ behavior: "smooth", block: "center" });

                // Додаємо підсвітку на весь рядок
                row.classList.add("highlight-edited");

                // Через 10 сек повертаємо початковий стан
                setTimeout(() => {
                    row.classList.remove("highlight-edited");
                }, 5000); // 5 секунд
            }
        }
    });
</script>

<%--Зберігаємо позицію скролу перед переходом на іншу сторінку--%>
<script>
    window.addEventListener("beforeunload", function() {
        localStorage.setItem("scrollPositionEstimate", window.scrollY);
    });

    // Відновлюємо позицію скролу після повернення
    document.addEventListener("DOMContentLoaded", function() {
        const savedScroll = localStorage.getItem("scrollPositionEstimate");
        if (savedScroll !== null) {
            window.scrollTo({
                top: parseInt(savedScroll),
                behavior: "instant" // щоб не було плавного скролу при відновленні
            });
            // очищаємо після відновлення, щоб не заважало
            localStorage.removeItem("scrollPositionEstimate");
        }
    });
</script>

