package afomina.graphs.mine;

import afomina.graphs.data.Graph;
import afomina.graphs.data.GraphDao;
import afomina.graphs.mine.condition.BruteMiner;
import afomina.graphs.mine.condition.Condition;
import afomina.graphs.mine.fpg.FPGMiner;
import ca.pfv.spmf.patterns.itemset_array_integers_with_count.Itemsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class Miner {

    @Autowired
    GraphDao graphDao;
    private static final Logger log = LoggerFactory.getLogger(Miner.class);

    public String mine() throws IOException {
        List<Graph> graphs = graphDao.findBySql("order < 10 and conn = 1 ");
        return stringify(new BruteMiner().mine(graphs));
    }

    public String mine(String sql, Condition.INVARIANT main, Condition.INVARIANT a, Condition.INVARIANT b) throws IOException {
        List<Graph> graphs;
        if (sql == null || sql.isEmpty()) {
            graphs = graphDao.findAll();
        } else {
            graphs = graphDao.findBySql(sql);
        }
        if (main == null) {
            return stringify(new BruteMiner().mine(graphs));
        }
        return stringify(new BruteMiner().mine(graphs, main, a, b));
    }

    protected String stringify(List<Condition> conditions) {
        StringBuffer buffer = new StringBuffer();
        for (Condition condition : conditions) {
            buffer.append(condition);
            buffer.append("<br/>");
        }
        return buffer.toString();
    }

    /**
     * Some association rule mining
     * See http://www.philippe-fournier-viger.com/spmf/index.php?link=download.php
     */
    public String mine(List<Graph> graphs) throws IOException {
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
