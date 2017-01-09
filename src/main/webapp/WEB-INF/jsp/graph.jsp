<!DOCTYPE html>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Информация о графе</title>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="https://rawgit.com/DmitryBaranovskiy/raphael/master/raphael.min.js"></script>
    <script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/graphdracula/1.0.3/dracula.min.js"></script>
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
    <tr>
        <td>${graph.getOrder()}</td>
        <td>${graph.getEdgeAmount()}</td>
        <td>${graph.getConnected() == 1? 'Да':'Нет'}</td>
        <td>${graph.getVertexConnectivity()}</td>
        <td>${graph.getConnected() == 1? graphs.get(i).getEdgeConnectivity() : 0}</td>
        <td>${graph.getConnected() == 1? graphs.get(i).getRadius(): '-'}</td>
        <td>${graph.getConnected() == 1? graphs.get(i).getDiametr(): '-'}</td>
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

<canvas id="canvas" width="400" height="400"></canvas>

<script type="text/javascript">
    window.onload = function () {
        var graph = $("#graph").text();
        console.log(graph);
        var n = $("#order").text();

//            var g = new Dracula.Graph();
        var x0 = 200, y0 = 200, r = 100;
        var fi = 2 * Math.PI / n;

        var c = document.getElementById("canvas");
        var ctx = c.getContext("2d");

        //vertexes
        for (var i = 0; i < n; i++) {

            var xi = x0 + r * Math.cos(fi * (i + 1));
            var yi = y0 + r * Math.sin(fi * (i + 1));
            ctx.beginPath();
            ctx.arc(xi, yi, 8, 0, 2 * Math.PI);
            ctx.stroke();
        }

        var s = graph.split(" ");
        var idx = 0;
        for (var i = 0; i < n; i++) {

            var xi = x0 + r * Math.cos(fi * (i + 1));
            var yi = y0 + r * Math.sin(fi * (i + 1));
            for (var j = 0; j < n; j++) {
                if (s[idx++] == "1") {
                    var xj = x0 + r * Math.cos(fi * (j + 1));
                    var yj = y0 + r * Math.sin(fi * (j + 1));

                    //edge
                    ctx.moveTo(xi, yi);
                    ctx.lineTo(xj, yj);
                    ctx.stroke();
                }
            }
        }

//            var layouter = new Dracula.Layout.Spring(g);
//            layouter.layout();
//
//            var renderer = new Dracula.Renderer.Raphael('canvas', g, 0, 200);
//            renderer.draw();
//
//            $('svg').removeAttr("style");
//            $('svg').width("500");
    };
</script>
</body>
</html>