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
import java.util.List;
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

        List<Extraction> extractions = getAllExtractionForOneDate(session,date);
        if (extractions == null) {
            return false;
        }

        List<Extraction> filteredList = extractions.stream()
                .filter(GiocoDelLottoPredicate::filterExtractionBySuperenalottoCity)
                .sorted(Comparator.comparing(e -> e.getWheel().getCity()))
                .toList();

        Session newSession = createSession();

        Query query = newSession.createSQLQuery("INSERT INTO superenalotto (bari, firenze, milano, napoli, " +
                "palermo, roma, extraction_date) " +
                "VALUES (:bari, :firenze, :milano, :napoli, :palermo, :roma, :extraction_date)")
                .setParameter("bari",filteredList.get(0).getFirstNumber())
                .setParameter("firenze",filteredList.get(1).getFirstNumber())
                .setParameter("milano",filteredList.get(2).getFirstNumber())
                .setParameter("napoli",filteredList.get(3).getFirstNumber())
                .setParameter("palermo",filteredList.get(4).getFirstNumber())
                .setParameter("roma",filteredList.get(5).getFirstNumber())
                .setParameter("extraction_date",filteredList.get(0).getExtractionDate());

        int res = query.executeUpdate();

        endSession(newSession);

        if (res != 0) {
            return true;
        }else {
            return false;
        }
    }





}