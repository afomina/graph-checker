package afomina.graphs.count;

import afomina.graphs.data.Graph;

public class ChromeNumber extends InvariantCounter<Integer> {

    @Override
    public Integer getInvariant(Graph g) {
        int order = g.getOrder();
        boolean[][] matrix = g.getMatrix();
        int[] color = new int[order];
        int curColor = 0;
        for (int i = 0; i < order; i++) {
            color[i] = ++curColor;
            for (int j = 0; j < order; j++) {
                if (!matrix[i][j] && color[j] == 0) { //нет ребра i,j; j-неокрашенная

                    for (int k = 0; k < order; k++) {
                        matrix[i][k] = matrix[i][k] || matrix[j][k]; //соседи j добавляются к i
                    }

                    color[j] = curColor;

                }
            }
        }
        return curColor;
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
