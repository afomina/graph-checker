package afomina.graphs.count;

import afomina.graphs.data.Graph;

public class Girth extends InvariantCounter<Integer> {

    @Override
    public Integer getInvariant(Graph g) {
        int n = g.getOrder();
        boolean[][] matrix = g.getMatrix();
        int minCycle = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            short[] color = new short[n];
            int[] p = new int[n];
            int cycle = cycle(i, matrix, color, p);
            minCycle = Math.min(minCycle, cycle);
        }

        g.setGirth(minCycle == Integer.MAX_VALUE? 0 : minCycle);
        return minCycle;
    }

    protected int cycle(int v, boolean[][] matrix, short[] color, int[] p) {
        color[v] = 1;

        for (int to = 0; to < matrix[v].length; ++to) {
            if (matrix[v][to] && to != p[v]) {
                if (color[to] == 0) {
                    p[to] = v;
                    return cycle(to, matrix, color, p);
                } else if (color[to] == 1) {
                    p[to] = v;
                    int end = v;
                    int begin = to;

                    int len = 0;
                    for (int k = end; k != begin; k = p[k]) {
                        len++;
                    }
                    return ++len;

                }
            }
        }
        color[v] = 2;
        return Integer.MAX_VALUE;
    }

}
