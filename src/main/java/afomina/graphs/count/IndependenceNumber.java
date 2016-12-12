package afomina.graphs.count;

import afomina.graphs.data.Graph;

public class IndependenceNumber extends InvariantCounter<Integer> {
    @Override
    public Integer getInvariant(Graph g) {
        int vectors = (int) Math.pow(2, g.getOrder());
        for (int i = 0; i < vectors - 1; i++) {

        }
        return null; //TODO
    }
}
