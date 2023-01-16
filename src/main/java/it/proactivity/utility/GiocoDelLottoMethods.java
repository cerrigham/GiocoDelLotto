package it.proactivity.utility;

import it.proactivity.model.Extraction;
import it.proactivity.model.Wheel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GiocoDelLottoMethods {

    private final static int MIN = 1;
    private final static int MAX = 90;

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

    public Boolean insertExtractionByDateAndWheel(Session session, String date, String wheelCityName) {
        checkSession(session);

        if (date == null || date.isEmpty() || wheelCityName == null || wheelCityName.isEmpty()) {
            endSession(session);
            return false;
        }

        // retrieve wheel by cityName and check
        final Wheel wheel = getWheelByCityName(session, wheelCityName);
       if(wheel == null) {
           endSession(session);
           return false;
       }

        LocalDate parsedDate = ParsingUtility.parseStringToDate(date);
        if (parsedDate == null) {
            endSession(session);
            return false;
        }

        Set<Integer> extractionSet = generateValidExtractionSequence();
        Extraction extraction = new Extraction();
        extraction.setExtractionDate(parsedDate);
        extraction.setWheel(wheel);
        //extraction.setFirstNumber(extractionSet.);
        //extraction.setSecondNumber();
        // ...


        session.persist(extraction);
        endSession(session);
        return true;
    }

    private Wheel getWheelByCityName(Session session, String wheelCityName) {
        if(session == null || wheelCityName == null || wheelCityName.isEmpty())
            return null;

        String queryString = "SELECT w FROM Wheel w " +
                "WHERE w.city = :city";
        Query<Wheel> query = session.createQuery(queryString).setParameter("city", wheelCityName);

        return query.getSingleResult();
    }

    private int generateValidNumberForExtraction() {
        Random random = new Random();
        return random.nextInt(MAX - MIN) + MIN;
    }

    private Set<Integer> generateValidExtractionSequence() {
        Set<Integer> extraction = new HashSet<>();

        while(extraction.size() != 5) {
            extraction.add(generateValidNumberForExtraction());
        }
        return extraction;
    }
}