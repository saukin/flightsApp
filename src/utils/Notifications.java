/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labo2_1.utils;

import labo2_1.Flight;

/**
 *
 * @author Siarhei Saukin and Jerry Joseph
 */
public class Notifications {
    
    public final static String WRONG_INPUT = "Mauvaise entrée";
    public final static String NO_SUCH_COMPANY = "Il n'y a pas telle compagnie";
    public final static String NO_FILE = "Fichier n'est pas trouvé";
    public final static String IO_EXCEPTION = "Une erreur est survenue";
    public final static String NO_MORE_FLIGHTS = "Il n'y a pas des vols reservé par la compagnie";
    public final static String FLIGHT_EXISTS = "Ce vol deja existe";
    public final static String FLIGHT_NOT_EXISTS = "Ce vol n'existe pas";
    public final static String REALLY = "Désirez-vous vraiment retirer ce vol? O/N : ";
    public final static String TOO_MANY_PLACES_RESERVED = "Il n'y a pas assez des places pour la reservation";
    public final static String DATE_CHANGED = "La Date et changé";
    public final static String FLIGHT_ADDED = "Le vol est ajouté";
    public final static String FLIGHT_REMOVED = "Le vol est retiré";
    
    
    
    public static String toManyFlights(int diff) {
        return String.format("Il y a trop des vols. Il faut retires au moin %d vols "
                + "pour inserer un nouveau", diff);
    }
    
    
    public static String nSeatsLeft(Flight flight) {
        int nSeatsLeft = flight.getnReservationsMax() - flight.getnReservations();
        return String.format("Le vol numero: %s \nDestination : %s \nDepart le : %s"
                + "\nNombre des places restant : %d", 
                flight.getFlightNo(), 
                flight.getDestination(),
                flight.getDepartureDate(),
                nSeatsLeft);
    }
    
    public static String flightReserved(Flight flight, int nReserved) {
        return String.format("Vous avez reservé %d places pour le vol numero: %s "
                + "\nDestination : %s \nDepart le : %s. Merci et bon voyage!",
                nReserved,
                flight.getFlightNo(), 
                flight.getDestination(),
                flight.getDepartureDate());
    }
}
