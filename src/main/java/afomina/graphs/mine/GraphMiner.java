package afomina.graphs.mine;

import afomina.graphs.data.Graph;
import afomina.graphs.mine.condition.Condition;

import java.util.List;

public interface GraphMiner {
    List<Condition> mine(List<Graph> graphs);
}
