<!DOCTYPE html>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <!--<meta charset="UTF-8">-->
    <title>Результат запроса</title>
    <style>
        thead {
        display: table-header-group;
        }
        tr,
        img {
        page-break-inside: avoid;
        }
        .table {
        border-collapse: collapse !important;
        }
        .table td,
        .table th {
        background-color: #fff !important;
        }
        .table-bordered th,
        .table-bordered td {
        border: 1px solid #ddd !important;
        }
    </style>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>
<body>
<h4>По запросу <mark>${params}</mark></h4>
<h4>найдено графов <mark>${amount}</mark></h4><br />
<c:if test="${graphs != null && graphs.size() > 0}">
<table class="table table-bordered">
    <thead>
    <tr>
        <th>#</th>
        <th>Код</th>
        <th>n</th>
        <th>m</th>
        <th>Связный</th>
        <th>Вершинная связность</th>
        <th>Реберная связность</th>
        <th>Радиус</th>
        <th>Диаметр</th>
        <th>Число компонент связности</th>
        <th>Обхват</th>
        <th>Ацикличный</th>
        <th>Двудольный</th>
        <th>Экспонент</th>
        <th>Хроматическое число</th>
        <th>Число независимости</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="i" begin="0" end="${graphs.size()-1}" >
    <tr>
        <td>${i+1}</td>
        <td>
            <a href="graphs/${graphs.get(i).getId()}">
                ${graphs.get(i).getCode()}
            </a>
        </td>
        <td>${graphs.get(i).getOrder()}</td>
        <td>${graphs.get(i).getEdgeAmount()}</td>
        <td>${graphs.get(i).getConnected() == 1? 'Да':'Нет'}</td>
        <td>${graphs.get(i).getVertexConnectivity()}</td>
        <td>${graphs.get(i).getConnected() == 1? graphs.get(i).getEdgeConnectivity() : 0}</td>
        <td>${graphs.get(i).getConnected() == 1? graphs.get(i).getRadius(): '-'}</td>
        <td>${graphs.get(i).getConnected() == 1? graphs.get(i).getDiametr(): '-'}</td>
        <td>${graphs.get(i).getComponents()}</td>
        <td>${graphs.get(i).isAcyclic()? '-' : graphs.get(i).getGirth()}</td>
        <td>${graphs.get(i).isAcyclic()?'Да':'Нет'}</td>
        <td>${graphs.get(i).getTwoPartial()==1? 'Да':'Нет'}</td>
        <td>${graphs.get(i).getExp()==null?'-':graphs.get(i).getExp()}</td>
        <td>${graphs.get(i).getChromeNumber()}</td>
        <td>${graphs.get(i).getIndependenceNumber()}</td>
    </tr>
    </c:forEach>
    </tbody>
</table>
<ul class="pagination">
    <c:if test="${curPage == 1}">
        <li class="active"><a onclick="page(1)">1</a></li>
    </c:if>
    <c:if test="${curPage != 1}">
        <li><a onclick="page(1)">1</a></li>
    </c:if>
    <c:if test="${curPage > 3}">
        <li><a href="#">...</a></li>
    </c:if>
    <c:if test="${curPage > 2}">
        <li><a onclick="page(${curPage - 1})">${curPage - 1}</a></li>
    </c:if>
    <c:if test="${curPage > 1}">
        <li class="active"><a onclick="page(${curPage})">${curPage}</a></li>
    </c:if>
    <c:if test="${pageCount > curPage}">
        <li><a onclick="page(${curPage + 1})">${curPage + 1}</a></li>
        <li><a href="#">...</a></li>
        <li><a onclick="page(${pageCount})">${pageCount}</a></li>
    </c:if>
</ul>
</c:if>
<script>
    var page = function(n) {
        var url = window.location.href;
        var idx = url.indexOf("page");
        if (idx == -1){
            url += '&page=' + n;
        } else {
            url = url.substring(0, idx) + 'page=' + n;
        }
        window.location.href = url;
    }
</script>
</body>
</html>