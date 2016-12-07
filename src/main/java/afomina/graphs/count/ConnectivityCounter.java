package afomina.graphs.count;

import afomina.graphs.data.Graph;

public class ConnectivityCounter extends InvariantCounter<Boolean> {
    @Override
    public Boolean getInvariant(Graph g) {
        boolean res = check(g);
        g.setConnected(res? 1: 0);
        return res;
    }

    public  static boolean check(Graph g) {
        boolean[] used = new boolean[g.getOrder()];
        dfs(1, g, used);

        for (boolean b : used) {
            if (!b) {
                return false;
            }
        }
       return true;
    }

}
