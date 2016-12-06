package afomina.graphs.count;


import afomina.graphs.data.Graph;

public abstract class InvariantCounter<T> {
   public abstract T getInvariant(Graph g);

    protected void dfs(int v, Graph g, boolean[] used) {
        used[v] = true;
        boolean[][] matrix = g.getMatrix();
        for (int i = 0; i < matrix[v].length; ++i) {
            if (matrix[v][i] && !used[i]) {
                dfs(i, g, used);
            }
        }
    }
}
