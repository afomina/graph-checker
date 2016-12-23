package afomina.graphs.count;

import afomina.graphs.data.Graph;

import java.util.LinkedList;
import java.util.Queue;

public class VertexConnectivity extends InvariantCounter<Integer> {

    @Override
    public Integer getInvariant(Graph g) {
        if (g.getVertexConnectivity() != null) {
            return g.getVertexConnectivity();
        }
        Integer res = menger(g);
        g.setVertexConnectivity(res);
        return res;
    }

    protected Integer menger(Graph g) {
        int order = g.getOrder();
        boolean[][] matrix = g.getMatrix();

        boolean[][] modMatrix = new boolean[2 * order][2 * order];
        for (int i = 0; i < order; i++) {
            modMatrix[2 * i][2 * i + 1] = true;
            modMatrix[2 * i + 1][2 * i] = true;
            for (int j = i + 1; j < order; j++) {
                modMatrix[2 * i][2 * j + 1] = matrix[i][j];
                modMatrix[2 * j][2 * i + 1] = matrix[j][i];
            }
        }
        return edgeConnectivity(modMatrix, false);
    }

    private int edgeConnectivity(boolean[][] Graph, boolean isEdge) {
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < Graph.length; i += (isEdge ? 1 : 2)) {
            for (int j = i + (isEdge ? 1 : 3); j < Graph[i].length; j += (isEdge ? 1 : 2)) {
                int[][] temp = copyGraph(Graph);
                int flow = findFlow(temp, i, j);
                ans = Math.min(ans, flow);
            }
        }
        return (ans == Integer.MAX_VALUE ? 0 : ans);
    }

    private int[][] copyGraph(boolean[][] g) {
        int[][] r = new int[g.length][g.length];
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[i].length; j++) {
                r[i][j] = g[i][j]? 1: 0;
            }
        }
        return r;
    }

    private int findFlow(int[][] Graph, int s, int t) {
        int max_flow = 0;
        int[] p = new int[Graph.length];
        for (int i = 0; i < Graph.length; i++) {
            p[i] = -1;
        }
        while (bfs1(Graph, s, t, p)) {
            int min_capacity = Integer.MAX_VALUE;
            int pred = t;
            while (p[pred] != -1) {
                min_capacity = Math.min(min_capacity, Graph[p[pred]][pred]);
                pred = p[pred];
            }
            pred = t;
            while (p[pred] != -1) {
                Graph[p[pred]][pred] -= min_capacity;
                pred = p[pred];
            }
            max_flow += min_capacity;
        }
        return max_flow;
    }

    boolean bfs1(int[][] Graph, int s, int t, int[] p) {
        Queue<Integer> q = new LinkedList<>();
        q.add(s);
        boolean[] used = new boolean[Graph.length];
        used[s] = true;
        while (!q.isEmpty()) {
            int v = q.poll();
            if (v == t) {
                return true;
            }
            for (int to = 0; to < Graph[v].length; to++) {
                if (Graph[v][to] !=0 && !used[to]) {
                    used[to] = true;
                    p[to] = v;
                    q.add(to);
                }
            }
        }
        return false;
    }
}
