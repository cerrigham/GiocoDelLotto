package it.proactivity.test;

import it.proactivity.model.Extraction;
import it.proactivity.model.Wheel;
import it.proactivity.utility.GiocoDelLottoMethods;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GiocoDelLottoMethodsTest {
    private GiocoDelLottoMethods getGiocoDelLottoMethods() {
        return new GiocoDelLottoMethods();
    }

    private Session createSession() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        return session;
    }

    @Test
    public void getAllExtractionsPositiveTest() {
        Session session = createSession();
        List<Extraction> extractionList = getGiocoDelLottoMethods().getAllExtractions(session);

        assertFalse(session.isOpen());
        assertNotNull(extractionList);
        assertTrue(extractionList.size() > 0);
    }

    @Test
    public void getAllExtractionForOneDatePositiveTest() {
        Session session = createSession();
        List<Extraction> extractions = getGiocoDelLottoMethods().getAllExtractionForOneDate(session,"2023-01-12");

        assertFalse(session.isOpen());
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
        List<Extraction> extractions = getGiocoDelLottoMethods().getAllExtractionForOneDate(session,"2023-01-12");

        assertFalse(session.isOpen());
        extractions.stream()
                .forEach(e -> assertTrue(e.getExtractionDate().equals(date)));
    }

    @Test
    public void getAllExtractionsNegativeTest() {
        LocalDate date = LocalDate.of(2023,01,14);

        Session session = createSession();
        List<Extraction> extractions = getGiocoDelLottoMethods().getAllExtractionForOneDate(session,"2023-01-12");

        assertFalse(session.isOpen());
        assertFalse(session.isOpen());
        extractions.stream()
                .forEach(e -> assertFalse(e.getExtractionDate().equals(date)));
    }

    @Test
    public void getAllExtractionForOneDateNegativeNullDateTest() {
        Session session = createSession();
        List<Extraction> extractions = getGiocoDelLottoMethods().getAllExtractionForOneDate(session,null);

        assertFalse(session.isOpen());
        assertNull(extractions);
    }

    @Test
    public void getAllExtractionForOneDateNegativeWrongFormatDateTest() {
        Session session = createSession();
        List<Extraction> extractions = getGiocoDelLottoMethods().getAllExtractionForOneDate(session,"12-01-2023");

        assertFalse(session.isOpen());
        assertNull(extractions);
    }

    @Test
    public void getAllExtractionForOneDateNegativeEmptyDateTest() {
        Session session = createSession();
        List<Extraction> extractions = getGiocoDelLottoMethods().getAllExtractionForOneDate(session,"");

        assertFalse(session.isOpen());
        assertNull(extractions);
    }

    @Test
    public void getAllExtractionsFromAWheelPositiveTest() {
        Session session = createSession();
        Wheel milanoWheel = getGiocoDelLottoMethods().getMilanoWheel(session);
        List<Extraction> extractionList = getGiocoDelLottoMethods().getAllExtractionsFromWheel(session, milanoWheel);

        assertFalse(session.isOpen());
        assertNotNull(extractionList != null);
    }

    @Test
    public void getAllExtractionsFromAWheelNegativeTestNullWheel() {
        Session session = createSession();
        List<Extraction> extractionList = getGiocoDelLottoMethods().getAllExtractionsFromWheel(session, null);

        assertFalse(session.isOpen());
        assertNull(extractionList);
    }
    @Test
    public void deleteAllExtractionsTest() {
        Session session = createSession();

        List<Extraction> extractionList = new ArrayList<>();
        extractionList
        assertTrue(getGiocoDelLottoMethods().deleteAllExtractions(session) == true);
        assertFalse(session.isOpen());
    }
    @Test
    public void deleteAllExtractionsFromIdTest() {
        Session sesssion = createSession();
    }


}