package afomina.graphs.ui;

import afomina.graphs.data.Graph;
import afomina.graphs.data.GraphDao;
import afomina.graphs.data.GraphService;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    GraphDao graphDao;

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
        Map<String, Object> userParams = new HashMap<>();
        for (Map.Entry<String, String> entry : requestParams.entrySet()) {
            Object value = entry.getValue();
            if (!entry.getValue().isEmpty()) {
                try {
                   value  = new Integer(Integer.parseInt(entry.getValue()));
                } catch (NumberFormatException e ) {}
                userParams.put(entry.getKey(), value);
            }
        }
//        Session session = GraphService.get().openSession();
        List<Graph> graphs = graphDao.find(userParams);
//        session.getTransaction().commit();
        model.addAttribute("graphs", graphs);
        return "graphs";
    }
}
