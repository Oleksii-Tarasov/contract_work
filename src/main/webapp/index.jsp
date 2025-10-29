<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/header.jspf" %>

<!-- Estimate table output -->
<%--<c:if test="${not empty sevUsers}">--%>
<div class="container-sm mt-4">
    <div class="bg-white p-4 rounded shadow">
        <table class="table table-striped-columns">
            <tr>
                <th>№</th>
                <th data-sortable="true">Назва предмета закупівлі</th>
                <th data-sortable="true">одиниця виміру</th>
                <th data-sortable="true">кількість</th>
                <th data-sortable="true">ціна за одиницю</th>
                <th data-sortable="true">Сума</th>
                <th data-sortable="true">Сума Договору</th>
                <th data-sortable="true">Залишок</th>
            </tr>
            <%--                <c:forEach var="user" items="${sevUsers}" varStatus="status">--%>
            <%--                    <tr>--%>
            <%--                        <td><c:out value="${status.count}"/></td>--%>
            <%--                        <td><c:out value="${user.edrpou}"/></td>--%>
            <%--                        <td><c:out value="${user.shortName}"/></td>--%>
            <%--                        <td><c:out value="${user.fullName}"/></td>--%>
            <%--                        <td><c:out value="${user.isTerminated}"/></td>--%>
            <%--                        <td><c:out value="${user.connected ? 'Так' : 'Ні'}"/></td>--%>
            <%--                    </tr>--%>
            <%--                </c:forEach>--%>
        </table>
    </div>
</div>
<%--</c:if>--%>

<%@include file="/WEB-INF/views/footer.jspf" %>
