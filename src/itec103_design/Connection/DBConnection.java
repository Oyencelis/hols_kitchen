/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itec103_design.Connection;

/**
 *
 * @author celis
 */

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class DBConnection {
    public static Connection connect() {
        
        String Surl, SUser,Spass;
         // please change the port number depending on the port of mysql running on your local ..
        Surl = "jdbc:MySQL://localhost:3306/hols_kitchen";
        SUser = "root";
        Spass = "";
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(Surl, SUser, Spass);
        } catch(Exception e) {
               JOptionPane.showMessageDialog(null, e);
        }
        
        return con;
    }
}
