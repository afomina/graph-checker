package afomina.graphs.count;

import afomina.graphs.data.Graph;

public class ComponentCounter extends InvariantCounter<Integer> {

    @Override
    public Integer getInvariant(Graph g) {
        if (g.getComponents() != null && g.getComponents()!=0) {
            return g.getComponents();
        }
        int n = g.getOrder();
        boolean[] used = new boolean[n];
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (!used[i]) {
                res++;
                dfs(i, g, used);
            }
        }

        g.setComponents(res);
        if (res == 1) {
            g.setConnected(1);
        }
        return res;
    }

}
