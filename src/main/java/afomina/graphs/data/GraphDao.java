package afomina.graphs.data;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("graphDao")
public class GraphDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Graph> find(Map<String, Object> userParams, int pageSize, int page) {
        Criteria criteria;

        Session session = entityManager.unwrap(Session.class);
        criteria = session.createCriteria(Graph.class);
        criteria.setFirstResult((page - 1) * pageSize);
        criteria.setMaxResults(pageSize);
        criteria.add(Restrictions.allEq(userParams));

        return (List<Graph>) criteria.list();
    }

    public List<Graph> findByOrder(Integer order) {
        Criteria criteria;

        Session session = entityManager.unwrap(Session.class);
        criteria = session.createCriteria(Graph.class);
        criteria.add(Restrictions.eq("order", order));

        return (List<Graph>) criteria.list();
    }

    public List<Graph> findByOrder(Integer order, int pageSize, int page) {
        Criteria criteria;

        Session session = entityManager.unwrap(Session.class);
        criteria = session.createCriteria(Graph.class);
        criteria.add(Restrictions.eq("order", order));
        criteria.setFirstResult(page * pageSize);
        criteria.setMaxResults(pageSize);

        return (List<Graph>) criteria.list();
    }

    public List<Graph> findByOrderAndNullInvariants(Integer order, int pageSize, int page) {
        Criteria criteria;

        Session session = entityManager.unwrap(Session.class);
        criteria = session.createCriteria(Graph.class);
        criteria.add(Restrictions.eq("order", order));
        criteria.add(Restrictions.or(Restrictions.isNull("chromeNumber"), Restrictions.isNull("vertexConnectivity")));
                criteria.setFirstResult(page * pageSize);
        criteria.setMaxResults(pageSize);

        return (List<Graph>) criteria.list();
    }

    public List<Graph> findByGirth(Integer girth) {
        Criteria criteria;

        Session session = entityManager.unwrap(Session.class);
        criteria = session.createCriteria(Graph.class);
        criteria.add(Restrictions.eq("girth", girth));

        return (List<Graph>) criteria.list();
    }

    public Graph findById(Integer id) {
        Session session = entityManager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Graph.class);
        criteria.add(Restrictions.eq("id", id));
        return (Graph) criteria.list().get(0);
    }

    public List<Graph> findBySql(String sql) {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("FROM Graph where " + sql).list();
    }

    public List<Graph> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Graph.class);
        return criteria.list();
    }

    public void save(Graph toStore) {
        Session session = entityManager.unwrap(Session.class);
        session.save(toStore);
    }

    public void flush() {
        Session session = entityManager.unwrap(Session.class);
        session.flush();
        session.clear();
    }

    public void start() {
        Session session = entityManager.unwrap(Session.class);
        session.getTransaction().begin();
    }

    public void close() {
        Session session = entityManager.unwrap(Session.class);
        session.getTransaction().commit();
        session.close();
    }

    public Long count(Map<String, Object> userParams) {
        Session session = entityManager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Graph.class)
                .add(Restrictions.allEq(userParams))
                .setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    public Long count(Integer order) {
        Session session = entityManager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Graph.class)
                .add(Restrictions.eq("order", order))
                .setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    public Long countNullInv(Integer order) {
        Session session = entityManager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Graph.class)
                .add(Restrictions.eq("order", order))
                .add(Restrictions.or(Restrictions.isNull("chromeNumber"), Restrictions.isNull("vertexConnectivity")))
                .setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }
}
