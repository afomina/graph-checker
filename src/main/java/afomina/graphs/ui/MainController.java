package afomina.graphs.ui;

import afomina.graphs.data.Graph;
import afomina.graphs.data.GraphService;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/graphs", method = RequestMethod.GET)
    public String findGraphs(@RequestParam Map<String, String> requestParams, Model model
            /*@RequestParam("vertex") Integer vertex,
                                  @RequestParam("edge") Integer edge,
                                  @RequestParam("edgeCon") Integer edgeCon,
                                  @RequestParam("isConnected") Integer connected*/) {
        List<Graph> graphs = GraphService.get()
                .openSession()
                .createCriteria(Graph.class)
                .add(Restrictions.allEq(requestParams))
                .list();
        model.addAttribute("graphs", graphs);
        return "graphs";
    }
}
