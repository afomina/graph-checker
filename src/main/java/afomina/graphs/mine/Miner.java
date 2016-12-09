package afomina.graphs.mine;

import afomina.graphs.data.Graph;
import afomina.graphs.data.GraphDao;
import ca.pfv.spmf.patterns.itemset_array_integers_with_count.Itemsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class Miner {

    @Autowired
    GraphDao graphDao;

    /**
     * Some association rule mining
     * See http://www.philippe-fournier-viger.com/spmf/index.php?link=download.php
     */
    public String mine() throws IOException {
        List<Graph> graphs = graphDao.findBySql("order < 7");
        FPGMiner miner = new FPGMiner();
        Itemsets patterns = miner.runAlgorithm(graphs, "mining-out.txt", 0.5);
        miner.printStats();
//        patterns.printItemsets(miner.getDatabaseSize());
        return "finished OK";
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
