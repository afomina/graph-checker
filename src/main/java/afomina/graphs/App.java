package afomina.graphs;

import java.io.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    private static final int MIN_VERTEXES = 9;
    private static final int MAX_VERTEXES = 11;
    private static final Pattern GRAPH_BEGIN = Pattern.compile("Graph ([0-9]+), order ([0-9]+).");
    private static final Pattern EDGE_CON = Pattern.compile(".*Edge connectivity = ([0-9]+)");
    private static final String INPUT_GRAPH_PATH = "input/g";
    private static final String OUTPUT_PATH = "res/g"; //"/media/alexa/DATA/res/g";
    private static final GraphService graphService = GraphService.get();
    private static final Logger log = Logger.getLogger("App");

    public static void parseAndStoreGraphs() throws Exception {
        long begin = System.currentTimeMillis();

        BufferedReader graphReader;
        BufferedReader edgeConReader;
        for (int n = MIN_VERTEXES; n <= MAX_VERTEXES; n++) {
            graphReader = new BufferedReader(new InputStreamReader(new FileInputStream(INPUT_GRAPH_PATH + n)));
            edgeConReader = new BufferedReader(new InputStreamReader(new FileInputStream(OUTPUT_PATH + n + ".out")));

            try {
                Graph graph;
                String line;
                while (graphReader.ready() && (line = graphReader.readLine()) != null) {
                    graph = readNextGraph(graphReader, line);

                    if (graph != null) {
                        int edgeCon = readNextEdgeCon(edgeConReader);
                        graph.setEdgeConnectivity(edgeCon);
                        try {
                            store(graph);
                        } catch (Exception e) {
                            log.log(Level.SEVERE, "exception when storing graph " + graph.toString(), e);
                        }
                    }
                }
            } finally {

            }

        }

        long end = System.currentTimeMillis();
        System.out.println("Total time: " + (end - begin));
    }

    private static void store(Graph graph) {
        graph.getCode();
        graph.getEdgeAmount();
        graph.getOrder();

        graph.getConnected();
        graph.getRadius();

        graph.getVertexConnectivity();
        graph.getEdgeConnectivity();
        graphService.save(graph);
    }

    private static int readNextEdgeCon(BufferedReader reader) throws IOException {
        String s = reader.readLine();
        Matcher matcher = EDGE_CON.matcher(s);
        if (matcher.find()) {
           return Integer.parseInt(matcher.group(1));
        }
        return -1;
    }

    public static void main(String[] args) throws Exception {
        setLogger();

        parseAndStoreGraphs();

    }

    private static void setLogger() {
        FileHandler fh;
        try {
            fh = new FileHandler("F:\\sasha\\data\\app.log");
            log.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

}
