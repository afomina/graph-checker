package afomina.graphs.count;

import afomina.graphs.data.Graph;

public class ChromeNumber extends InvariantCounter<Integer> {

    @Override
    public Integer getInvariant(Graph g) {
        if (g.getChromeNumber() != null) {
            return g.getChromeNumber();
        }
        int order = g.getOrder();
        boolean[][] matrix = g.getMatrix();
        int[] color = new int[order];
        int curColor = 0;
        for (int i = 0; i < order; i++) {
            if (color[i] == 0) {
                color[i] = ++curColor;
                for (int j = 0; j < order; j++) {
                    if (i != j && !matrix[i][j] && color[j] == 0) { //нет ребра i,j; j-неокрашенная

                        for (int k = 0; k < order; k++) {
                            matrix[i][k] = matrix[i][k] || matrix[j][k]; //соседи j добавляются к i
                        }

                        color[j] = curColor;

                    }
                }
            }
        }
        g.setChromeNumber(curColor);
        return curColor;
    }

}
