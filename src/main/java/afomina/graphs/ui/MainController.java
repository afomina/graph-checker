package afomina.graphs.ui;

import afomina.graphs.count.*;
import afomina.graphs.data.Graph;
import afomina.graphs.data.GraphDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private static final int MIN_VERTEXES = 6;
    private static final int MAX_VERTEXES = 10;
    private static final int GRAPHS_TO_STORE = 100;
    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final List<? extends InvariantCounter> INVARIANTS = Arrays.asList(new Girth());
    @Autowired
    GraphDao graphDao;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "more", method = RequestMethod.GET)
    public String more() {
        return "more";
    }

    @RequestMapping(value = "/graphs", method = RequestMethod.GET)
    public String findGraphs(@RequestParam Map<String, String> requestParams, Model model) {
        Map<String, Object> userParams = new HashMap<>();
        for (Map.Entry<String, String> entry : requestParams.entrySet()) {
            if (!"page".equals(entry.getKey())) {
                Object value = entry.getValue();
                if (!entry.getValue().isEmpty()) {
                    try {
                        value = new Integer(Integer.parseInt(entry.getValue()));
                    } catch (NumberFormatException e) {
                    }
                    userParams.put(entry.getKey(), value);
                }
            }
        }
        int page = requestParams.containsKey("page") ? Integer.parseInt(requestParams.get("page")) : 1;
        model.addAttribute("curPage", page);
        List<Graph> graphs = graphDao.find(userParams, DEFAULT_PAGE_SIZE, page);

        model.addAttribute("graphs", graphs);
        Long count = graphDao.count(userParams);
        long pageCount = count / DEFAULT_PAGE_SIZE;
        if (count % DEFAULT_PAGE_SIZE != 0) {
            pageCount++;
        }
        model.addAttribute("pageCount", pageCount);
        return "graphs";
    }

    @RequestMapping(value = "graphsMore", method = RequestMethod.GET)
    public String findGraphs(@RequestParam String sql, Model model) {
        List<Graph> graphs = graphDao.findBySql(sql);
        model.addAttribute("graphs", graphs);
        return "graphs";
    }

    @RequestMapping(value = "/graphs/{id}", method = RequestMethod.GET)
    public String getGraph(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("graph", graphDao.findById(id));
        return "graph";
    }

    @RequestMapping(value = "calc", method = RequestMethod.GET)
    public String calcInvariants() {
        processGraphs();
        return "index";
    }

    public void processGraphs() {
        int cnt = 0;
//        int page = 1;
//        graphDao.start();
        for (int n = MIN_VERTEXES; n <= MAX_VERTEXES; n++) {
            try {
                Long count = graphDao.count(n);
                long pageCount = count / 100;
                if (count % 100 != 0) {
                    pageCount++;
                }
                for (int page = 0; page < pageCount; page++) {
                    List<Graph> graphs = graphDao.findByOrder(n, 100, page);
                    for (Graph graph : graphs) {
                        for (InvariantCounter invariant : INVARIANTS) {
                            invariant.getInvariant(graph);
                        }
                        graphDao.save(graph);
                        if (++cnt == GRAPHS_TO_STORE) {
                            graphDao.flush();
                            cnt = 0;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception processing graph, vertexes=" + n);
//                break;
            }
        }
        graphDao.flush();
    }

    public void processGraphs(List<Graph> graphs) {
        int cnt = 0;
        for (Graph graph : graphs) {
            for (InvariantCounter invariant : INVARIANTS) {
                invariant.getInvariant(graph);
            }
            graphDao.save(graph);
            if (++cnt == GRAPHS_TO_STORE) {
                graphDao.flush();
                cnt = 0;
            }
        }
        graphDao.flush();
    }
}
