package afomina.graphs;

import afomina.graphs.count.ConnectivityCounter;
import afomina.graphs.count.InvariantCounter;
import afomina.graphs.count.RadDimCounter;
import afomina.graphs.data.Graph;
import afomina.graphs.data.GraphService;
import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
//@Configuration
@EnableAutoConfiguration
@ComponentScan("afomina.graphs")
//@EnableWebMvc
//@EnableWebSecurity
public class App {
    private static final int MIN_VERTEXES = 9;
    private static final int MAX_VERTEXES = 11;
    private static final int GRAPHS_TO_STORE = 5000;
    private static final Pattern GRAPH_BEGIN = Pattern.compile("Graph ([0-9]+), order ([0-9]+).");
    private static final Pattern EDGE_CON = Pattern.compile(".*Edge connectivity = ([0-9]+)");
    private static final String INPUT_GRAPH_PATH = "input/g";
    private static final String OUTPUT_PATH = "res/g"; //"/media/alexa/DATA/res/g";
    private static final GraphService graphService = GraphService.get();
    private static final Logger log = Logger.getLogger("App");
    private static final List<InvariantCounter> INVARIANTS = Arrays.asList(/*new VertexConnectivity(), */new ConnectivityCounter(), new RadDimCounter());

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

    private static void store(Graph graph, Session session) {
        graph.getCode();
        graph.getEdgeAmount();
        graph.getOrder();
//        graph.getEdgeConnectivity();
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
        dataSourceBuilder.url("jdbc:sqlite:graph.db");
        return dataSourceBuilder.build();
    }

    public static void main(String[] args) throws Exception {
//        setLogger();

//        parseAndStoreGraphs();
//        processGraphs();
        SpringApplication.run(App.class, args);
    }

//    static InvariantCounter vertexConnectivity = new VertexConnectivity(),cnnectivityCounter = new ConnectivityCounter(), radDimCounter =new RadDimCounter();
    private static void calcInvariants(Graph graph, Session session) {
        for (InvariantCounter invariant : INVARIANTS) {
            invariant.getInvariant(graph);
        }
        if (session == null ){//|| session.getTransaction().wasCommitted()) {
            session = graphService.openSession();
        }
        graphService.save(graph, session); //FIXME: this creates new graph instead .. :(
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
