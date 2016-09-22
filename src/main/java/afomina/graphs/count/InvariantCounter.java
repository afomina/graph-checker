package afomina.graphs.count;


import afomina.graphs.data.Graph;

public interface InvariantCounter<T> {
    T getInvariant(Graph g);
}
