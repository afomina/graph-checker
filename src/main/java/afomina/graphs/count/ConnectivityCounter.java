package afomina.graphs.count;

import afomina.graphs.Graph;

/**
 * Created by alexandra on 10.09.2016.
 * TODO : test this bullshit
 */
public class ConnectivityCounter implements InvariantCounter<Boolean> {
    @Override
    public Boolean getInvariant(Graph g) {
        boolean[] used = new boolean[g.getOrder()];
        dfs(1, g, used);

        for (boolean b : used) {
            if (!b) {
                return false;
            }
        }
        return true;
    }


    private void dfs(int v, Graph g, boolean[] used) {
        used[v] = true;
        for (int i = 0; i < g.getMatrix()[v].length; ++i) {
            int to = g.getMatrix()[v][i];
            if (!used[to])
                dfs(to, g, used);
        }
    }

}
