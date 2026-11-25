<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="base" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Результат проверки</title>
    <link rel="stylesheet" href="${base}/static/style.css">
</head>
<body>

<header id="header">
    <div id="film" aria-hidden="true"></div>
    <div class="header-name">Новошицкая Анна Николаевна</div>
    <div class="header-info">Группа P3232</div>
    <div class="header-info">Вариант XXXXXX</div>
</header>

<main id="main">
    <div class="block" id="results-block">
        <c:choose>
            <c:when test="${not empty error}">
                <h2 class="block-title">Ошибка</h2>
                <%-- c:out экранирует < > & " — защита от XSS --%>
                <p class="fail"><c:out value="${error}"/></p>
            </c:when>
            <c:otherwise>
                <h2 class="block-title">Результат проверки</h2>
                <div class="table-wrap">
                    <table class="data-table">
                        <thead>
                        <tr>
                            <th>X</th>
                            <th>Y</th>
                            <th>R</th>
                            <th>Результат</th>
                            <th>Дата и время</th>
                            <th>Время работы, мкс</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${result.x}</td>
                            <td>${result.y}</td>
                            <td>${result.r}</td>
                            <td class="${result.hit ? 'hit' : 'miss'}">${result.hit ? 'Попадание' : 'Промах'}</td>
                            <td data-time="${result.time}"></td>
                            <td>${result.exec}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <p class="${result.hit ? 'success' : 'fail'}">
                    ${result.hit ? 'Детка, ты выполнила задание на 5+' : 'Я могу расплакаться'}
                </p>
            </c:otherwise>
        </c:choose>

        <!-- Контроллер без параметров покажет форму -->
        <div class="buttons">
            <a class="button-link" href="${base}/controller">Назад к форме</a>
        </div>
    </div>
</main>

<script src="${base}/static/script.js"></script>
</body>
</html>
