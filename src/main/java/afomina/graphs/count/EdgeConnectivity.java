package afomina.graphs.count;

import afomina.graphs.data.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EdgeConnectivity extends InvariantCounter<Integer> {
    public Integer getInvariant(Graph g) {
        int res = 0;
        Graph tmp = g;
        ConnectivityCounter connectivityCounter = new ConnectivityCounter();
        while (tmp.isCon()) {
            short[][] m = tmp.getMatrix();
            for (int i = 0; i < tmp.getOrder(); i++) {
                for (int j = 0; j < tmp.getOrder(); j++) {
                    if (m[i][j] == 1) {
                        m[i][j] = 0;
                        m[j][i] = 0;
                        res++;
                        if (!connectivityCounter.getInvariant(tmp)) {
                            return res;
                        }
                    }
                }
            }

        }
        g.setEdgeConnectivity(res);
        return res;
    }

    private static final int MAXN = 1000;

    int mincut(short[][] graph) {
        short[][] g = Arrays.copyOf(graph, graph.length);
        for (int i = 0; i < g.length; i++) {
            g[i] = Arrays.copyOf(graph[i], graph[i].length);
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
                        g[prev][i] = g[i][prev] += g[sel][i];
                    }
                    exist[sel] = false;
                } else {
                    in_a[sel] = true;
                    for (int i = 0; i < n; ++i)
                        w[i] += g[sel][i];
                    prev = sel;
                }
            }
        }

        return best_cost;
    }
}
