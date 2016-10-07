package afomina.graphs.count;

import afomina.graphs.data.Graph;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;

/**
 * Created by alexandra on 05.10.2016.
 */
public class ExponentCounter extends InvariantCounter<Integer> {
    @Override
    public Integer getInvariant(Graph g) {
        Algebra algebra = new Algebra();
        DoubleMatrix2D graphMatrix = DoubleFactory2D.dense.make(double[][](g.getMatrix()));
        algebra.mult()
        return null;
    }
}
