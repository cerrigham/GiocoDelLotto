package it.proactivity.utility;

import it.proactivity.model.Extraction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class GiocoDelLottoMethods {

    public static List<Extraction> getAllExtractions(Session session) {

        if(!session.isOpen()) {
            session = createSession();
            session.getTransaction();
        }

        final String selectAllExtractions = "SELECT e FROM Extraction e";
        Query<Extraction> extractionQuery = session.createQuery(selectAllExtractions);
        List<Extraction> extractions = extractionQuery.list();

        endSession(session);

        return extractions;
    }

    public static List<Extraction> getAllExtractionForOneDate(Session session, String date) {

        if(!session.isOpen()) {
            session = createSession();
            session.getTransaction();
        }

        if (date == null || date.isEmpty()) {
            return null;
        }

        LocalDate parsedDate = ParsingUtility.parseStringToDate(date);
        if(parsedDate == null) {
            return null;
        }

        final String getAllTransactionForOneDate = "SELECT e FROM Extraction e LEFT JOIN FETCH e.wheel " +
                "WHERE e.extractionDate = :extractionDate";

        Query<Extraction> extractionQuery = session.createQuery(getAllTransactionForOneDate)
                .setParameter("extractionDate", parsedDate);

        endSession(session);

        return extractionQuery.list();
    }

    private static Session createSession() {
        SessionFactory sessionFactory = new Configuration().configure()
                .buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        return session;
    }
    private static void endSession(Session session) {
        session.getTransaction().commit();
        session.close();
    }
}
