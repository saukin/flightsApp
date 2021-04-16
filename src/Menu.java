package labo2_1;

import java.io.IOException;
import labo2_1.Company;
import labo2_1.utils.Dialog;
import labo2_1.utils.Notifications;


/**
 *
 * @author Siarhei Saukin and Jerry Joseph
 */
public class Menu {
    
    private final static String MENU_HEADER = "GESTION DES VOLS";
    private final static String FLIGHTS_LIST = "Liste des vols";
    private final static String ADD_FLIGHT = "Ajout d'un vol";
    private final static String REMOVE_FLIGHT = "Retrait d'un vol";
    private final static String CHANGE_FLIGHT = "Modification de la date de départ";
    private final static String RESERVATION = "Réservation d'un vol";
    private final static String EXIT = "Terminer";
    private final static String CHOOSE = "Faites votre choix";
    public final static String LIST_FLIGHTS_HEADER = String.format("\tLISTE DES VOLS\n|%s\t|%20s  \t| %s\t| %s\n"
            +       "-----------------------------------------------------------------------------------------\n", 
                    "Numéro", "destination", "Date départ", "Reservations");
    
        
    private final static String MENU = "\t" + MENU_HEADER  
                                     + "\n1. " + FLIGHTS_LIST
                                     + "\n2. " + ADD_FLIGHT
                                     + "\n3. " + REMOVE_FLIGHT 
                                     + "\n4. " + CHANGE_FLIGHT
                                     + "\n5. " + RESERVATION 
                                     + "\n0. " + EXIT
                                     + "\t" + CHOOSE;
            
    /**
     * Method pour traiter les operations avec des vols d'une compagnie en passant 
     * aux fenetres dialog des message et titles appropries
     *  
     * @param company instance de Company class pour traiter
     * @throws IOException 
     */
    public static void doMenu(Company company) throws IOException {
        int choice;
        do {
            try {
                String sValue = Dialog
                        .input(MENU, company.getCompanyName());
                int i = 1 + 2;
                if (sValue == null) {
                    company.saveToFile();
                    break;      // cas de Cancel
                }
                choice = Integer.parseInt(sValue.trim());
            } catch (NumberFormatException | NullPointerException nfe) {
                // passe pour avoir notification de default case et nouvel input-
                choice = -1;
            }
            switch (choice) {
                case 1: 
                    company.listFlights(FLIGHTS_LIST);
                    break;
                case 2:
                    company.addFlight(ADD_FLIGHT);
                    break;
                case 3:
                    company.removeFlight(REMOVE_FLIGHT);
                    break;
                case 4:
                    company.changeFlightDate(CHANGE_FLIGHT);
                    break;
                case 5:
                    company.reserveFlight(RESERVATION);
                    break;
                case 0:
                    company.saveToFile();
                    System.exit(0);
                default:
                    Dialog.errorMessage(Notifications.WRONG_INPUT, MENU_HEADER);
            }
        } while (choice != 0);
        
    }
    
    
}
