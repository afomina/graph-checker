package afomina.graphs;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class GraphService {

    private static final GraphService INSTANCE = new GraphService();
    private static ServiceRegistry serviceRegistry;
    private static SessionFactory sessionFactory;

    private GraphService() {}
    
    /**
     * Returns a single <code>GraphService</code> instance.
     * 
     * @return the <code>GraphService</code> object
     */
    public static GraphService get() {
//    	Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
    	Configuration configuration = new Configuration().configure();
        serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
        				  .buildServiceRegistry();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return INSTANCE;
    }

    public void save(Graph graph) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.save(graph);
		transaction.commit();
    }

    public void update(Graph graph) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(graph);
        transaction.commit();
    }

    public void delete(Graph file) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(file);
        transaction.commit();
    }
    

}
