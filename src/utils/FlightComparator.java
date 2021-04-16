package labo2_1.utils;

import java.util.Comparator;
import labo2_1.Flight;

/**
 *
 * @author Siarhei Saukin and Jerry Joseph
 * 
 * Comparator class pour ranger un tableux des Vols
 */
public class FlightComparator implements Comparator<Flight> {

    @Override
    public int compare(Flight o1, Flight o2) {
         if (o1 == null && o2 == null) {
            return 0;
        } else if (o2 == null) {
            return -1;
        } else if (o1 == null) {
            return 1;
        }
        return o1.getFlightNo().compareTo(o2.getFlightNo());
    }
    
}
