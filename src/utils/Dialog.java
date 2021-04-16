package labo2_1.utils;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Siarhei Saukin and Jerry Joseph
 * 
 * 
 * Class pour avoir des fenetres de input ou messages
 * 
 */
public class Dialog {
    
    public static String input(String message, String title) {
        return JOptionPane.showInputDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
    }
    
    public static void errorMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    public static void confirmMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
    }
    
    public static void showTextArea(JTextArea jta, String title) {
        JOptionPane.showMessageDialog(null, jta, title, JOptionPane.PLAIN_MESSAGE, null);
    }
    
}
