import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EdgeConnectivity implements InvariantCounter {
    @Override
    public int getInvariant(Graph g) {
        return mincut(g.getMatrix());
    }

    private static final int MAXN = 1000;

    int mincut(short[][] graph) {
        short[][] g = Arrays.copyOf(graph, graph.length);
        for (int i = 0; i < g.length; i++) {
            g[i] = Arrays.copyOf(graph[i], graph[i].length);
        }

        int n = g.length;
        int best_cost = Integer.MAX_VALUE;
//        List<Integer> best_cut = Collections.emptyList();
        List<List<Integer>> v = new ArrayList<>(n);
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
//                        best_cut = v.get(sel);
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
