import java.io.FileNotFoundException;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
//        Scanner scanner = new Scanner(new File("input.txt"));
        long begin = System.currentTimeMillis();
        final InvariantCounter worker = new EdgeConnectivity();
        for (int n = 2; n < 1000; n++) {
            //int n = 50;//scanner.nextInt();
            // for (int g = 0; g < 100; g++) {
            Graph graph = generateGraph(n);
            System.out.println("For " + n + " vertexes edge connectivity = " + worker.getInvariant(graph));
            //}
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time: " + (end - begin));
    }

    static Graph generateGraph(int n) {
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = 1;//scanner.nextInt();
            }
            matrix[i][i] = 0;
        }
        return new Graph(matrix);
    }
}
