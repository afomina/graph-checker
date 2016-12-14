import afomina.graphs.data.Graph;

public abstract class InvariantTest {

    /**
     * See sample graph and its invariants at
     * https://hog.grinvin.org/ViewGraphInfo.action?id=276
     */
    final static boolean[][] MATRIX = {{false, false, false, true, true},
            {false, false, false, true, true},
            {false, false, false, true, true},
            {true, true, true, false, true},
            {true, true, true, true, false}};

    static Graph GRAPH;

    static {
        GRAPH = new Graph(MATRIX);
        GRAPH.setOrder(MATRIX.length);
    }

}
