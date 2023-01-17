package it.proactivity.main;

import it.proactivity.model.Wheel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class GiocoDelLottoCriteriaMethods {

    private final static int MIN = 1;
    private final static int MAX = 90;

    public static Session createSession() {
        SessionFactory sessionFactory = new Configuration().configure()
                .buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        return session;
    }

    public static void checkSession(Session session) {
        if (!session.isOpen()) {
            session = createSession();
        }
        session.getTransaction();
    }

    public void endSession(Session session) {
        session.getTransaction().commit();
        session.close();
    }

    public static List<Wheel> getAllWheelWithCriteria(Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Wheel> cr = cb.createQuery(Wheel.class);
        Root<Wheel> root = cr.from(Wheel.class);
        // clausole == where condition
        cr.select(root);

        // every criteria will be transformed in a SQL query
        Query<Wheel> query = session.createQuery(cr);
        List<Wheel> results = query.getResultList();

        return results;
    }

    public static Wheel getWheelByName(Session session, String wheelName) {
        if(session == null || wheelName == null || wheelName == "")
            return null;

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Wheel> cr = cb.createQuery(Wheel.class);
        Root<Wheel> root = cr.from(Wheel.class);
        // clausole == where condition
        cr.select(root).where(cb.equal(root.get("city"), wheelName));

        Query<Wheel> query = session.createQuery(cr);
        List<Wheel> results = query.getResultList();
        if(results == null || results.isEmpty() || results.size() > 1)
            return null;

        return results.get(0);
    }

    public static List<Wheel> getWheelwithIdGreaterThenOne(Session session) {
        if(session == null)
            return null;

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Wheel> cr = cb.createQuery(Wheel.class);
        Root<Wheel> root = cr.from(Wheel.class);
        // clausole == where condition
        cr.select(root).where(cb.gt(root.get("id"), 1)); // where w.id > 1

        Query<Wheel> query = session.createQuery(cr);
        List<Wheel> results = query.getResultList();

        return results;
    }

    public static void main(String args[]) {
        Session session = createSession();
        checkSession(session);

        //System.out.println(getAllWheelWithCriteria(session));

        System.out.println(getWheelwithIdGreaterThenOne(session));

    }
}
