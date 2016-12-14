import afomina.graphs.count.IndependenceNumber;
import afomina.graphs.count.InvariantCounter;
import org.junit.Assert;
import org.junit.Test;

public class IndependenceNumberTest extends InvariantTest{

    @Test
    public void testIndependencyNumber() {
        InvariantCounter<Integer> indepCount = new IndependenceNumber();
        int result = indepCount.getInvariant(GRAPH);
        Assert.assertEquals(3, result);
    }
}
