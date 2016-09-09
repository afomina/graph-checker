package afomina.graphs.count;


import afomina.graphs.Graph;

public interface InvariantCounter<T> {
    T getInvariant(Graph g);
}
