package afomina.graphs.mine;

import afomina.graphs.data.Graph;
import afomina.graphs.data.GraphDao;
import afomina.graphs.mine.condition.BruteMiner;
import afomina.graphs.mine.condition.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class Miner {

    @Autowired
    GraphDao graphDao;
    final BruteMiner bruteMiner = new BruteMiner();

    public String mine(String sql, Condition.INVARIANT main, Condition.INVARIANT a, Condition.INVARIANT b) throws IOException {
        List<Graph> graphs;
        if (sql == null || sql.isEmpty()) {
            Long count = graphDao.count();
            long pageCount = count / 100;
            if (count % 100 != 0) {
                pageCount++;
            }
            StringBuilder res = new StringBuilder();
            for (int page = 0; page < pageCount; page++) {
                graphs = graphDao.findAll(100, page);
                if (main == null) {
                    res.append(stringify(bruteMiner.mine(graphs)));
                } else {
                    res.append(stringify(bruteMiner.mine(graphs, main, a, b)));
                }
            }
            return res.toString();
        } else {
            graphs = graphDao.findBySql(sql);
        }
        if (main == null) {
            return stringify(bruteMiner.mine(graphs));
        }
        return stringify(bruteMiner.mine(graphs, main, a, b));
    }

    protected String stringify(List<Condition> conditions) {
        StringBuffer buffer = new StringBuffer();
        for (Condition condition : conditions) {
            buffer.append(condition);
            buffer.append("<br/>");
        }
        return buffer.toString();
    }

}
