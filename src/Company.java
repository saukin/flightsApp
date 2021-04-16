package labo2_1;

import java.awt.Font;
import java.awt.HeadlessException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JTextArea;
import labo2_1.utils.Dialog;
import labo2_1.utils.FlightComparator;
import labo2_1.utils.Notifications;
import labo2_1.utils.Prompts;

/**
 *
 * @author Siarhei Saukin and Jerry Joseph
 */
public class Company {

    public static final int MAX_PLACES = 340;
    private final String companyName;
    private final int nFlightsMax;
    private Flight[] flights;
    private int nActiveFlights;
    private final Comparator flightComparator = new FlightComparator();

    public Company(String companyName, int nFlightsMax) {
        this.companyName = companyName;
        this.nFlightsMax = nFlightsMax;
        this.flights = getFlightsFromFile();
    }

    public String getCompanyName() {
        return companyName;
    }

    
    /**
     * 
     * Lire des vols du fichier et les mettre dans le tableu flights
     */
    private Flight[] getFlightsFromFile() {
        Flight[] tempFlights = null;
        try {
            List<String> file = Files.readAllLines(Paths.get("./" + companyName.replaceAll(" ", "_") + ".txt"));
            int existingFlights = file.size();

/*
    verifier si la quantité des vols dans un fichier est plus grande que nombre maximale des vols
    si oui l'instance de Company est crée avec toutes les vols de fihier mais il faudra
    retirer de vols supplimentaires 
*/
            if (existingFlights > this.nFlightsMax) {
                tempFlights = new Flight[existingFlights];
                int diff = existingFlights - nFlightsMax + 1;
                Dialog.errorMessage(Notifications.toManyFlights(diff), companyName);
            } else {
                tempFlights = new Flight[this.nFlightsMax];
            }

            for (String s : file)    {
                StringTokenizer tokens = new StringTokenizer(s, ";");
                while (tokens.hasMoreTokens()) {
                    String flightNo = tokens.nextToken();
                    String destination = tokens.nextToken();
                    Date date = getDate(tokens.nextToken(), tokens.nextToken(), tokens.nextToken());
                    int nRes = Integer.parseInt(tokens.nextToken());
                    Flight flight = new Flight(flightNo, 
                                            destination,
                                            date,
                                            nRes);
                    tempFlights[this.nActiveFlights++] = flight;
                }
            }
        } catch (FileNotFoundException fileException) {
            Dialog.errorMessage(Notifications.NO_FILE, "");
        } catch (IOException io) {
            Dialog.errorMessage(Notifications.IO_EXCEPTION, "");
        }
        return tempFlights;
    }

    public void listFlights(String windowTitle) {

        JTextArea jta = new JTextArea(30, 50);
        jta.append(Menu.LIST_FLIGHTS_HEADER);
        for (Flight f : this.flights) {
            if (f == null) {
                break;
            }
            jta.append(f.toString());
        }
        Font font = new Font("courier", Font.PLAIN, 12);
        jta.setEditable(false);
        jta.setFont(font);

        Dialog.showTextArea(jta, windowTitle);
    }

    
    public void addFlight(String windowTitle) {
        
        // verifier s'il y des place restant dans l'aeroplan
        if (this.nActiveFlights >= this.nFlightsMax) {
            int diff = nActiveFlights - nFlightsMax + 1;
            Dialog.errorMessage(Notifications.toManyFlights(diff), windowTitle);
            return;
        }
        
        String flightNo = getFlightNo(windowTitle);
        if (flightNo != null) {
            if (binarySearch(flightNo) >= 0) {
                Dialog.errorMessage(Notifications.FLIGHT_EXISTS, windowTitle);
                return;
            }
            String sDate = Dialog.input(Prompts.DATE, windowTitle);
            if (sDate != null) {
                Date date = getDate(sDate.trim().split("/"));   
                if (date != null) {
                    String destination = getDestination(windowTitle);
                    if (destination != null) {
                        Flight newFlight = new Flight(flightNo, destination, date, 0);
                        insertFlight(newFlight);
                        Dialog.confirmMessage(Notifications.FLIGHT_ADDED, windowTitle);
                    }
                }
            }
        }
    }

    public void removeFlight(String windowTitle) {
        int fIndex = getFlightIndex(windowTitle);
        if (fIndex >= 0) {
            String temp = Dialog.input(Notifications.REALLY, windowTitle);
            if (temp == null || temp.trim().toLowerCase().charAt(0) != Prompts.OUI) {
                return;
            }
            this.flights[fIndex] = null;
            Dialog.confirmMessage(Notifications.FLIGHT_REMOVED, windowTitle);
            // rearranger flights pour garder l'ordre pour binary search
            Arrays.sort(this.flights, flightComparator);
        }
        this.nActiveFlights--;
    }

    public void changeFlightDate(String windowTitle) {
        int fIndex = getFlightIndex(windowTitle);
        if (fIndex >= 0) {
            String sDate = Dialog.input(Prompts.DATE, windowTitle);
            if (sDate != null) {
                Date date = getDate(sDate.trim().replaceAll(" ", "").split("/"));
                this.flights[fIndex].setDepartureDate(date);
                Dialog.confirmMessage(Notifications.DATE_CHANGED, windowTitle);
            }
        }
    }

    public void reserveFlight(String windowTitle) {
        int fIndex = getFlightIndex(windowTitle);
        if (fIndex >= 0) {
            Dialog.confirmMessage(Notifications.nSeatsLeft(flights[fIndex]), windowTitle);
            try {
                String sValue = Dialog.input(Prompts.N_RESERVATIONS, windowTitle);
                if (sValue == null) {
                    return;    // cas Cancel
                }
                int nRes = Integer.parseInt(sValue.trim());
                if (nRes + flights[fIndex].getnReservations() > flights[fIndex].getnReservationsMax()) {
                    Dialog.errorMessage(Notifications.TOO_MANY_PLACES_RESERVED, sValue);
                    return;
                }
                this.flights[fIndex].setnReservations(nRes);
                Dialog.confirmMessage(Notifications.flightReserved(this.flights[fIndex], nRes), windowTitle);
            } catch (NumberFormatException nfe) {
                Dialog.errorMessage(Notifications.WRONG_INPUT, windowTitle);
            }
        }
    }
    
    public void saveToFile() throws IOException {
        StringBuilder sb = new StringBuilder();
        for (Flight f : this.flights) {
            if (f != null){
                sb.append(f.toFileString());
            }
        }
        byte[] lines = sb.toString().getBytes();
        Files.write(Paths.get("./" + companyName.replaceAll(" ", "_") + ".txt"), lines, StandardOpenOption.TRUNCATE_EXISTING);
    }
    
    private String getDestination(String windowTitle) {
        String destination;
        do {
            destination = Dialog.input(Prompts.DESTINATION, windowTitle);
        } while (destination != null && (destination.length() > 20 || destination.length() < 1));
        
        return destination;
    }

    private String getFlightNo(String header) {
        String flightNo;
        do {
            flightNo = Dialog.input(Prompts.FLIGHT_NO, header);
        } while (flightNo != null && (flightNo.length() != 5));
        
        return flightNo;
    }

    private void insertFlight(Flight newFlight) {
        this.flights[this.nActiveFlights] = newFlight;
        if (nActiveFlights < flights.length) {
            ++nActiveFlights;
        }
        // rearranger flights pour garder l'ordre pour binary search
        Arrays.sort(this.flights, flightComparator);
    }

    private int getFlightIndex(String windowTitle) {
        String flightNo = Dialog.input(Prompts.FLIGHT_NO, windowTitle);
        if (flightNo == null) {
            return -1;
        }
        int fIndex = binarySearch(flightNo);
        if (binarySearch(flightNo) < 0) {
            Dialog.errorMessage(Notifications.FLIGHT_NOT_EXISTS, windowTitle);
            return -1;
        }
        return fIndex;
    }

    private int binarySearch(String flightNo) {

        int start = 0;
        int end = this.nActiveFlights - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (this.flights[mid].getFlightNo().compareTo(flightNo) == 0) {
                return mid;
            } else if (this.flights[mid].getFlightNo().compareTo(flightNo) < 0) {
                start = mid + 1;
            } else if (this.flights[mid].getFlightNo().compareTo(flightNo) > 0) {
                end = mid - 1;
            }
        }
        return -1;
    }

    
    /**
     * 
     * Method pour construire l'instance de Date du input
     * 
     * @param sDate - tableu ou quelques valeurs en format string - jour, mois, an
     * @return 
     */
    private Date getDate(String... sDate) {
        Date date = null;
        try {
            int[] dateArray = new int[sDate.length];
            for (int i = 0; i < dateArray.length; i++) {
                dateArray[i] = Integer.parseInt(sDate[i]);
            }
            if (dateIsGood(dateArray)) {
                date = new Date(dateArray[0], dateArray[1], dateArray[2]);
            } else {
                Dialog.errorMessage(Notifications.WRONG_INPUT, "");
            }
        } catch (HeadlessException | NumberFormatException e) {
            Dialog.errorMessage(Notifications.WRONG_INPUT, "");
        }
        return date;
    }

    /**
     * 
     * Verifier si la date est correct: longeurs et valeurs des mois et jours sont dateArray
     * 
     * @param iDate
     * @return 
     */
    private boolean dateIsGood(int... dateArray) {
        int day = dateArray[0];
        int month = dateArray[1];
        int year = dateArray[2];
        boolean good = false;
        // les mois de 31 jour
        int[] monthList_31 = {1, 3, 5, 7, 8, 10, 12};
        // les mois de 30 jour
        int[] monthList_30 = {4, 6, 9, 11};
        // fevrier
        int february = 2;

        if (dateArray.length == 3) {
            if ((Arrays.binarySearch(monthList_31, month) >= 0 && (day > 0 && day <= 31))           // controle des mois de 31 jour
                    || (Arrays.binarySearch(monthList_30, month) >= 0 && (day > 0 && day <= 30))    // controle des mois de 30 jour
                    || (february == month && ((day > 0 && day <= 28)) || (isLeapYear(year) && day > 0 && day <= 29))) { // controle des mois de 28/29 jour
                good = true;
            }
        }
        
        return good;
    }
       
    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }
}
