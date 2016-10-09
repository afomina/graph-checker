package afomina.graphs.count;

import afomina.graphs.data.Graph;

public class ConnectivityCounter extends InvariantCounter<Boolean> {
    @Override
    public Boolean getInvariant(Graph g) {
        boolean[] used = new boolean[g.getOrder()];
        dfs(1, g, used);

        for (boolean b : used) {
            if (!b) {
                g.setConnected(0);
                return false;
            }
        }
        g.setConnected(1);
        return true;
    }

}
