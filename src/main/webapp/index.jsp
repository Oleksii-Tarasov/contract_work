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


                </table>
            </div>
        </div>

        <%@include file="/WEB-INF/views/layout/footer.jspf" %>