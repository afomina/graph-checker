package afomina.graphs;

import afomina.graphs.count.Girth;
import afomina.graphs.count.InvariantCounter;
import afomina.graphs.data.Graph;
import afomina.graphs.data.GraphService;
import afomina.graphs.mine.Miner;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("afomina.graphs")
public class App extends SpringBootServletInitializer {
    private static final int MIN_VERTEXES = 3;
    private static final int MAX_VERTEXES = 3;
    private static final int GRAPHS_TO_STORE = 5000;
    private static final Pattern GRAPH_BEGIN = Pattern.compile("Graph ([0-9]+), order ([0-9]+).");
    private static final Pattern EDGE_CON = Pattern.compile(".*Edge connectivity = ([0-9]+)");
    private static final String INPUT_GRAPH_PATH = "input/g";
    private static final String OUTPUT_PATH = "res/g";
    private static final GraphService graphService = GraphService.get();
    private static final Logger log = LoggerFactory.getLogger(App.class);
    private static final List<? extends InvariantCounter> INVARIANTS = Arrays.asList(new Girth());

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
                Session session = graphService.openSession();
                int cnt = 0;
                while (graphReader.ready() && (line = graphReader.readLine()) != null) {
                    graph = readNextGraph(graphReader, line);

                    if (graph != null) {
                        int edgeCon = readNextEdgeCon(edgeConReader);
                        graph.setEdgeConnectivity(edgeCon);
                        try {
                            store(graph, session);
                            if (++cnt == GRAPHS_TO_STORE) {
                                graphService.closeSession(session);
                                cnt = 0;
                            }
                        } catch (Exception e) {
                            log.error("exception when storing graph " + graph.toString(), e);
                        }
                    }
                }
            } finally {

            }

        }

        long end = System.currentTimeMillis();
        System.out.println("Total time: " + (end - begin));
    }

    private static void store(Graph graph, Session session) {
        graph.getCode();
        graph.getEdgeAmount();
        graph.getOrder();
       calcInvariants(graph, session);
    }

    private static int readNextEdgeCon(BufferedReader reader) throws IOException {
        String s = reader.readLine();
        Matcher matcher = EDGE_CON.matcher(s);
        if (matcher.find()) {
           return Integer.parseInt(matcher.group(1));
        }
        return -1;
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(App.class);
    }

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
        dataSourceBuilder.url("jdbc:sqlite:../webapps/graph-checker-1.0-SNAPSHOT/WEB-INF/classes/graph.db");
        return dataSourceBuilder.build();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }

    private static void calcInvariants(Graph graph, Session session) {
        for (InvariantCounter invariant : INVARIANTS) {
            invariant.getInvariant(graph);
        }
        if (session == null ){
            session = graphService.openSession();
        }
        graphService.save(graph, session);
    }

    static Graph readNextGraph(BufferedReader reader, String s) throws Exception {
        Matcher matcher = GRAPH_BEGIN.matcher(s);
        if (matcher.matches()) {
            int n = Integer.parseInt(matcher.group(2));
            boolean[][] matrix = new boolean[n][n];
            for (int i = 0; i < n; i++) {
                String line = reader.readLine();
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = (line.charAt(j) == '1' );
                }
            }
            return new Graph(matrix, s);
        }
        return null;
    }

}
