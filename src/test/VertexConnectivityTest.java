import afomina.graphs.count.VertexConnectivity;
import afomina.graphs.data.Graph;
import org.junit.Assert;
import org.junit.Test;


public class VertexConnectivityTest {
    final static boolean[][] MATRIX = {{false, false, false, true, true},
            {false, false, false, true, true},
            {false, false, false, true, true},
            {true, true, true, false, true},
            {true, true, true, true, false}};

    final static VertexConnectivity vertexConnectivity = new VertexConnectivity();


    @Test
    public void testVertCon() {
         Graph graph = new Graph(MATRIX);
        graph.setOrder(MATRIX.length);
        int con = vertexConnectivity.getInvariant(graph);
        Assert.assertEquals(2, con);
    }
}
