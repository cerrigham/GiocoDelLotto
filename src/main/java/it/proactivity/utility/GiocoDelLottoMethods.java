package it.proactivity.utility;

import it.proactivity.model.Extraction;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class GiocoDelLottoMethods {

    public static List<Extraction> showAllExtractions(Session session) {
        final String selectAllExtractions = "SELECT e FROM Extraction e";
        Query<Extraction> extractionQuery = session.createQuery(selectAllExtractions);
        List<Extraction> extractions = extractionQuery.list();

        return extractions;
    }

}
