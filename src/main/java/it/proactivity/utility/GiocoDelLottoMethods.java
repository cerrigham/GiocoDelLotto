package it.proactivity.utility;

import it.proactivity.model.Extraction;
import it.proactivity.model.Wheel;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class GiocoDelLottoMethods {

    public static List<Extraction> getAllExtractions(Session session) {
        final String selectAllExtractions = "SELECT e FROM Extraction e";
        Query<Extraction> extractionQuery = session.createQuery(selectAllExtractions);
        List<Extraction> extractions = extractionQuery.list();

        return extractions;
    }

    public static List<Extraction> getAllExtractionsFromAWheel(Session session, Wheel wheel) {
        if (wheel == null) {
            return null;
        }
        final String selectAllExtractionsFromWheel = "SELECT e FROM Extraction e WHERE e.wheel = :wheel";
        List<Extraction> extractions = session.createQuery(selectAllExtractionsFromWheel)
                .setParameter("wheel", wheel).list();
        return extractions;
    }

    public static Wheel getMilanoWheel(Session session) {
       final String MILANO_CITY = "Milano";
       final String milanoWheelQueryString = "SELECT w " +
               "FROM Wheel w " +
               "WHERE w.city = :milanoCity";
       Query<Wheel> wheelQuery = session.createQuery(milanoWheelQueryString)
               .setParameter("milanoCity", MILANO_CITY);
       return wheelQuery.getSingleResult();
    }

}
