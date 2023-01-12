package it.proactivity.utility;

import it.proactivity.model.Extraction;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class GiocoDelLottoMethods {

    public static List<Extraction> getAllExtractions(Session session) {
        final String selectAllExtractions = "SELECT e FROM Extraction e";
        Query<Extraction> extractionQuery = session.createQuery(selectAllExtractions);
        List<Extraction> extractions = extractionQuery.list();

        return extractions;
    }

    public static List<Extraction> getAllExtractionForOneDate(Session session, String date) {

        if (date == null || date.isEmpty()) {
            return null;
        }

        LocalDate parsedDate = ParsingUtility.parseStringToDate(date);

        final String getAllTransactionForOneDate= "SELECT e FROM Extraction e LEFT JOIN FETCH e.wheel " +
                "WHERE e.extractionDate = :extractionDate";

        Query<Extraction> extractionQuery = session.createQuery(getAllTransactionForOneDate)
                .setParameter("extractionDate",parsedDate);

             return  extractionQuery.list();


    }

}
