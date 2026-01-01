<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@include file="/WEB-INF/views/layout/header.jspf" %>

        <div class="container-sm mt-3">
            <div class="bg-white p-4 rounded shadow">
                <h5 class="mb-3 fw-bold">Редагування проєкту</h5>

                <!-- action веде на сервлет або контролер для оновлення -->
                <form method="post" action="${pageContext.request.contextPath}/purchases/update-project">
                    <!-- приховане поле для ID -->
                    <input type="hidden" name="id" value="${projectForUpdate.id}">

                    <div class="row">
                        <!-- Лівий стовпчик -->
                        <div class="col-md-6">
                            <!-- КЕКВ -->
                            <div class="mb-3">
                                <label for="kekv" class="form-label fw-bold">КЕКВ:</label>
                                <select class="form-select" id="kekv" name="kekv" required>
                                    <option value="" disabled>Оберіть КЕКВ</option>
                                    <option value="2210" ${projectForUpdate.kekv=='2210' ? 'selected' : '' }>2210
                                        Предмети, матеріали, обладнання та інвентар</option>
                                    <option value="2240" ${projectForUpdate.kekv=='2240' ? 'selected' : '' }>2240 Оплата
                                        послуг (крім комунальних)</option>
                                    <option value="3110" ${projectForUpdate.kekv=='3110' ? 'selected' : '' }>3110
                                        Придбання обладнання і предметів довгострокового користування</option>
                                </select>
                            </div>

                            <!-- Код ДК -->
                            <div class="mb-3">
                                <label for="dkCode" class="form-label fw-bold">Код ДК 021:2015</label>
                                <input type="text" class="form-control" id="dkCode" name="dkCode" maxlength="10"
                                    minlength="10" pattern="\d{8}-\d{1}" value="${projectForUpdate.dkCode}" required>
                            </div>

                            <!-- Назва -->
                            <div class="mb-3">
                                <label for="projectName" class="form-label fw-bold">Назва предмета закупівлі:</label>
                                <textarea class="form-control" id="projectName" name="projectName" rows="2"
                                    required>${projectForUpdate.projectName}</textarea>
                            </div>

                            <!-- Обґрунтування -->
                            <div class="mb-3">
                                <label for="justification" class="form-label fw-bold">Обґрунтування закупівлі:</label>
                                <textarea class="form-control" id="justification" name="justification" rows="3"
                                    required>${projectForUpdate.justification}</textarea>
                            </div>

                            <!-- Одиниця виміру -->
                            <div class="mb-3">
                                <label for="unitOfMeasure" class="form-label fw-bold">Одиниця виміру:</label>
                                <select class="form-select" id="unitOfMeasure" name="unitOfMeasure" required>
                                    <option value="" disabled>Оберіть одиницю виміру</option>
                                    <option value="шт." ${projectForUpdate.unitOfMeasure=='шт.' ? 'selected' : '' }>штук
                                    </option>
                                    <option value="компл." ${projectForUpdate.unitOfMeasure=='компл.' ? 'selected' : ''
                                        }>комплекти</option>
                                    <option value="кільк." ${projectForUpdate.unitOfMeasure=='кільк.' ? 'selected' : ''
                                        }>кількість</option>
                                    <option value="посл." ${projectForUpdate.unitOfMeasure=='посл.' ? 'selected' : '' }>
                                        послуги</option>
                                    <option value="місяць" ${projectForUpdate.unitOfMeasure=='місяць' ? 'selected' : ''
                                        }>місяць</option>
                                    <option value="квартал" ${projectForUpdate.unitOfMeasure=='квартал' ? 'selected'
                                        : '' }>квартал</option>
                                </select>
                            </div>

                            <!-- Кількість -->
                            <div class="mb-3">
                                <label for="quantity" class="form-label fw-bold">Кількість:</label>
                                <input type="number" class="form-control" id="quantity" name="quantity" min="0"
                                    value="${projectForUpdate.quantity}" required>
                            </div>

                            <!-- Ціна за одиницю -->
                            <div class="mb-3">
                                <label for="price" class="form-label fw-bold">Ціна за одиницю, грн:</label>
                                <input type="number" class="form-control" id="price" name="price" step="0.01" min="0"
                                    value="${projectForUpdate.price}" required>
                            </div>

                        </div>

                        <!-- Правий стовпчик -->
                        <div class="col-md-6">

                            <!-- Загальна Сума -->
                            <div class="mb-3">
                                <label for="totalPrice" class="form-label fw-bold">Загальна Сума, грн:</label>
                                <input type="text" class="form-control" id="totalPrice" name="totalPrice" readonly
                                    value="${projectForUpdate.totalPrice}">
                            </div>

                            <!-- Спеціальний фонд -->
                            <div class="mb-3">
                                <label for="specialFund" class="form-label fw-bold">Спеціальний фонд:</label>
                                <input type="text" class="form-control" id="specialFund" name="specialFund" readonly
                                    value="${projectForUpdate.specialFund}">
                            </div>
                            <!-- Загальний фонд -->
                            <div class="mb-3">
                                <label for="generalFund" class="form-label fw-bold">Загальний фонд:</label>
                                <input type="number" class="form-control" id="generalFund" name="generalFund" min="0"
                                    value="${projectForUpdate.generalFund}">
                            </div>
                            <!-- Статус закупівлі -->
                            <div class="mb-3">
                                <label for="projectStatus" class="form-label fw-bold">Статус закупівлі:</label>
                                <select class="form-select" id="projectStatus" name="projectStatus">
                                    <c:forEach var="st" items="${projectStatuses}">
                                        <option value="${st.dbValue}" ${projectForUpdate.projectStatus !=null &&
                                            projectForUpdate.projectStatus.dbValue==st.dbValue ? 'selected' : '' }>
                                            ${fn:escapeXml(st.displayName)}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <!-- Відповідальний виконавець -->
                            <div class="mb-3">
                                <label for="executorId" class="form-label fw-bold">Відповідальний виконавець:</label>
                                <select class="form-select" id="executorId" name="executorId">
                                    <option value="">-- не вибрано --</option>
                                    <c:forEach var="u" items="${users}">
                                        <option value="${u.id}" ${projectForUpdate.responsibleExecutor !=null &&
                                            projectForUpdate.responsibleExecutor.id==u.id ? 'selected' : '' }>
                                            ${fn:escapeXml(u.shortName)}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <!-- Сума Договору -->
                            <div class="mb-3">
                                <label for="contractPrice" class="form-label fw-bold">Сума Договору, грн:</label>
                                <input type="number" class="form-control" id="contractPrice" name="contractPrice"
                                    step="0.01" min="0" value="${projectForUpdate.contractPrice}">
                            </div>
                            <!-- Оплата до -->
                            <div class="mb-3">
                                <label for="paymentTo" class="form-label fw-bold">Оплата до:</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="fa-solid fa-calendar-days"></i></span>
                                    <%-- fn:substring обрізає час, залишаючи тільки дату YYYY-MM-DD для input
                                        type="date" --%>
                                        <input type="date" class="form-control" id="paymentTo" name="paymentTo"
                                            value="${projectForUpdate.paymentTo != null ? fn:substring(projectForUpdate.paymentTo, 0, 10) : ''}">
                                </div>
                            </div>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-warning">Зберегти</button>
                    <a href="${pageContext.request.contextPath}/purchases?updatedId=${projectForUpdate.id}"
                        class="btn btn-secondary">
                        Скасувати
                    </a>
                </form>
            </div>
        </div>

        <script>
            // Функція для розрахунку та оновлення суми
            function calculateTotal() {
                const quantityInput = document.getElementById('quantity');
                const priceInput = document.getElementById('price');
                const totalPriceInput = document.getElementById('totalPrice');
                const generalFundInput = document.getElementById('generalFund');
                const specialFundInput = document.getElementById('specialFund');

                const quantity = parseFloat(quantityInput.value) || 0;
                const price = parseFloat(priceInput.value) || 0;

                const total = quantity * price;
                const generalFund = parseFloat(generalFundInput.value) || 0;

                totalPriceInput.value = total.toFixed(2);

                const specialFund = total - generalFund;

                if (specialFund < 0) {
                    specialFundInput.value = (0).toFixed(2);
                    generalFundInput.value = total.toFixed(2);
                } else {
                    specialFundInput.value = specialFund.toFixed(2);
                }
            }

            document.addEventListener('DOMContentLoaded', () => {
                const quantityInput = document.getElementById('quantity');
                const priceInput = document.getElementById('price');
                const generalFundInput = document.getElementById('generalFund');

                if (quantityInput && priceInput) {
                    quantityInput.addEventListener('input', calculateTotal);
                    priceInput.addEventListener('input', calculateTotal);
                    calculateTotal();
                }

                if (generalFundInput) {
                    generalFundInput.addEventListener('input', calculateTotal);
                }
            });
        </script>

        <%@include file="/WEB-INF/views/layout/footer.jspf" %>