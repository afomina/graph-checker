package afomina.graphs.mine;

import afomina.graphs.data.Graph;
import afomina.graphs.data.GraphDao;
import afomina.graphs.mine.condition.BruteMiner;
import afomina.graphs.mine.condition.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

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

    protected String stringify(Collection<Condition> conditions) {
        List<Condition> conditionList = new ArrayList<>(conditions);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < conditionList.size(); i++) {
            Condition condition = conditionList.get(i);

            boolean repeated = false;
            if (condition.getOperation().equals(Condition.OPERATION.SUM) || condition.getOperation().equals(Condition.OPERATION.MULT)) {
                for (int j = 0; j < i; j++) {
                    Condition another = conditionList.get(j);
                    if (another.getOperation().equals(condition.getOperation()) && another.getInvariants()[0].equals(condition.getInvariants()[0]) &&
                            (another.getInvariants()[1].equals(condition.getInvariants()[1]) && another.getInvariants()[2].equals(condition.getInvariants()[2])
                            ||
                            another.getInvariants()[1].equals(condition.getInvariants()[2]) && another.getInvariants()[2].equals(condition.getInvariants()[1]))) {
                        repeated = true;
                        break;
                    }
                }
            }

            if (!repeated) {
                buffer.append(condition);
                buffer.append("<br/>");
            }
        }
        return buffer.toString();
    }

}
