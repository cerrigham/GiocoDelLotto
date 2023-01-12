package it.proactivity.utility;

import it.proactivity.model.Extraction;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

//a - mostrare tutte le estrazioni
//b - mostrare tutte le estrazioni. per una singola data
//c - mostrare tutte le estrazioni per una singola ruota
//d - inserire una estrazione per una certa data e una ruota (i numeri da inserire sono randomici da 1 a 90 e non possono essere duplicati)
//e - presa in input l'elenco delle estrazioni per una data, inserire la combinazione del superenalotto nell'apposita tabella
//f - cancellare tutte le estrazioni
//g - cancellare una estrazione (per id)
public class GiocoDelLottoMethods {

    public static List<Extraction> showAllExtractions(Session session) {
        final String selectAllExtractions = "SELECT e FROM Extraction";
        Query<Extraction> extractionQuery = session.createQuery(selectAllExtractions);
        List<Extraction> extractions = extractionQuery.list();
        return extractions;
    }

}
