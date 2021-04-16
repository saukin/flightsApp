package labo2_1;

/**
 *
 * @author Siarhei Saukin and Jerry Joseph
 */
public class Flight {
    
    private String flightNo;
    private String destination;
    private Date departureDate;
    private int nReservations;
    private final int N_RESERVATIONS_MAX = 340;

    public Flight(String flightNo, String destination, Date departureDate, int nReservations) {
        this.flightNo = flightNo;
        this.destination = destination;
        this.departureDate = departureDate;
        this.nReservations = nReservations;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public String getDestination() {
        return destination;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public int getnReservations() {
        return nReservations;
    }

    public int getnReservationsMax() {
        return N_RESERVATIONS_MAX;
    }
    
    
    public void setnReservations(int nReservations) {
        this.nReservations += nReservations;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }
    
    
    
    @Override
    public String toString() {
        return String.format("|%s\t|%20s  \t| %s\t| %d\n", flightNo, destination, departureDate.toString(), nReservations);
    }
    
    
    // format de toString pour souvgarder dans un fichier
    public String toFileString() {
        return String.format("%s;%s;%s;%d\n", flightNo, destination, 
                departureDate.toFileString(), nReservations);
    }

    
}
