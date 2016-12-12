package afomina.graphs.mine.condition;

import afomina.graphs.data.Graph;
import afomina.graphs.mine.GraphMiner;
import afomina.graphs.mine.condition.Condition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BruteMiner implements GraphMiner {

    private static final Logger log = LoggerFactory.getLogger(GraphMiner.class);

    @Override
    public List<Condition> mine(List<Graph> graphs) {
        List<Condition> conditions = new ArrayList<>(graphs.size());

        for (Condition.OPERATION operation : Condition.OPERATION.values()) {
            for (Condition.INVARIANT a : Condition.INVARIANT.values()) {
                for (Condition.INVARIANT b : Condition.INVARIANT.values()) {
                    if (b != a) {
                        for (Condition.INVARIANT c : Condition.INVARIANT.values()) {
                            if (c != a && c != b) {

                                Condition condition = new Condition(operation, a, b, c);

                                for (Graph graph : graphs) {
                                    if (!condition.calculate(graph)) {
                                        break;
                                    }
                                }

                                if (condition.getResult()) {
                                    conditions.add(condition);
                                    log.debug(condition.toString());
                                }

                            }
                        }
                    }
                }
            }
        }
        return conditions;
    }

}
