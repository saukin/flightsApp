package labo2_1;

/**
 *
 * @author Siarhei Saukin and Jerry Joseph
 */
public class Date {
    
    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    

    @Override
    public String toString() {
        return String.format("%02d/%02d/%d", day, month, year);
    }
    
    // format de toString pour souvgarder dans un fichier
    public String toFileString() {
        return String.format("%02d;%02d;%d", day, month, year);
    }
    
}
