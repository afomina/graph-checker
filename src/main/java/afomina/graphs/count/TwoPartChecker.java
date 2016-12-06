package afomina.graphs.count;

import afomina.graphs.data.Graph;

public class TwoPartChecker extends InvariantCounter<Boolean> {
    @Override
    public Boolean getInvariant(Graph graph) {
        int n = graph.getOrder();
        boolean[][] g = graph.getMatrix();
        int[] part = new int[n];
        for (int i = 0; i < n; i++) {
            part[i] = -1;
        }

        boolean ok = true;
        int[] q = new int[n];

        int curPart = 1;
        for (int st = 0; st < n; ++st) {
            if (part[st] == -1) {
                int h = 0, t = 0;
                q[t++] = st;
                part[st] = 0;
                while (h < t) {
                    int v = q[h++];
                    for (int i = 0; i < g[v].length; ++i) {
                        if (g[v][i]) { //todo
                            int to = i;//g[v][i]; todo: what is this??
                            if (part[to] == -1) {
                                part[to] = curPart++;
                                q[t++] = to;
                            } else {
                                ok &= part[to] != part[v];
                            }
                        }
                    }
                }
            }
        }
        graph.setTwoPartial(ok? 1 : 0);
        return ok;
    }
}
