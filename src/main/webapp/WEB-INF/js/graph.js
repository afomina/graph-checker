$(document).ready(function(){
//            /*<![CDATA[*/
//
//            var graph = [[]];
    //console.log(${graph});/

    var g = new Dracula.Graph();

    g.addEdge("strawberry", "cherry");
    g.addEdge("strawberry", "apple");
    g.addEdge("strawberry", "tomato");

    g.addEdge("tomato", "apple");
    g.addEdge("tomato", "kiwi");

    g.addEdge("cherry", "apple");
    g.addEdge("cherry", "kiwi");
    var render = function(r, n) {
        /* the Raphael set is obligatory, containing all you want to display */
        var set = r.set().push(
            /* custom objects go here */
            r.rect(n.point[0]-30, n.point[1]-13, 62, 86)
                .attr({"fill": "#fa8", "stroke-width": 2, r : "9px"}))
            .push(r.text(n.point[0], n.point[1] + 30, n.label)
                .attr({"font-size":"20px"}));
        /* custom tooltip attached to the set */
        set.items.forEach(
            function(el) {
                el.tooltip(r.set().push(r.rect(0, 0, 30, 30)
                    .attr({"fill": "#fec", "stroke-width": 1, r : "9px"})))});
        return set;
    };

    g.addNode("id35", {
        label : "meat\nand\ngreed" ,
        /* filling the shape with a color makes it easier to be dragged */
        render : render
    });

    var layouter = new Dracula.Layout.Spring(g);
    layouter.layout();

    var renderer = new Dracula.Renderer.Raphael('canvas', g, 400, 300);
    renderer.draw();

//            /*]]>*/
        });
