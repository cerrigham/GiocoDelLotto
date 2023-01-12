package it.proactivity.test;

import it.proactivity.model.Extraction;
import it.proactivity.utility.GiocoDelLottoMethods;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

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
    public void getAllExtractionsNegativeTest() {
        Session session = createSession();
        beginTransaction(session);

        List<Extraction> extractionList = GiocoDelLottoMethods.getAllExtractions(session);

        endTransaction(session);
        closeSession(session);

        assertFalse(extractionList.size() == 3);
    }

    @Test
    public void getAllExtractionsFromAWheelPositiveTest() {
        Session session = createSession();
        beginTransaction(session);

        List<Extraction> extractionList = GiocoDelLottoMethods.getAllExtractionsFromAWheel(session, 2l);
        endTransaction(session);
        closeSession(session);

        assertTrue(extractionList.size() != 0);
    }

    @Test
    public void getAllExtractionsFromAWheelNegativeTest() {
        Session session = createSession();
        beginTransaction(session);

        List<Extraction> extractionList = GiocoDelLottoMethods.getAllExtractionsFromAWheel(session, 2l);
        endTransaction(session);
        closeSession(session);

        assertFalse(extractionList.size() == 0);
    }




}