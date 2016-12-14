package afomina.graphs.count;

import afomina.graphs.data.Graph;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class IndependenceNumber extends InvariantCounter<Integer> {
    @Override
    public Integer getInvariant(Graph g) {
        int n = g.getOrder();
        boolean[][] matrix = g.getMatrix();
        int vectors = (int) Math.pow(2, n);
        int maxIndependent = 0;
        for (int i = 1; i < vectors - 1; i++) {
            List<Integer> vector = vector(i, n);
            if (checkIndependentSet(vector, matrix) && vector.size() > maxIndependent) {
                maxIndependent = vector.size();
            }
        }
        return maxIndependent;
    }

    private boolean checkIndependentSet(List<Integer> set, boolean[][] g) {
        for (int i = 0; i < set.size(); i++) {
            Integer vertex1 = set.get(i);
            for (int j = 0; j < set.size() && j != i; j++) {
                Integer vertex2 = set.get(j);
                if (g[vertex1][vertex2]) {
                    return false;
                }
            }
        }
        return true;
    }

    private List<Integer> vector(int i, int n) {
        BigInteger bigInteger = new BigInteger(i + "");
        String vector = bigInteger.toString(2);
        if (vector.length() < n) {
            int toAdd = n - vector.length();
            for (int j = 0; j < toAdd; j++) {
                vector = "0" + vector;
            }
        }

        List<Integer> set = new ArrayList<>();
        for (int j = 0; j < vector.length(); j++) {
            if (vector.charAt(j) == '1') { // j-vertex is in set
                set.add(j);
            }
        }
        return set;
    }
}
