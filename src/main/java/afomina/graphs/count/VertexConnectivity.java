package afomina.graphs.count;

import afomina.graphs.data.Graph;

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
        return dinic(g);
    }

    protected Integer menger(Graph g) {
        int min = Integer.MAX_VALUE;
        int order = g.getOrder();
        for (int u = 0; u < order; u++) {
            for (int v = 0; v < order; v++) {

            }
        }
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
