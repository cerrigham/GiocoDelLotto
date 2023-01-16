package it.proactivity.utility;

import it.proactivity.giocoDelLottoPredicate.GiocoDelLottoPredicate;
import it.proactivity.model.Extraction;
import it.proactivity.model.Wheel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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


    public Boolean insertExtractionIntoSuperenalotto(Session session, String date) {

        checkSession(session);
        LocalDate parsedDate = ParsingUtility.parseStringToDate(date);
        if (parsedDate == null) {
            endSession(session);
            return null;
        }

        List<Integer> firstNumberList = firstNumberList(session,date);

        Query query = session.createQuery("INSERT INTO superenalotto(bari, firenze, milano, napoli, palermo, roma,extraction_date)" +
                "VALUES(:bari, :firenze, :milano, :napoli, :palermo, :roma, :date)");
        query.setParameter("bari", firstNumberList.get(0));
        query.setParameter("firenze", firstNumberList.get(1));
        query.setParameter("milano", firstNumberList.get(2));
        query.setParameter("napoli", firstNumberList.get(3));
        query.setParameter("palermo", firstNumberList.get(4));
        query.setParameter("roma", firstNumberList.get(5));
        query.setParameter("date", parsedDate);

        int res = query.executeUpdate();

        endSession(session);

        if(res != 0)
            return true;
        else
            return false;

    }


    private Map<Integer,String> firstNumberList(Session session, String date) {
        if (session == null) {
            return null;
        }

        LocalDate parsedDate = ParsingUtility.parseStringToDate(date);

       Query query = session.createSQLQuery("SELECT e.first_number, w.city FROM extraction e, wheel w" +
               " WHERE w.city IN('Bari', 'Firenze', 'Milano', 'Napoli', 'Palermo', 'Roma') AND e.extraction_date = :date " +
               "ORDER BY w.city ");

       query.setParameter("date", parsedDate);

       List<Integer> firstNumberList = query.list();
       Map<Integer,String> firstNumbersMap = new HashMap<>();



       return ;
    }



}