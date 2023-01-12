package it.proactivity.utility;

import it.proactivity.model.Extraction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
    public void showAllExtractionsTest() {
        Session session = createSession();
        beginTransaction(session);

        List<Extraction> extractionList = GiocoDelLottoMethods.showAllExtractions(session);

        endTransaction(session);
        closeSession(session);

        assertNotNull(extractionList);
        assertTrue(extractionList.size() > 0);
    }
}