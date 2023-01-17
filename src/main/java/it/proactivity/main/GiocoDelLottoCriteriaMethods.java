package it.proactivity.main;

import it.proactivity.model.Extraction;
import it.proactivity.model.Wheel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        cr.orderBy(
                cb.asc(root.get("city")),
                cb.desc(root.get("id")));
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
        cr.select(root).where(cb.gt(root.get("id"), 1))
                .where(cb.equal(root.get("city"), "Torino"));

        Query<Wheel> query = session.createQuery(cr);
        List<Wheel> results = query.getResultList();

        return results;
    }

    public static List<Extraction> getExtractionListBetWeenDate(Session session, LocalDate from, LocalDate to) {
        if(session == null)
            return null;

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Extraction> cr = cb.createQuery(Extraction.class);
        Root<Extraction> root = cr.from(Extraction.class);

        cr.select(root).where(cb.between(root.get("extractionDate"), from, to));

        Query<Extraction> query = session.createQuery(cr);
        List<Extraction> results = query.getResultList();

        return results;

    }

    public static Integer getLowerFirstNumberExtracted(Session session) {
        if(session == null)
            return null;

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Integer> cr = cb.createQuery(Integer.class);
        Root<Extraction> root = cr.from(Extraction.class);
        cr.select(cb.max(root.get("firstNumber")));

        Query<Integer> query = session.createQuery(cr);
        List<Integer> lower = query.getResultList();

        return lower.get(0);
    }

    public static void main(String args[]) {
        Session session = createSession();
        checkSession(session);

        System.out.println(getAllWheelWithCriteria(session));
        /*
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String stringDateFrom = "2023-01-14";
        String StringDateTo = "2023-01-17";

        LocalDate from = LocalDate.parse(stringDateFrom, formatter);
        LocalDate to = LocalDate.parse(StringDateTo, formatter);
        System.out.println(getExtractionListBetWeenDate(session, from, to));
        */
    }
}
