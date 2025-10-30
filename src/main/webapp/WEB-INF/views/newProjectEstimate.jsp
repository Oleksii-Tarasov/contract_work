<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/header.jspf" %>

<div class="container-sm mt-3">
    <div class="bg-white p-4 rounded shadow">
        <h5 class="mb-3 fw-bold">Створення проєкту до кошторису</h5>

        <form method="post" action="${pageContext.request.contextPath}/new-project">
            <div class="row">
                <!-- Лівий стовпчик -->
                <div class="col-md-6">
                    <!-- КЕКВ -->
                    <div class="mb-3">
                        <label for="kekv" class="form-label fw-bold">КЕКВ:</label>
                        <select class="form-select" id="kekv" name="kekv" required>
                            <option value="" disabled selected>Оберіть КЕКВ</option>
                            <option value="2210">2210 Предмети, матеріали, обладнання та інвентар</option>
                            <option value="2240">2240 Оплата послуг (крім комунальних)</option>
                            <option value="3110">3110 Придбання обладнання і предметів довгострокового користування</option>
                        </select>
                    </div>
                    <!-- Код ДК: -->
                    <div class="mb-3">
                        <label for="dkCode" class="form-label fw-bold">Код ДК 021:2015</label>
                        <input type="text" class="form-control" id="dkCode" name="dkCode"
                               maxlength="10" minlength="10" pattern="\d{8}-\d{1}" required>
                    </div>
                    <!-- Назва предмета закупівлі -->
                    <div class="mb-3">
                        <label for="nameProject" class="form-label fw-bold">Назва предмета закупівлі:</label>
                        <textarea class="form-control" id="nameProject" name="nameProject" rows="2" required></textarea>
                    </div>
                    <!-- Обгрунтування закупівлі -->
                    <div class="mb-3">
                        <label for="justification" class="form-label fw-bold">Обгрунтування закупівлі:</label>
                        <textarea class="form-control" id="justification" name="justification" rows="3" required></textarea>
                    </div>
                    <!-- Одиниця виміру -->
                    <div class="mb-3">
                        <label for="unitOfMeasure" class="form-label fw-bold">Одиниця виміру:</label>
                        <select class="form-select" id="unitOfMeasure" name="unitOfMeasure" required>
                            <option value="" disabled selected>Оберіть одиницю виміру</option>
                            <option value="шт.">штук</option>
                            <option value="компл.">комплекти</option>
                            <option value="кільк.">кількість</option>
                            <option value="посл.">послуги</option>
                            <option value="місяць">місяць</option>
                            <option value="квартал">квартал</option>
                        </select>
                    </div>
                </div>

                <!-- Правий стовпчик -->
                <div class="col-md-6">
                    <!-- Кількість -->
                    <div class="mb-3">
                        <label for="quantity" class="form-label fw-bold">Кількість:</label>
                        <input type="number" class="form-control" id="quantity" name="quantity" min="0" required>
                    </div>
                    <!-- Ціна за одиницю -->
                    <div class="mb-3">
                        <label for="price" class="form-label fw-bold">Ціна за одиницю, грн:</label>
                        <input type="number" class="form-control" id="price" name="price" step="0.01" min="0" required>
                    </div>
                    <!-- Загальна Сума -->
                    <div class="mb-3">
                        <label for="totalPrice" class="form-label fw-bold">Загальна Сума, грн:</label>
                        <input type="text" class="form-control" id="totalPrice" name="totalPrice" readonly>
                    </div>
                    <!-- Поле для Спеціального фонду -->
                    <div class="mb-3">
                        <label for="specialFund" class="form-label fw-bold">Спеціальний фонд:</label>
                        <input type="text" class="form-control" id="specialFund" name="specialFund" readonly>
                    </div>
                    <!-- Поле для Загального фонду -->
                    <div class="mb-3">
                        <label for="generalFund" class="form-label fw-bold">Загальний фонд:</label>
                        <input type="number" class="form-control" id="generalFund" name="generalFund" min="0" value="0">
                    </div>
                </div>
            </div>
            <button type="submit" class="btn btn-primary">Створити</button>
            <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Скасувати</a>
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

        // Оновлюємо загальну суму
        totalPriceInput.value = total.toFixed(2);

        // Перераховуємо спеціальний фонд
        const specialFund = total - generalFund;

        // Перевіряємо, щоб сума спеціального фонду не була від'ємною
        if (specialFund < 0) {
            specialFundInput.value = (0).toFixed(2);
            generalFundInput.value = total.toFixed(2);
        } else {
            specialFundInput.value = specialFund.toFixed(2);
        }
    }

    // Функція для ініціалізації розподілу коштів
    function initializeFunds() {
        const specialFundInput = document.getElementById('specialFund');
        const totalPriceInput = document.getElementById('totalPrice');

        const total = parseFloat(totalPriceInput.value) || 0;

        // Автоматично встановлюємо всю суму в спеціальний фонд
        specialFundInput.value = total.toFixed(2);
    }

    document.addEventListener('DOMContentLoaded', () => {
        const quantityInput = document.getElementById('quantity');
        const priceInput = document.getElementById('price');
        const generalFundInput = document.getElementById('generalFund');

        if (quantityInput && priceInput) {
            quantityInput.addEventListener('input', calculateTotal);
            priceInput.addEventListener('input', calculateTotal);

            // Викликаємо функцію розподілу при завантаженні сторінки
            calculateTotal();
        }

        // Слухач для поля "Загальний фонд"
        if (generalFundInput) {
            generalFundInput.addEventListener('input', calculateTotal);
        }
    });
</script>

<%@include file="/WEB-INF/views/footer.jspf" %>
