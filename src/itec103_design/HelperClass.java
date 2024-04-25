/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itec103_design;

import java.awt.Frame;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author celis
 */
public class HelperClass {
    public void messageDialog(String message) {
	showMessageDialog(null, message);
    }
    
    public void errorMessageDialog(String message) {
	JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public int confirmDialog(String message) {
	int input = JOptionPane.showConfirmDialog(null, 
                message, "Select an Option",JOptionPane.YES_NO_OPTION);
        return input;
    }
}
