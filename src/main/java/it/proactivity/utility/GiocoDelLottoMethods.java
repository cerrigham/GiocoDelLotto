package it.proactivity.utility;

import it.proactivity.model.Extraction;
import it.proactivity.model.Wheel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class GiocoDelLottoMethods {

    public Session createSession() {
        SessionFactory sessionFactory = new Configuration().configure()
                .buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        return session;
    }

    private void checkSession(Session session) {
        if (!session.isOpen()) {
            session = createSession();
        }
        session.getTransaction();
    }

    public void endSession(Session session) {
        session.getTransaction().commit();
        session.close();
    }

    public List<Extraction> getAllExtractions(Session session) {
        checkSession(session);

        final String selectAllExtractions = "SELECT e FROM Extraction e";
        Query<Extraction> extractionQuery = session.createQuery(selectAllExtractions);
        List<Extraction> extractions = extractionQuery.list();

        endSession(session);

        return extractions;
    }

    public List<Extraction> getAllExtractionForOneDate(Session session, String date) {
        checkSession(session);

        if (date == null || date.isEmpty()) {
            endSession(session);
            return null;
        }

        LocalDate parsedDate = ParsingUtility.parseStringToDate(date);
        if (parsedDate == null) {
            endSession(session);
            return null;
        }

        final String getAllTransactionForOneDate = "SELECT e FROM Extraction e LEFT JOIN FETCH e.wheel " +
                "WHERE e.extractionDate = :extractionDate";

        Query<Extraction> extractionQuery = session.createQuery(getAllTransactionForOneDate)
                .setParameter("extractionDate", parsedDate);

        List<Extraction> extractions = extractionQuery.getResultList();

        endSession(session);

        return extractions;
    }

    public List<Extraction> getAllExtractionsFromWheel(Session session, Wheel wheel) {
        if (wheel == null) {
            endSession(session);
            return null;
        }
        final String selectAllExtractionsFromWheel = "SELECT e FROM Extraction e WHERE e.wheel = :wheel";
        List<Extraction> extractions = session.createQuery(selectAllExtractionsFromWheel)
                .setParameter("wheel", wheel).list();

        endSession(session);
        return extractions;
    }

    public Wheel getMilanoWheel(Session session) {
        final String MILANO_CITY = "Milano";
        final String milanoWheelQueryString = "SELECT w " +
                "FROM Wheel w " +
                "WHERE w.city = :milanoCity";
        Query<Wheel> wheelQuery = session.createQuery(milanoWheelQueryString)
                .setParameter("milanoCity", MILANO_CITY);
        return wheelQuery.getSingleResult();
    }

    public Boolean deleteAllExtractions(Session session) {
        if (session == null) {
            return false;
        }
        checkSession(session);

        final String queryString = "DELETE FROM Extraction e";
        Query<Extraction> extractionQuery = session.createQuery(queryString);
        extractionQuery.executeUpdate();
        endSession(session);
        return true;
    }

    public Boolean deleteAllExtractionsFromId(Session session, Long id) {
        if (session == null || id == null) {
            return false;
        }
        checkSession(session);

        final String queryString = "SELECT e FROM Extraction e WHERE e.id = :id";
        Query<Extraction> extractionQuery = session.createQuery(queryString)
                .setParameter("id", id);
        if (extractionQuery != null) {
            session.delete(extractionQuery);
        }
        endSession(session);
        return true;
    }


}