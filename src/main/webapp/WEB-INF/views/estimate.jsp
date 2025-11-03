<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/header.jspf" %>

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
                <th>Розрахунок та обгрунтування щодо необхідності зазначеного придбання</th>
            </tr>
            <tr>
                <th>2210</th>
                <th colspan="8" style="text-align: left">Предмети, матеріали, обладнання та інвентар, у т. ч. м'який
                    інвентар та обмундирування
                </th>
            </tr>
            <c:forEach var="project2210" items="${projectsForEstimate2210}">
                <tr>
                    <td></td>
                    <td><c:out value="${project2210.dkCode}"/> - <c:out value="${project2210.nameProject}"/></td>
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
            <tr>
                <th>2240</th>
                <th colspan="8" style="text-align: left">Оплата послуг (крім комунальних)</th>
            </tr>
            <c:forEach var="project2240" items="${projectsForEstimate2240}">
                <tr>
                    <td></td>
                    <td><c:out value="${project2240.dkCode}"/> - <c:out value="${project2240.nameProject}"/></td>
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
            <tr>
                <th>3110</th>
                <th colspan="8" style="text-align: left">Придбання обладнання і предметів довгострокового користування
                </th>
            </tr>
            <c:forEach var="project3110" items="${projectsForEstimate3110}">
                <tr>
                    <td></td>
                    <td><c:out value="${project3110.dkCode}"/> - <c:out value="${project3110.nameProject}"/></td>
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
</div>

<%@include file="/WEB-INF/views/footer.jspf" %>
