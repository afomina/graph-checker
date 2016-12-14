import afomina.graphs.count.VertexConnectivity;
import org.junit.Assert;
import org.junit.Test;


public class VertexConnectivityTest extends InvariantTest {

    final static VertexConnectivity vertexConnectivity = new VertexConnectivity();

    @Test
    public void testVertCon() {
        int con = vertexConnectivity.getInvariant(GRAPH);
        Assert.assertEquals(2, con);
    }
}
