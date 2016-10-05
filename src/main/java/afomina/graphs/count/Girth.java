package afomina.graphs.count;

import afomina.graphs.data.Graph;

/**
 * Обхват
 */
public class Girth extends InvariantCounter<Integer> {

    @Override
    public Integer getInvariant(Graph g) {
        int n = g.getOrder();
        short[][] matrix = g.getMatrix();
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

    protected int cycle(int v, short[][] matrix, short[] color, int[] p) {
        color[v] = 1;

        for (int to = 0; to < matrix[v].length; ++to) {// count shortest circle = girth here
            if (matrix[v][to] == 1) {
                if (color[to] == 0) {
                    p[to] = v;
                    cycle(to, matrix, color, p);
                } else if (color[to] == 1) {
                    int end = v;
                    int begin = to;

                    int len = 0;
                    for (int k = end; k != begin; k = p[k]) {
                        len++;
                    }
                    return len;

                }
            }
        }
        color[v] = 2;
        return Integer.MAX_VALUE;
    }

}
