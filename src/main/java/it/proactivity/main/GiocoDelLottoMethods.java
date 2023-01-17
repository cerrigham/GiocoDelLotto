package it.proactivity.main;

import it.proactivity.model.Extraction;
import it.proactivity.model.Superenalotto;
import it.proactivity.model.Wheel;
import it.proactivity.utility.ParsingUtility;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.*;

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

    public void checkSession(Session session) {
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
        if (session == null)
            return null;

        checkSession(session);

        final String selectAllExtractions = "SELECT e FROM Extraction e";
        Query<Extraction> extractionQuery = session.createQuery(selectAllExtractions);
        List<Extraction> extractions = extractionQuery.list();

        endSession(session);

        return extractions;
    }

    public List<Extraction> getAllExtractionForOneDate(Session session, String date) {
        if (session == null)
            return null;

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
        if (session == null)
            return null;

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
        if (session == null)
            return null;

        checkSession(session);

        if (date == null || date.isEmpty() || wheelCityName == null || wheelCityName.isEmpty()) {
            endSession(session);
            return false;
        }


        final List<Wheel> wheelList = getWheelByCityName(session, wheelCityName);
        if (wheelList == null || wheelList.isEmpty()) {
            endSession(session);
            return false;
        }

        LocalDate parsedDate = ParsingUtility.parseStringToDate(date);
        if (parsedDate == null) {
            endSession(session);
            return false;
        }

        List<Integer> extractionList = new ArrayList<>(generateValidExtractionSequence());

        Extraction extraction = createExtraction(parsedDate, wheelList.get(0), extractionList);
        if (extraction != null) {
            session.persist(extraction);
            endSession(session);
            return true;
        } else {
            endSession(session);
            return false;
        }
    }

    public Boolean insertExtractionIntoSuperenalotto(Session session, String date) {
        if (session == null || date == null || date.isEmpty()) {
            endSession(session);
            return false;
        }

        checkSession(session);

        LocalDate parsedDate = ParsingUtility.parseStringToDate(date);

        if (parsedDate == null) {
            endSession(session);
            return false;
        }

        List<Integer> firstNumbersList = getFirstNumbersList(session, parsedDate);

        Query query = session.createSQLQuery("INSERT INTO superenalotto (bari, firenze, milano, napoli, palermo, roma, extraction_date)" +
                "VALUES (:bari, :firenze, :milano, :napoli, :palermo, :roma, :date)");

        query.setParameter("bari", firstNumbersList.get(0));
        query.setParameter("firenze", firstNumbersList.get(1));
        query.setParameter("milano", firstNumbersList.get(2));
        query.setParameter("napoli", firstNumbersList.get(3));
        query.setParameter("palermo", firstNumbersList.get(4));
        query.setParameter("roma", firstNumbersList.get(5));
        query.setParameter("date", parsedDate);

        int res = query.executeUpdate();

        endSession(session);

        if (res != 0)
            return true;
        else
            return false;
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

        final String queryString = "SELECT e " +
                "FROM Extraction e " +
                "WHERE e.id = :id";
        Query<Extraction> extractionQuery = session.createQuery(queryString)
                .setParameter("id", id);
        List<Extraction> extractionList = extractionQuery.getResultList();

        if (extractionList == null && extractionList.size() > 1) {
            endSession(session);
            return false;
        } else {
            session.delete(extractionList.get(0));
            endSession(session);
            return true;
        }
    }
    public Boolean insertTenSuperenalottoExtraction(Session session, String date) {
        if (session == null || date == null || date.isEmpty()) {
            endSession(session);
            return false;
        }

        checkSession(session);
        LocalDate parsedDate = ParsingUtility.parseStringToDate(date);
        if (parsedDate == null) {
            endSession(session);
            return false;
        }
        int counter = 1;
        int res = 0;
        while (counter <= 10) {

            Set<Integer> extractionsSet = generateValidExtractionSequenceForSuperenalotto();
            if (extractionsSet == null || extractionsSet.isEmpty()) {
                endSession(session);
                return false;
            }
            List<Integer> extractionsList = new ArrayList<>(extractionsSet);

            Query query = session.createSQLQuery("INSERT INTO superenalotto(bari, firenze, milano," +
                    " palermo, roma, napoli, extraction_date)" +
                    "VALUES(:bari, :firenze, :milano, :palermo, :roma, :napoli, :date)");

            query.setParameter("bari", extractionsList.get(0));
            query.setParameter("firenze", extractionsList.get(1));
            query.setParameter("milano", extractionsList.get(2));
            query.setParameter("napoli", extractionsList.get(3));
            query.setParameter("palermo", extractionsList.get(4));
            query.setParameter("roma", extractionsList.get(5));
            query.setParameter("date", parsedDate);

            res = query.executeUpdate();
            counter++;
        }

        endSession(session);

        if (res != 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<Integer> findMinMaxAvarageFromSuperenalottoExtraction(Session session) {
        if (session == null) {
            endSession(session);
            return null;
        }
        checkSession(session);

        final String getAllSuperenalottoExtraction = "SELECT s FROM Superenalotto s";
        Query<Superenalotto> query = session.createQuery(getAllSuperenalottoExtraction);


        List<Superenalotto> superenalottoList = query.getResultList();

        if (superenalottoList == null || superenalottoList.isEmpty()) {
            endSession(session);
            return null;
        }

        List<Integer> mediumValueList = getMediumValueFromSuperenalottoList(superenalottoList);

        if (mediumValueList == null || mediumValueList.isEmpty()) {
            endSession(session);
            return null;
        }

        List<Integer> resultList = new ArrayList<>();
        Integer maxAvarage = mediumValueList.stream().max(Integer::max).get();
        Integer minAvarage = mediumValueList.stream().min(Integer::min).get();
        resultList.add(maxAvarage);
        resultList.add(minAvarage);

        endSession(session);
        return resultList;

    }

    private List<Integer> getMediumValueFromSuperenalottoList(List<Superenalotto> list) {

        List<Integer> mediumValueList = new ArrayList<>();
        int counter = 0;
        for (Superenalotto s : list) {
         Integer sum = s.getBari();
         counter++;

         sum += s.getFirenze();
         counter++;

         sum += s.getMilano();
         counter++;

         sum += s.getRoma();
         counter++;

         sum += s.getNapoli();
         counter++;

         sum += s.getPalermo();
         counter++;
            Integer mediumValue = sum/counter;
            mediumValueList.add(mediumValue);

            counter = 0;
        }
        return mediumValueList;
    }
    private List<Integer> getFirstNumbersList(Session session, LocalDate date) {
        if (session == null) {
            return null;
        }

        Query query = session.createSQLQuery("SELECT e.first_number " +
                "FROM extraction e, wheel w " +
                "WHERE w.city IN('Bari', 'Firenze', 'Milano', 'Napoli', 'Palermo', 'Roma') " +
                "AND e.extraction_date = :date " +
                " AND e.wheel_id = w.id " +
                "ORDER BY w.city");

        query.setParameter("date", date);

        List<Integer> firstNumbers = query.getResultList();

        return firstNumbers;
    }

    private List<Wheel> getWheelByCityName(Session session, String wheelCityName) {
        if (session == null || wheelCityName == null || wheelCityName.isEmpty())
            return null;

        String queryString = "SELECT w FROM Wheel w " +
                "WHERE w.city = :city";
        Query<Wheel> query = session.createQuery(queryString).setParameter("city", wheelCityName);

        return query.getResultList();
    }

    private int generateValidNumberForExtraction() {
        Random random = new Random();
        return random.nextInt(MAX - MIN) + MIN;
    }

    private Set<Integer> generateValidExtractionSequence() {
        Set<Integer> extraction = new HashSet<>();

        while (extraction.size() != 5) {
            extraction.add(generateValidNumberForExtraction());
        }
        return extraction;
    }

    private Set<Integer> generateValidExtractionSequenceForSuperenalotto() {
        Set<Integer> extraction = new HashSet<>();

        while (extraction.size() != 6) {
            extraction.add(generateValidNumberForExtraction());
        }
        return extraction;
    }

    private Extraction createExtraction(LocalDate date, Wheel wheel, List<Integer> extractionList) {
        if (date == null || wheel == null || extractionList == null || extractionList.isEmpty())
            return null;

        Extraction extraction = new Extraction();
        extraction.setWheel(wheel);
        extraction.setExtractionDate(date);
        extraction.setFirstNumber(extractionList.get(0));
        extraction.setSecondNumber(extractionList.get(1));
        extraction.setThirdNumber(extractionList.get(2));
        extraction.setFourthNumber(extractionList.get(3));
        extraction.setFifthNumber(extractionList.get(4));

        return extraction;
    }


}