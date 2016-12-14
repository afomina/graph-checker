<!DOCTYPE html>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Информация о графе</title>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="https://rawgit.com/DmitryBaranovskiy/raphael/master/raphael.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/graphdracula/1.0.3/dracula.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>
<body>
<span id="graph" style="display:none;">${graph.matrixString()}</span>
<span id="order" style="display:none;">${graph.getOrder()}</span>

<table class="table table-bordered">
    <thead>
    <tr>
<th>n</th>
<th>m</th>
<th>Связный</th>
<%--<th>Вершинная связность</th>--%>
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
    <tr>
        <td>${graph.getOrder()}</td>
        <td>${graph.getEdgeAmount()}</td>
        <td>${graph.isCon()? 'Да':'Нет'}</td>
        <%--<td>${graph.getVertexConnectivity()}</td>--%>
        <td>${graph.isCon()? graphs.get(i).getEdgeConnectivity() : 0}</td>
        <td>${graph.isCon()? graphs.get(i).getRadius(): '-'}</td>
        <td>${graph.isCon()? graphs.get(i).getDiametr(): '-'}</td>
        <td>${graph.getComponents()}</td>
        <td>${graph.isAcyclic()? '-' : graphs.get(i).getGirth()}</td>
        <td>${graph.isAcyclic()?'Да':'Нет'}</td>
        <td>${graph.getTwoPartial()==1? 'Да':'Нет'}</td>
        <td>${graph.getExp()==null?'-':graphs.get(i).getExp()}</td>
        <td>${graphs.get(i).getChromeNumber()}</td>
        <td>${graphs.get(i).getIndependenceNumber()}</td>
    </tr>
    </tbody>
    </table>

    <div id="canvas" ></div>

    <script type="text/javascript">
window.onload = function() {
            var graph = $("#graph").text();
            console.log(graph);
            var n=$("#order").text();

            var g = new Dracula.Graph();

            var s=graph.split(" ");
            var idx=0;
            for (var i=0; i<n; i++) {
                for (var j=0;j<n;j++) {
                    if (s[idx++] == "1") {
                        g.addEdge(i+1, j+1);
                    }
                }
            }

            var layouter = new Dracula.Layout.Spring(g);
            layouter.layout();

            var renderer = new Dracula.Renderer.Raphael('canvas', g, 0, 200);
            renderer.draw();

            $('svg').removeAttr("style");
            $('svg').width("500");
};
    </script>
</body>
</html>