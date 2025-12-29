<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@include file="/WEB-INF/views/layout/header.jspf" %>
        <style>
            <%@include file="/css/style.css" %>
        </style>

        <!-- Estimate table output -->
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

                    <%-- <tr class="black-borders">--%>
                        <%-- <th>2210</th>--%>
                            <%-- <th colspan="8" style="text-align: left">Предмети, матеріали, обладнання та інвентар, у
                                т. ч. м'який--%>
                                <%-- інвентар та обмундирування--%>
                                    <%-- </th>--%>
                                        <%-- </tr>--%>
                                            <%-- <c:forEach var="project2210" items="${projectsForPurchases2210}"
                                                varStatus="status">--%>
                                                <%-- <tr
                                                    onclick="openActionModal(${project2210.id}, '${project2210.nameProject}')">--%>
                                                    <%-- <td>
                                                        <c:out value="${status.count}" />
                                                        </td>--%>
                                                        <%--&lt;%&ndash; <td id="project-${project2210.id}"
                                                            class="project-row ${project2210.informatization ? 'informatization-row' : ''}">
                                                            <c:out value="${project2210.dkCode}" /> -
                                                            <c:out value="${project2210.nameProject}" />
                                                            </td>&ndash;%&gt;--%>
                                                            <%-- <td>
                                                                <c:out value="${project2210.dkCode}" /> -
                                                                <c:out value="${project2210.nameProject}" />
                                                                </td>--%>
                                                                <%-- <td>
                                                                    <c:out value="${project2210.unitOfMeasure}" />
                                                                    </td>--%>
                                                                    <%-- <td>
                                                                        <fmt:formatNumber
                                                                            value="${project2210.quantity}"
                                                                            pattern="#,##0" />
                                                                        </td>--%>
                                                                        <%-- <td>
                                                                            <fmt:formatNumber
                                                                                value="${project2210.price}"
                                                                                pattern="#,##0.00" />
                                                                            </td>--%>
                                                                            <%-- <td>
                                                                                <fmt:formatNumber
                                                                                    value="${project2210.totalPrice}"
                                                                                    pattern="#,##0.00" />
                                                                                </td>--%>
                                                                                <%-- </tr>--%>
                                                                                    <%-- </c:forEach>--%>
                                                                                        <%-- <tr>--%>
                                                                                            <%-- <th></th>--%>
                                                                                                <%-- <th
                                                                                                    style="text-align: right">ВСЬОГО
                                                                                                    ЗА 2210</th>--%>
                                                                                                    <%-- <th></th>--%>
                                                                                                        <%-- <th>
                                                                                                            <fmt:formatNumber
                                                                                                                value="${totalQuantity2210}"
                                                                                                                pattern="#,##0" />
                                                                                                            </th>--%>
                                                                                                            <%-- <th>
                                                                                                                </th>
                                                                                                                --%>
                                                                                                                <%--
                                                                                                                    <th>
                                                                                                                    <fmt:formatNumber
                                                                                                                        value="${totalPriceSum2210}"
                                                                                                                        pattern="#,##0.00" />
                                                                                                                    </th>
                                                                                                                    --%>
                                                                                                                    <%--
                                                                                                                        <th>
                                                                                                                        <fmt:formatNumber
                                                                                                                            value="${totalGeneralFund2210}"
                                                                                                                            pattern="#,##0.00" />
                                                                                                                        </th>
                                                                                                                        --%>
                                                                                                                        <%--
                                                                                                                            <th>
                                                                                                                            <fmt:formatNumber
                                                                                                                                value="${totalSpecialFund2210}"
                                                                                                                                pattern="#,##0.00" />
                                                                                                                            </th>
                                                                                                                            --%>
                                                                                                                            <%--
                                                                                                                                <th>
                                                                                                                                </th>
                                                                                                                                --%>
                                                                                                                                <%--
                                                                                                                                    </tr>--%>
                </table>
            </div>
        </div>

        <%@include file="/WEB-INF/views/layout/footer.jspf" %>