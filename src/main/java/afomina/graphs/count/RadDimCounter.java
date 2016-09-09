package afomina.graphs.count;

import afomina.graphs.Graph;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by alexandra on 10.09.2016.
 */
public class RadDimCounter implements InvariantCounter {
    @Override
    public Object getInvariant(Graph g) {
        int n = g.getOrder(); // Количество вершин в графе
        int INF = Integer.MAX_VALUE; // Бесконечность
        int[][] d = new int[n][n]; // Дистанции в графе
        int[] e = new int[n]; // Эксцентриситет вершин
        Set<Integer> centr = new TreeSet<>(); // Центр графа
        int rad = INF; // Радиус графа
        int diam = -1; // Диаметр графа

// Алгоритм Флойда-Уоршелла
        for (int k = 0; k < n; k++) {
            for (int j = 0; j < n; j++) {
                for (int i = 0; i < n; i++) {
                    d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
                }
            }
        }

// Нахождение эксцентриситета
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                e[i] = Math.max(e[i], d[i][j]);
            }
        }

// Нахождение диаметра и радиуса
        for (int i = 0; i < n; i++) {
            rad = Math.min(rad, e[i]);
            diam = Math.max(diam, e[i]);
        }

        for (int i = 0; i < n; i++) {
            if (e[i] == rad) {
                centr.add(i);
            }
        }

        g.setRadius(rad);
        g.setDiametr(diam);
        return centr;
    }
}
