<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/header.jspf" %>

<div class="container-sm mt-4">
    <div class="bg-white p-4 rounded shadow">
        <h2 class="mb-4">Новий проєкт кошторису</h2>

        <form method="post" action="${pageContext.request.contextPath}/new-project">
            <!-- КЕКВ -->
            <div class="mb-3">
                <label for="kekv" class="form-label">КЕКВ:</label>
                <select class="form-select" id="kekv" name="kekv" required>
                    <option value="" disabled selected>Оберіть КЕКВ</option>
                    <option value="2210">2210</option>
                    <option value="2240">2240</option>
                    <option value="3110">3110</option>
                </select>
            </div>
            <!-- Код ДК: -->
            <div class="mb-3">
                <label for="dkCode" class="form-label">Код ДК 021:2015</label>
                <input type="text" class="form-control" id="dkCode" name="dkCode"
                       maxlength="10" minlength="10" pattern="\d{8}-\d{1}" required>
            </div>
            <!-- Назва предмета закупівлі -->
            <div class="mb-3">
                <label for="justification" class="form-label">Назва предмета закупівлі:</label>
                <textarea class="form-control" id="nameProject" name="nameProject" rows="2" required></textarea>
            </div>
            <!-- Одиниці виміру -->
            <div class="mb-3">
                <label for="unitOfMeasure" class="form-label">Одиниця виміру:</label>
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
            <!-- Кількість -->
            <div class="mb-3">
                <label for="quantity" class="form-label">Кількість:</label>
                <input type="number" class="form-control" id="quantity" name="quantity" min="0" required>
            </div>
            <!-- Ціна за одиницю -->
            <div class="mb-3">
                <label for="price" class="form-label">Ціна за одиницю:</label>
                <input type="number" class="form-control" id="price" name="price" step="0.01" min="0" required>
            </div>
            <!-- Загальна Сума -->
            <div class="mb-3">
                <label for="totalPrice" class="form-label">Сума:</label>
                <input type="text" class="form-control" id="totalPrice" name="totalPrice" readonly>
            </div>


            <button type="submit" class="btn btn-primary">Створити</button>
            <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Скасувати</a>
        </form>
    </div>
</div>

<!-- Розрахунок суми -->
<script>
    // Функція для розрахунку та оновлення суми
    function calculateTotal() {
        const quantityInput = document.getElementById('quantity');
        const priceInput = document.getElementById('price');
        const totalPriceInput = document.getElementById('totalPrice');

        const quantity = parseFloat(quantityInput.value) || 0;
        const price = parseFloat(priceInput.value) || 0;

        const total = quantity * price;

        // Відображення суми з форматуванням (до двох знаків після коми)
        totalPriceInput.value = total.toFixed(2);
    }

    // Слухачі подій для полів "Кількість" та "Ціна"
    document.addEventListener('DOMContentLoaded', () => {
        const quantityInput = document.getElementById('quantity');
        const priceInput = document.getElementById('price');

        if (quantityInput && priceInput) {
            quantityInput.addEventListener('input', calculateTotal);
            priceInput.addEventListener('input', calculateTotal);
        }
    });
</script>

<%@include file="/WEB-INF/views/footer.jspf" %>
