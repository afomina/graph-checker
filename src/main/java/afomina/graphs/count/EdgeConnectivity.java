package afomina.graphs.count;

import afomina.graphs.data.Graph;

import java.util.ArrayList;
import java.util.List;

public class EdgeConnectivity extends InvariantCounter<Integer> {
    public Integer getInvariant(Graph g) {
        int res = calc(g);
        g.setEdgeConnectivity(res);
        return res;
    }

    public int calc(Graph g) {
        return mincut(g.getMatrix());
    }

    private static final int MAXN = 1000;

    int mincut(boolean[][] graph) {
        int[][] g = new int[graph.length][graph.length];
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                g[i][j] = graph[i][j] ? 1 : 0;
            }
        }

        int n = g.length;
        int best_cost = Integer.MAX_VALUE;
        List<List<Integer>> v = new ArrayList<List<Integer>>(n);
        for (int i = 0; i < n; ++i) {
            v.add(new ArrayList<Integer>(i));
        }

        boolean[] exist = new boolean[MAXN];
        for (int i = 0; i < exist.length; i++) {
            exist[i] = true;
        }

        for (int ph = 0; ph < n - 1; ++ph) {
            boolean[] in_a = new boolean[MAXN];
            int[] w = new int[MAXN];
            int prev = 0;

            for (int it = 0; it < n - ph; ++it) {
                int sel = -1;
                for (int i = 0; i < n; ++i) {
                    if (exist[i] && !in_a[i] && (sel == -1 || w[i] > w[sel])) {
                        sel = i;
                    }
                }
                if (it == n - ph - 1) {
                    if (w[sel] < best_cost) {
                        best_cost = w[sel];
                    }
                    v.get(prev).addAll(v.get(sel));
                    for (int i = 0; i < n; ++i) {
                        g[prev][i] +=  g[sel][i];
                        g[i][prev] = g[prev][i];
                    }
                    exist[sel] = false;
                } else {
                    in_a[sel] = true;
                    for (int i = 0; i < n; ++i) {
                        w[i] += g[sel][i] ;
                    }
                    prev = sel;
                }
            }
        }

        return best_cost;
    }
}
