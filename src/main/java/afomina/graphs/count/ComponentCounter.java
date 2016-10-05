package afomina.graphs.count;

import afomina.graphs.data.Graph;

/**
 * Created by alexandra on 03.10.2016.
 */
public class ComponentCounter extends InvariantCounter<Integer> {

    @Override
    public Integer getInvariant(Graph g) {
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
        return res;
    }

}
