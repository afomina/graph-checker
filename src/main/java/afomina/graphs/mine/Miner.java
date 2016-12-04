package afomina.graphs.mine;

//import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPGrowth;

import afomina.graphs.data.Graph;
import afomina.graphs.data.GraphDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Miner {

    @Autowired
    GraphDao graphDao;

    public String mine() {
//        AlgoFPGrowth algo = new AlgoFPGrowth(); //http://www.philippe-fournier-viger.com/spmf/index.php?link=download.php
//        algo.runAlgorithm() todo

        List<Graph> graphs = graphDao.findBySql("order < 7");
        boolean c2 = true;
        boolean c3 = true;
        for (Graph graph : graphs) {
            if (!checkCondition2(graph)) {
                c2 = false;
            }
            if (!checkCondition3(graph)) {
                c3 = false;
            }
            if (!c2 && !c3) {
                return "no true condition";
            }
        }

        String res = "";
        if (c2) res += "condition 2 ";
        if (c3) res += "Condition 3";
        return res; //"for all graph with vertex <= 7 is true (EXP + EDGE_CON <= GIRTH)";

    }

    //обхват, экспонент, реб связность - не равно
    //эксп + реб св <= обхват
    private boolean checkCondition1(Graph graph) {
        if (graph.getExp() != null && graph.getGirth() != 0) {
            return graph.getExp() + graph.getEdgeConnectivity() <= graph.getGirth();
        }
        return true;
    }

    private boolean checkCondition2(Graph graph) {
        if (graph.getExp() != null && graph.getGirth() != 0) {
            return graph.getExp() * graph.getEdgeConnectivity() <= graph.getGirth();
        }
        return true;
    }

    private boolean checkCondition3(Graph graph) {
        if (graph.getExp() != null && graph.getGirth() != 0) {
            return Math.pow(graph.getExp(), graph.getEdgeConnectivity()) <= graph.getGirth();
        }
        return true;
    }

//    private boolean check(int[] invariants, int n) {
//
//    }
}
