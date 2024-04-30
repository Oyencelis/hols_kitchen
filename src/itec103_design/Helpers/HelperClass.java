/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itec103_design.Helpers;

import itec103_design.Categories;
import itec103_design.Connection.DBConnection;
import itec103_design.Model.Category;
import itec103_design.Model.Product;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
/**
 *
 * @author celis
 */

public class HelperClass {
    Connection con = DBConnection.connect();
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
    
    
    public List<Category> getAllCategories() {
        String query = "SELECT * FROM categories WHERE status= '0' ORDER BY category_name ASC";
        List categories = new ArrayList<>();

        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                int category_id = rs.getInt("category_id");
                String category_name = rs.getString("category_name");
                int status = rs.getInt("status");
                Category category = new Category(category_id, category_name, status);
                categories.add(category);
            }

        } catch (SQLException e) {
            Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, e);
        }
        return categories;
    }
    
    public String getCategoryByName(String name) {
        String query = "SELECT category_id FROM categories WHERE category_name = '"+name+"' AND status = '0'";
        String id = null;
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                id = rs.getString("category_id");
            }

        } catch (SQLException e) {
            Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, e);
        }
        return id;
    }
    
    public String getCategoryById(int id) {
        String query = "SELECT category_name FROM categories WHERE category_id = '"+id+"' AND status = '0'";
        String name = null;
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                name = rs.getString("category_name");
            }

        } catch (SQLException e) {
            Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, e);
        }
        return name;
    }
    
    
    public List<Product> getAllProducts(String query) {
        List products = new ArrayList<>();

        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                int prdoduct_id = rs.getInt("product_id");                
                int category_id = rs.getInt("category_id");
                String product_name = rs.getString("product_name");
                String price = rs.getString("price");
                String created_at = rs.getString("created_at");
                String updated_at = rs.getString("updated_at");
                int status = rs.getInt("status");
                Product category = new Product(prdoduct_id, category_id, product_name, price, created_at, updated_at, status);
                products.add(category);
            }

        } catch (SQLException e) {
            Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, e);
        }
        return products;
    }
}
