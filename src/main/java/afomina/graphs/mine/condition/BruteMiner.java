package afomina.graphs.mine.condition;

import afomina.graphs.data.Graph;
import afomina.graphs.mine.GraphMiner;
import afomina.graphs.mine.condition.Condition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BruteMiner implements GraphMiner {

    private static final Logger log = LoggerFactory.getLogger(GraphMiner.class);

    @Override
    public List<Condition> mine(List<Graph> graphs) {
        List<Condition> conditions = new ArrayList<>(graphs.size());
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("brute-mine-" + System.currentTimeMillis() + ".txt"));
            try {
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
                                            log.error(condition.toString());
                                            writer.write(condition.toString());
                                            writer.newLine();
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            log.error("io exception", e);
        }
        return conditions;
    }

}
