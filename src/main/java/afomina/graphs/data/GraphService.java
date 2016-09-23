package afomina.graphs.data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;

import java.util.Collections;
import java.util.List;

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
        serviceRegistry =  new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
        				  .build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return INSTANCE;
    }

    public Session openSession() {
        Session session = sessionFactory.openSession();
//        session.beginTransaction();
        return session;
    }

    public void closeSession(Session session) {
        session.getTransaction().commit();
    }

    public void save(Graph graph, Session session) {
        session.save(graph);
    }

    public void saveOne(Graph graph) {
		Session session = openSession();
		session.save(graph);
        closeSession(session);
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

    public List<Graph> findByOrder(Integer order) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            List<Graph> result = session.createCriteria(Graph.class)
                    .add(Restrictions.eq("order", order))
                    .list();
            session.getTransaction().commit();
            return result;
        } catch (NullPointerException e) {
            return Collections.emptyList();
        }
    }

//    public List<Graph> findByCriteria(Criteria criteria) {
//
//    }


}
