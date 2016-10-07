package afomina.graphs.data;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

/**
 * Created by alexandra on 23.09.2016.
 */
@Repository("graphDao")
public class GraphDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Graph> find(Map<String, Object> userParams) {
        Criteria criteria;

        Session session = entityManager.unwrap(Session.class);
        criteria = session.createCriteria(Graph.class);
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

    public List<Graph> findById(Integer id) {
        Criteria criteria;

        Session session = entityManager.unwrap(Session.class);
        criteria = session.createCriteria(Graph.class);
        criteria.add(Restrictions.eq("id", id));

        return (List<Graph>) criteria.list();
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
}
