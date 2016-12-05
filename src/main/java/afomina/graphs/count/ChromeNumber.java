package afomina.graphs.count;

import afomina.graphs.data.Graph;

public class ChromeNumber implements InvariantCounter<Integer> {

    @Override
    public Integer getInvariant(Graph g) {
        int order = g.getOrder();
        short[][]matrix = g.getMatrix();
        int[] color = new int[order];
        for (int k = 2; k < order; k++) {
            int cur
            for (int i = 0; i < order; i++) {
                for (int j = 0; j < order; j++) {
                    if (matrix[i][j] == 1) {

                    }
                }
            }
        }
        return null;
    }

//    protected int[] getPermutation(int len) {
//        int[] perm = new int[len];
//        for (int i = 0; i < len; i++) {
//            for (int j = 0; j < len; j++) {
//                perm[j] = i;
//            }
//        }
//    }
}
