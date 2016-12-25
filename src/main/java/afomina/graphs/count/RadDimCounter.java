package afomina.graphs.count;

import afomina.graphs.data.Graph;

import java.util.Set;
import java.util.TreeSet;

public class RadDimCounter extends InvariantCounter {
    @Override
    public Object getInvariant(Graph g) {
        if (g.getRadius() != null) {
            return g.getRadius();
        }
        int n = g.getOrder();
        int INF = Integer.MAX_VALUE/2;
        boolean[][] matrix = g.getMatrix();
        int[][] d = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i==j) {
                    continue;
                }
                if (!matrix[i][j]) {
                    d[i][j] = INF;
                } else {
                    d[i][j] = matrix[i][j]?1:0;
                }
            }
        }
        int[] e = new int[n];
        Set<Integer> centr = new TreeSet<>();
        int rad = INF;
        int diam = 0;

        for (int k = 0; k < n; k++) {
            for (int j = 0; j < n; j++) {
                for (int i = 0; i < n; i++) {
                    d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                e[i] = Math.max(e[i], d[i][j]);
            }
        }

        for (int i = 0; i < n; i++) {
            rad = Math.min(rad, e[i]);
            diam = Math.max(diam, e[i]);
        }

        for (int i = 0; i < n; i++) {
            if (e[i] == rad) {
                centr.add(i);
            }
        }

        g.setRadius(rad);
        g.setDiametr(diam);
        return centr;
    }
}
