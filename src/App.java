import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    private static final int MIN_VERTEXES = 2;
    private static final int MAX_VERTEXES = 11;
    private static final Pattern GRAPH_BEGIN = Pattern.compile("Graph ([0-9]+), order ([0-9]+).");
    private static final String INPUT_GRAPH_PATH = "input/g";
    private static final String OUTPUT_PATH = "res/g"; //"/media/alexa/DATA/res/g";

    public static void main(String[] args) throws Exception {
        long begin = System.currentTimeMillis();

        final InvariantCounter worker = new EdgeConnectivity();
        BufferedReader reader;
        BufferedWriter writer = null;
        for (int n = MIN_VERTEXES; n <= MAX_VERTEXES; n++) {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(INPUT_GRAPH_PATH + n)));
            long a = System.currentTimeMillis();
            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OUTPUT_PATH + n + ".out")));
                System.out.println("Order " + n + " counting...");

                Graph graph;
                String line;
                while (reader.ready() && (line = reader.readLine()) != null) {
                    graph = readNextGraph(reader, line);
                    if (graph != null) {
                        writer.append(graph.toString());
                        writer.append(" Edge connectivity = " + worker.getInvariant(graph));
                        writer.append('\n');
                    }
                }
            } finally {
                long b = System.currentTimeMillis();
                System.out.println("Order " + n + " finished with time " + (b - a));
                if (writer != null) {
                    writer.close();
                }
                if (reader != null) {
                    reader.close();
                }
            }

        }

        long end = System.currentTimeMillis();
        System.out.println("Total time: " + (end - begin));
    }

    static Graph readNextGraph(BufferedReader reader, String s) throws Exception {
        Matcher matcher = GRAPH_BEGIN.matcher(s);
        if (matcher.matches()) {
            int n = Integer.parseInt(matcher.group(2));
            short[][] matrix = new short[n][n];
            for (int i = 0; i < n; i++) {
                String line = reader.readLine();
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
