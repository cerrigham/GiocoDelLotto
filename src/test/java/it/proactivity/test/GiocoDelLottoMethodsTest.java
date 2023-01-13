package it.proactivity.test;

import it.proactivity.model.Extraction;
import it.proactivity.utility.GiocoDelLottoMethods;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GiocoDelLottoMethodsTest {

    private Session createSession() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        return session;
    }

    private void closeSession(Session session) {
        session.close();
    }

    private static Transaction beginTransaction(Session session) {
        return session.getTransaction();
    }

    private static void endTransaction(Session session) {
        session.getTransaction().commit();
    }

    @Test
    public void getAllExtractionsPositiveTest() {
        Session session = createSession();
        beginTransaction(session);

        List<Extraction> extractionList = GiocoDelLottoMethods.getAllExtractions(session);

        endTransaction(session);
        closeSession(session);

        assertNotNull(extractionList);
        assertTrue(extractionList.size() > 0);
    }

    @Test
    public void getAllExtractionForOneDatePositiveTest() {
        Session session = createSession();
        beginTransaction(session);

        List<Extraction> extractions = GiocoDelLottoMethods.getAllExtractionForOneDate(session,"2023-01-12");

        endTransaction(session);
        closeSession(session);

        assertNotNull(extractions);
        assertTrue(extractions.size() > 0);
        extractions.stream()
                .forEach(e -> assertTrue(!(e.getFirstNumber().equals(null) && !(e.getSecondNumber().equals(null) &&
                        !(e.getThirdNumber().equals(null) && !(e.getFourthNumber().equals(null) &&
                                !(e.getFifthNumber().equals(null))))))));

    }

    @Test
    public void checkExtractionListDatePositive() {

        LocalDate date = LocalDate.of(2023,01,12);
        Session session = createSession();
        beginTransaction(session);

        List<Extraction> extractions = GiocoDelLottoMethods.getAllExtractionForOneDate(session,"2023-01-12");

        endTransaction(session);
        closeSession(session);

        extractions.stream()
                .forEach(e -> assertTrue(e.getExtractionDate().equals(date)));
    }

    @Test
    public void getAllExtractionsNegativeTest() {

        LocalDate date = LocalDate.of(2023,01,14);
        Session session = createSession();
        beginTransaction(session);

        List<Extraction> extractions = GiocoDelLottoMethods.getAllExtractionForOneDate(session,"2023-01-12");

        endTransaction(session);
        closeSession(session);

        extractions.stream()
                .forEach(e -> assertFalse(e.getExtractionDate().equals(date)));
    }

    @Test
    public void getAllExtractionsNullListFromNullInputTest() {
        Session session = createSession();
        beginTransaction(session);

        List<Extraction> extractions = GiocoDelLottoMethods.getAllExtractionForOneDate(session,null);

        endTransaction(session);
        closeSession(session);

        assertNull(extractions);
    }

    @Test
    public void getAllExtractionsNullListFromWrongFormatDateTest() {
        Session session = createSession();
        beginTransaction(session);

        List<Extraction> extractions = GiocoDelLottoMethods.getAllExtractionForOneDate(session,"12-01-2023");

        endTransaction(session);
        closeSession(session);
    }
}