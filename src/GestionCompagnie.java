package labo2_1;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import labo2_1.utils.Dialog;
import labo2_1.utils.Notifications;
import labo2_1.utils.Prompts;

/**
 *
 * @author Siarhei Saukin and Jerry Joseph
 */
public class GestionCompagnie {
    
    private final Company company;

    public GestionCompagnie() {
        this.company = new Company(getCompanyName(), getMaxFlights());
    }
    
    private String getCompanyName() {
        String companyName = Dialog.input(Prompts.COMPANY_NAME, "Welcome");
        if (companyName == null) {   //case Cancel
            System.exit(0);
        }
        companyName = companyName.trim();
        if (!(new File("./" + companyName.replaceAll(" ", "_") + ".txt").exists())) {
            Dialog.errorMessage(Notifications.NO_SUCH_COMPANY, "");
            companyName = getCompanyName();
        }
        return companyName;
    }
    
    
    private int getMaxFlights() {
        int nFlightsMax = 0;
        try {
            String strValue = Dialog.input(Prompts.MAX_FLIGHTS, "Numéro maximale des vols");
            if (strValue == null) {    //case Cancel
                System.exit(0);
            }
            nFlightsMax = Integer.parseInt(strValue.trim());
        } catch (NumberFormatException nfe) {
            Dialog.errorMessage(Notifications.WRONG_INPUT, "");
            nFlightsMax = getMaxFlights();
        }
        return nFlightsMax;
    }
        
    public void perform() throws IOException {
        Menu.doMenu(this.company);
    }
    
}
