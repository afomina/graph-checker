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

</head>
<body>
<span id="graph">${graph.matrixString()}</span>
<span id="order" style="display:none;">${graph.getOrder()}</span>

    <div id="canvas" ></div>

    <script type="text/javascript">
window.onload = function() {
            var graph = $("#graph").text();
            console.log(graph);

            var g = new Dracula.Graph();

            g.addEdge("strawberry", "cherry");
            g.addEdge("strawberry", "apple");
            g.addEdge("strawberry", "tomato");

            g.addEdge("tomato", "apple");
            g.addEdge("tomato", "kiwi");

            g.addEdge("cherry", "apple");
            g.addEdge("cherry", "kiwi");

            var layouter = new Dracula.Layout.Spring(g);
            layouter.layout();

            var renderer = new Dracula.Renderer.Raphael('canvas', g, 0, 200);
            renderer.draw();

            $('svg').removeAttr("style");
//    $('svg').removeAttr("width");
            $('svg').width("500");
};
    </script>
</body>
</html>