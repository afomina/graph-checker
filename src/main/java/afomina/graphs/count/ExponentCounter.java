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
        int n = g.getOrder();
        Algebra algebra = new Algebra();
        short[][] matrix = g.getMatrix();
        double[][] doubleMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                doubleMatrix[i][j] = matrix[i][j];
            }
        }
        DoubleMatrix2D graphMatrix = DoubleFactory2D.dense.make(doubleMatrix);
        int exp = 1;
        int maxExp = n * n - 2 * n + 2;
        while (exp <= maxExp && !isMatrixEqual1(graphMatrix, n) /*!=(1 1 1)*/) {
            graphMatrix = algebra.mult(graphMatrix, graphMatrix);
            exp++;
        }
        if (!isMatrixEqual1(graphMatrix, n)) {
            g.setPrimitive(0);
        } else {
            g.setPrimitive(1);
            if (exp > 1) {
                exp--;
            }
            g.setExp(exp);
        }
        return exp;
    }

    private boolean isMatrixEqual1(DoubleMatrix2D matrix, int n) {
        for (int i = 0; i < matrix.rows(); i++) {
            for (int j = 0; j < matrix.columns(); j++) {
                if (matrix.getQuick(i, j) != 1) {
                    return false;
                }
            }
        }
        return true;
    }
}
