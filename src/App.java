import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    private static final int MIN_VERTEXES = 5;
    private static final int MAX_VERTEXES = 5;
    private static final Pattern GRAPH_BEGIN = Pattern.compile("Graph ([0-9]+), order ([0-9]+).");

    public static void main(String[] args) throws FileNotFoundException {
        long begin = System.currentTimeMillis();

        final InvariantCounter worker = new EdgeConnectivity();
        for (int n = MIN_VERTEXES; n <= MAX_VERTEXES; n++) {
            Scanner scanner = new Scanner(new File("g" + n));
            PrintWriter writer = new PrintWriter("g" + n + ".out");
            while (scanner.hasNext()) {
                Graph graph = readNextGraph(scanner);
                if (graph != null) {
                    writer.println(graph.toString() + " Edge connectivity = " + worker.getInvariant(graph));
                }
            }
            writer.close();
        }

        long end = System.currentTimeMillis();
        System.out.println("Total time: " + (end - begin));
    }

    static Graph readNextGraph(Scanner scanner) {
        String s = scanner.nextLine();
        Matcher matcher = GRAPH_BEGIN.matcher(s);
        if (matcher.matches()) {
            int n = Integer.parseInt(matcher.group(2));
            short[][] matrix = new short[n][n];
            for (int i = 0; i < n; i++) {
                String line = scanner.next();
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = (short) (line.charAt(j) == '1' ? 1 : 0);
                }
            }
            return new Graph(matrix, s);
        }
        return null;
    }

    static Graph generateFullGraph(short n) {
        short[][] matrix = new short[n][n];
        for (short i = 0; i < n; i++) {
            for (short j = 0; j < n; j++) {
                matrix[i][j] = 1;
            }
            matrix[i][i] = 0;
        }
        return new Graph(matrix);
    }
}
