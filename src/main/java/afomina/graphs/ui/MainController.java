package afomina.graphs.ui;

import afomina.graphs.count.*;
import afomina.graphs.data.Graph;
import afomina.graphs.data.GraphDao;
import afomina.graphs.mine.Miner;
import afomina.graphs.mine.condition.Condition;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private static final int MIN_VERTEXES = 9;
    private static final int MAX_VERTEXES = 9;
    private static final int GRAPHS_TO_STORE = 100;
    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final List<? extends InvariantCounter> INVARIANTS = Arrays.asList(new VertexConnectivity(), new ChromeNumber(), new IndependenceNumber());
    @Autowired
    GraphDao graphDao;

    org.slf4j.Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private Miner graphMiner;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "more", method = RequestMethod.GET)
    public String more() {
        return "more";
    }

    @RequestMapping(value = "mining", method = RequestMethod.GET)
    public String mining() {
        return "mining";
    }

    @RequestMapping(value = "/mine", method = RequestMethod.GET)
    public String mine(Model model, @RequestParam(required = false) String sql,
                       @RequestParam(required = false) Condition.INVARIANT main,
                       @RequestParam(required = false) Condition.INVARIANT a,
                       @RequestParam(required = false) Condition.INVARIANT b) {
        String res = null;
        try {
            if (sql == null) {
                res = graphMiner.mine();
            } else {
                sql = replaceParams(sql);
                res = graphMiner.mine(sql, main, a, b);
            }
        } catch (IOException e) {
            res = "io exception";
            log.error("mine io exception", e);
        }
        log.error(res);
        model.addAttribute("msg", res);
        return "mineresults";
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
        model.addAttribute("params", userParams);
        int page = requestParams.containsKey("page") ? Integer.parseInt(requestParams.get("page")) : 1;
        model.addAttribute("curPage", page);
        List<Graph> graphs = graphDao.find(userParams, DEFAULT_PAGE_SIZE, page);

        model.addAttribute("graphs", graphs);
        Long count = graphDao.count(userParams);
        model.addAttribute("amount", count);
        long pageCount = count / DEFAULT_PAGE_SIZE;
        if (count % DEFAULT_PAGE_SIZE != 0) {
            pageCount++;
        }
        model.addAttribute("pageCount", pageCount);
        return "graphs";
    }

    @RequestMapping(value = "graphsMore", method = RequestMethod.GET)
    public String findGraphs(@RequestParam String sql, Model model) {
        sql = replaceParams(sql);
        List<Graph> graphs = graphDao.findBySql(sql);
        model.addAttribute("graphs", graphs);
        return "graphs";
    }

    private String replaceParams(String sql) {
        sql = sql.replaceAll("vertex", "order");
        sql = sql.replaceAll("edge", "edgeAmount");
        sql = sql.replaceAll("edgeCon", "edgeConnectivity");
        sql = sql.replaceAll("vertCon", "vertexConnectivity");
        sql = sql.replaceAll("isConnected", "connected");
        sql = sql.replaceAll("diameter", "diametr");
        sql = sql.replaceAll("isPrimitive", "primitive");
        sql = sql.replaceAll("isBipartite", "twoPartial");
        sql = sql.replaceAll("chromaticNum", "chromeNumber");
        sql = sql.replaceAll("independenceNum", "independenceNumber");
        return sql;
    }

    @RequestMapping(value = "/graphs/{id}", method = RequestMethod.GET)
    public String getGraph(@PathVariable("id") Integer id, Model model, HttpServletResponse response) {
        model.addAttribute("graph", graphDao.findById(id));
        response.getHeaders("X-Content-Type-Options").clear();
        return "graph";
    }

    @RequestMapping(value = "calc", method = RequestMethod.GET)
    public String calcInvariants(@RequestParam(required = false) Integer min, @RequestParam(required = false) Integer max) {
        if (min == null) min = MIN_VERTEXES;
        if (max == null) max = MAX_VERTEXES;
        processGraphs(min, max);
        return "index";
    }

    public void processGraphs(Integer min, Integer max) {
        int cnt = 0;
        List<Graph> graphs;
        for (int n = min; n <= max; n++) {
            try {
                Long count = graphDao.count(n);
                long pageCount = count / 100;
                if (count % 100 != 0) {
                    pageCount++;
                }
                for (int page = 0; page < pageCount; page++) {
                    graphs = graphDao.findByOrder(n, 100, page);
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
                e.printStackTrace();
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
