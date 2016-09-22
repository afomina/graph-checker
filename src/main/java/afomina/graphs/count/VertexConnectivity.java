package afomina.graphs.count;

import afomina.graphs.data.Graph;

/**
 * Created by alexandra on 10.09.2016.
 */
public class VertexConnectivity implements InvariantCounter<Integer> {

    int MAXN = 11;//g.getOrder(); // число вершин
    int INF = Integer.MAX_VALUE; // константа-бесконечность

    int n = MAXN;//??
    int[][] c = new int[MAXN][MAXN];
    int[][] f = new int[MAXN][MAXN];
    int s, t;
    int[] d = new int[MAXN];
    int[] ptr = new int[MAXN];
    int[] q = new int[MAXN];

    @Override
    public Integer getInvariant(Graph g) {//TODO!!!!

//        vvint f (n, vint(n));
//        for (;;)
//        {
//
//            vint from (n, -1);
//            vint q (n);
//            int h=0, t=0;
//            q[t++] = 0;
//            from[0] = 0;
//            for (int cur; h<t;)
//            {
//                cur = q[h++];
//                for (int v=0; v<n; v++)
//                    if (from[v] == -1 &&
//                            c[cur][v]-f[cur][v] > 0)
//                    {
//                        q[t++] = v;
//                        from[v] = cur;
//                    }
//            }
//
//            if (from[n-1] == -1)
//                break;
//            int cf = inf;
//            for (int cur=n-1; cur!=0; )
//            {
//                int prev = from[cur];
//                cf = min (cf, c[prev][cur]-f[prev][cur]);
//                cur = prev;
//            }
//
//            for (int cur=n-1; cur!=0; )
//            {
//                int prev = from[cur];
//                f[prev][cur] += cf;
//                f[cur][prev] -= cf;
//                cur = prev;
//            }
//
//        }
//
//        int flow = 0;
//        for (int i=0; i<n; i++)
//            if (c[0][i])
//                flow += f[0][i];
//
//        cout << flow;

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
