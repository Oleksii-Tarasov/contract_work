<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            <c:forEach var="project2210" items="${projectsForPurchases2210}" varStatus="status">
                <tr id="project-${project2210.id}">
                    <td><c:out value="${status.count}"/></td>
                        <%-- informatization == true --%>
                    <td class="${project2210.informatization ? 'informatization-row' : ''}"
                        onclick="openActionModal(${project2210.id}, '${project2210.nameProject}')">
                        <c:out value="${project2210.dkCode}"/> - <c:out value="${project2210.nameProject}"/>
                    </td>
                    <td><c:out value="${project2210.unitOfMeasure}"/></td>
                    <td><fmt:formatNumber value="${project2210.quantity}" pattern="#,##0"/></td>
                    <td><fmt:formatNumber value="${project2210.price}" pattern="#,##0.00"/></td>
                    <td><fmt:formatNumber value="${project2210.totalPrice}" pattern="#,##0.00"/></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
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

<%@include file="/WEB-INF/views/footer.jspf" %>
<%@include file="/WEB-INF/views/tableCommonScripts.jspf" %>
