<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%-- Бин из сессии (его кладёт SessionListener; если вдруг нет, useBean создаст новый) --%>
<jsp:useBean id="results" class="lab3.model.ResultsBean" scope="session"/>
<c:set var="base" value="${pageContext.request.contextPath}"/>
<c:set var="last" value="${results.last}"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Лабораторная работа №3</title>
    <link rel="stylesheet" href="${base}/static/style.css">
</head>
<body data-base="${base}">

<!-- Шапка -->
<header id="header">
    <div id="film" aria-hidden="true"></div>
    <div class="header-name">Новошицкая Анна Николаевна</div>
    <div class="header-info">Группа P3232</div>
    <div class="header-info">Вариант 57415392</div>
</header>

<main id="main">
    <!-- График -->
    <div class="block" id="plot-block">
        <h2 class="block-title">Область</h2>
        <canvas id="plot" width="400" height="400"></canvas>
        <div class="error" id="plot-error"></div>
    </div>

    <!-- Форма: обычная отправка GET на контроллер, страница перезагрузится -->
    <div class="block" id="form-block">
        <h2 class="block-title">Параметры точки</h2>
        <form id="point-form" action="${base}/controller" method="get" novalidate>

            <!-- X -->
            <div class="field">
                <label class="field-label" for="x-select">X:</label>
                <select id="x-select" name="x">
                    <option value="" selected disabled>выберите X</option>
                    <c:forEach var="xv" items="-2,-1.5,-1,-0.5,0,0.5,1,1.5,2">
                        <option value="${xv}">${xv}</option>
                    </c:forEach>
                </select>
                <div class="error" id="x-error"></div>
            </div>

            <!-- Y -->
            <div class="field">
                <label class="field-label" for="y-input">Y:</label>
                <input type="text" id="y-input" name="y" maxlength="14"
                       placeholder="число в интервале (-5, 5)" autocomplete="off">
                <div class="error" id="y-error"></div>
            </div>

            <!-- R: по умолчанию выбран R последней проверки, чтобы на графике сразу были точки -->
            <div class="field">
                <span class="field-label">R:</span>
                <div class="radio-group" id="r-group">
                    <c:forEach var="rv" items="1,2,3,4,5">
                        <label>
                            <input type="radio" name="r" value="${rv}" ${last.r == rv ? 'checked' : ''}>
                            <span>${rv}</span>
                        </label>
                    </c:forEach>
                </div>
                <div class="error" id="r-error"></div>
            </div>

            <div class="buttons">
                <button type="submit" id="submit-btn">Проверить</button>
            </div>
        </form>
    </div>

    <!-- Результаты: строки берутся из бина в сессии -->
    <div class="block" id="results-block">
        <h2 class="block-title">Результаты</h2>
        <div class="table-wrap">
            <table class="data-table" id="results">
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
                <tbody id="results-body">
                <c:forEach var="row" items="${results.results}">
                    <%-- data-* читает script.js, чтобы нарисовать точки --%>
                    <tr data-x="${row.x}" data-y="${row.y}" data-r="${row.r}" data-hit="${row.hit}">
                        <td>${row.x}</td>
                        <td>${row.y}</td>
                        <td>${row.r}</td>
                        <td class="${row.hit ? 'hit' : 'miss'}">${row.hit ? 'Попадание' : 'Промах'}</td>
                        <td data-time="${row.time}"></td>
                        <td>${row.exec}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <c:if test="${empty results.results}">
            <p class="empty">Пока нет ни одной проверки.</p>
        </c:if>
    </div>
</main>

<script src="${base}/static/script.js"></script>
</body>
</html>
