package it.proactivity.utility;

public class TransformerUtility {

    public static Integer transformCityToId(String city) {

        if (city.equals("Bari")) {
            return 1;
        } else if (city.equals("Napoli")) {
            return 2;
        } else if(city.equals("Milano")) {
            return 3;
        } else if(city.equals("Palermo")) {
            return 4;
        } else if(city.equals("Roma")) {
            return 5;
        } else if (city.equals("Firenze")) {
            return 6;
        } else if (city.equals("Nazionale")) {
            return 7;
        } else if (city.equals("Cagliari")) {
            return 8;
        } else if (city.equals("Venezia")) {
            return 9;
        } else if(city.equals("Torino")) {
            return 10;
        } else {
            return 0;
        }
    }
}
