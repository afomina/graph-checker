package afomina.graphs.mine;

import afomina.graphs.data.Graph;
import afomina.graphs.mine.condition.Condition;

import java.util.Collection;
import java.util.List;

public interface GraphMiner {
    Collection<Condition> mine(List<Graph> graphs);

    Collection<Condition> mine(List<Graph> graphs, Condition.INVARIANT main, Condition.INVARIANT a, Condition.INVARIANT b);
}
