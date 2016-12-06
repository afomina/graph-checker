package afomina.graphs.count;

import afomina.graphs.data.Graph;

import java.util.LinkedList;
import java.util.Queue;

public class VertexConnectivity extends InvariantCounter<Integer> {

    private int MAXN = 11;
    private int INF = Integer.MAX_VALUE;

    private int n = MAXN;//??
    private int[][] c = new int[MAXN][MAXN];
    private int[][] f = new int[MAXN][MAXN];
    private int s, t;
    private int[] d = new int[MAXN];
    private int[] ptr = new int[MAXN];
    private int[] q = new int[MAXN];

    @Override
    public Integer getInvariant(Graph g) {
        Integer res = menger(g);
        g.setVertexConnectivity(res);
        return res;
    }

    protected Integer menger(Graph g) {
        int order = g.getOrder();
        boolean[][] matrix = g.getMatrix();

        boolean[][] modMatrix = new boolean[2 * order][2 * order];
        for (int i = 0; i < order; i++) {
            modMatrix[2 * i][2 * i + 1] = modMatrix[2 * i + 1][2 * i] = true;
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
                boolean[][] temp = Graph;
                int flow = findFlow(temp, i, j);
                ans = Math.min(ans, flow);
            }
        }
        return (ans == Integer.MAX_VALUE ? 0 : ans);
    }

    private int findFlow(boolean[][] Graph, int s, int t) {
        int max_flow = 0;
        int[] p = new int[Graph.length];
        for (int i = 0; i < Graph.length; i++) {
            p[i] = -1;
        }
        while (bfs1(Graph, s, t, p)) {
            int min_capacity = Integer.MAX_VALUE;
            int pred = t;
            while (p[pred] != -1) {
                min_capacity = Math.min(min_capacity, Graph[p[pred]][pred]?1:0);
                pred = p[pred];
            }
            pred = t;
            while (p[pred] != -1) {
                if (min_capacity > 0) {
                    Graph[p[pred]][pred] = false;
                }
                pred = p[pred];
            }
            max_flow += min_capacity;
        }
        return max_flow;
    }

    boolean bfs1(boolean[][] Graph, int s, int t, int[] p) {
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
                if (Graph[v][to]  && !used[to]) {
                    used[to] = true;
                    p[to] = v;
                    q.add(to);
                }
            }
        }
        return false;
    }

    Integer dinic(Graph g) {
        int flow = 0;
        for (; ; ) {
            if (!bfs()) break;
            for (int i = 0; i < n; i++) {
                ptr[i] = 0;
            }
            int pushed;
            while ((pushed = dfs(s, INF)) != 0) {
                flow += pushed;
            }
        }
        g.setVertexConnectivity(flow);
        return flow;
    }

    boolean bfs() {
        int qh = 0, qt = 0;
        q[qt++] = s;
        for (int i = 0; i < n; i++) {
            d[i] = -1;
        }
        d[s] = 0;
        while (qh < qt) {
            int v = q[qh++];
            for (int to = 0; to < n; ++to)
                if (d[to] == -1 && f[v][to] < c[v][to]) {
                    q[qt++] = to;
                    d[to] = d[v] + 1;
                }
        }
        return d[t] != -1;
    }

    int dfs(int v, int flow) {
        if (flow == 0) return 0;
        if (v == t) return flow;
        for (int to = ptr[v]; to < n; ++to) {
            if (d[to] != d[v] + 1) continue;
            int pushed = dfs(to, Math.min(flow, c[v][to] - f[v][to]));
            if (pushed != 0) {
                f[v][to] += pushed;
                f[to][v] -= pushed;
                return pushed;
            }
        }
        return 0;
    }

}
