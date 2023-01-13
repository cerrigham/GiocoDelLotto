package it.proactivity.giocoDelLottoPredicate;

import it.proactivity.model.Extraction;

public class GiocoDelLottoPredicate {

    public static Boolean filterExtractionBySuperenalottoCity(Extraction extraction) {

        return extraction.getWheel().getCity().equals("Milano") || extraction.getWheel().getCity().equals("Palermo") ||
                extraction.getWheel().getCity().equals("Firenze") || extraction.getWheel().getCity().equals("Roma") ||
                extraction.getWheel().getCity().equals("Bari") || extraction.getWheel().getCity().equals("Napoli");
    }
}
