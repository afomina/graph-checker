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
                for (Condition.INVARIANT a : Condition.INVARIANT.values()) {
                    mineFixedA(graphs, conditions, writer, a);
                }
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            log.error("io exception", e);
        }
        return conditions;
    }

    private void mineFixedA(List<Graph> graphs, List<Condition> conditions, BufferedWriter writer, Condition.INVARIANT a) throws IOException {
        for (Condition.INVARIANT b : Condition.INVARIANT.values()) {
            if (b != a) {
                mineFixedAB(graphs, conditions, writer, a, b);
            }
        }
    }

    private void mineFixedAB(List<Graph> graphs, List<Condition> conditions, BufferedWriter writer, Condition.INVARIANT a, Condition.INVARIANT b) throws IOException {
        boolean minusOne = false, divideTwo = false;

        for (Condition.OPERATION operation : Condition.OPERATION.oneParamOperations()) {
            if (minusOne && Condition.OPERATION.PLUS_ONE == operation || divideTwo && Condition.OPERATION.MULT_TWO == operation) {
                continue;
            }

            Condition condition = new Condition(operation, a, b, null);
            checkCondition(graphs, conditions, writer, condition);

            if (Condition.OPERATION.MINUS_ONE == operation && condition.getResult()) {
                minusOne = true;
            } else if (Condition.OPERATION.DIVIDE_TWO == operation && condition.getResult()) {
                divideTwo = true;
            }
        }

        for (Condition.INVARIANT c : Condition.INVARIANT.values()) {
            if (c != a && c != b) {
                mineABC(graphs, conditions, writer, a, b, c);
            }
        }
    }

    private void mineABC(List<Graph> graphs, List<Condition> conditions, BufferedWriter writer, Condition.INVARIANT a, Condition.INVARIANT b, Condition.INVARIANT c) throws IOException {
        for (Condition.OPERATION operation : Condition.OPERATION.twoParamOperations()) {
            checkCondition(graphs, conditions, writer, new Condition(operation, a, b, c));
        }
    }

    @Override
    public List<Condition> mine(List<Graph> graphs, Condition.INVARIANT main, Condition.INVARIANT a, Condition.INVARIANT b) {
        List<Condition> conditions = new ArrayList<>(graphs.size());
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("brute-mine-invariants-" + System.currentTimeMillis() + ".txt"));
            try {
                if (a == null) {
                    mineFixedA(graphs, conditions, writer, main);
                } else if (b == null) {
                    mineFixedAB(graphs, conditions, writer, main, a);
                } else {
                    mineABC(graphs, conditions, writer, main, a, b); //TODO: remove equal conditions like a+b=b+a
                }
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            log.error("io exception", e);
        }
        return conditions;
    }

    private void checkCondition(List<Graph> graphs, List<Condition> conditions, BufferedWriter writer, Condition condition) throws IOException {
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
