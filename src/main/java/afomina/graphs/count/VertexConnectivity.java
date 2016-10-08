package afomina.graphs.count;

import afomina.graphs.data.Graph;
import edu.uci.ics.jung.algorithms.flows.EdmondsKarpMaxFlow;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.Factory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexandra on 10.09.2016.
 */
public class VertexConnectivity extends InvariantCounter<Integer> {

    int MAXN = 11;
    int INF = Integer.MAX_VALUE;

    int n = MAXN;//??
    int[][] c = new int[MAXN][MAXN];
    int[][] f = new int[MAXN][MAXN];
    int s, t;
    int[] d = new int[MAXN];
    int[] ptr = new int[MAXN];
    int[] q = new int[MAXN];

    @Override
    public Integer getInvariant(Graph g) {
        Transformer capTransformer =
                new Transformer() {
                    @Override
                    public Object transform(Object o) {
                        return o;
                    }
                };
        Map<Integer, Double> edgeFlowMap = new HashMap<Integer, Double>();
        // This Factory produces new edges for use by the algorithm
        Factory edgeFactory = new Factory() {
            public Integer create() {
                return 1;
            }
        };
//        EdmondsKarpMaxFlow<Integer, Integer> alg = new EdmondsKarpMaxFlow(g, 0, g.getOrder()-1, capTransformer, edgeFlowMap, edgeFactory);
//        alg.evaluate();
        Integer maxFlow =0;// alg.getMaxFlow();
        g.setVertexConnectivity(maxFlow);
        return maxFlow;
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
