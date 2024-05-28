/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itec103_design.Helpers;

import itec103_design.Categories;
import itec103_design.Connection.DBConnection;
import itec103_design.Model.Category;
import itec103_design.Model.Order;
import itec103_design.Model.OrderItem;
import itec103_design.Model.Product;
import itec103_design.Model.User;
import java.awt.Toolkit;
import java.security.MessageDigest;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JRadioButton;
/**
 *
 * @author celis
 */

public class HelperClass {
    Connection con = DBConnection.connect();
    
    public boolean changeFrame(JFrame frame) {
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        return true;
    }
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
    
     public String numberFormatter(String price) {
        double amount = Double.parseDouble(price);
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        price = formatter.format(amount);
        
        return price;
    }
     
  
     
    public String passwordHash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(password.getBytes());
            byte[] rbt = md.digest();
            StringBuilder sb = new StringBuilder();
            
            for(byte b: rbt) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }catch(Exception e) {
            
        }
        return null;
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
    
    public List<Order> getAllOrders(String query){
        List orders = new ArrayList<>();
        
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while(rs.next()) {
                int order_id = rs.getInt("order_id");                
                int reference = rs.getInt("reference");
                int user_id = rs.getInt("user_id");
                String customer_name = rs.getString("customer_name");
                int cash = rs.getInt("cash");
                String cash_type = rs.getString("cash_type");                
                String order_type = rs.getString("order_type");
                String total = rs.getString("total");
                String created_at = rs.getString("created_at");
                String updated_at = rs.getString("updated_at");
                int status = rs.getInt("status");
                Order order = new Order(order_id, reference, user_id, customer_name, cash, cash_type, order_type,total, created_at, updated_at, status);
                orders.add(order);
            }
        } catch (SQLException e) {
            Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, e);
        }
        return orders;
    }
    
    public List<OrderItem> getOrderItemByRef (String query){
        List orderItems = new ArrayList<>();
        
        try{
            
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while(rs.next()) {
                int order_item_id = rs.getInt("order_item_id");
                int order_reference = rs.getInt("order_reference");
                int product_id = rs.getInt("product_id");
                String qty = rs.getString("qty");
                
                OrderItem orderItem = new OrderItem(order_item_id, order_reference, product_id, qty);
                orderItems.add(orderItem);
            }
            
        } catch (SQLException e){
            Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return orderItems;
    }
    
    
    public String getDetail(String query, String return_field) {
        String field = null;
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                field = rs.getString(return_field);
            }

        } catch (SQLException e) {
            Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, e);
        }
        return field;
    }
    
    public List<User> getUsers(String query) {
//        String query = "SELECT * FROM users WHERE status= '0' ORDER BY user_id ASC";
        List users = new ArrayList<>();

        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                int user_id = rs.getInt("user_id");
                String lastname = rs.getString("lastname");
                String firstname = rs.getString("firstname");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String created_at = rs.getString("created_at");
                String updated_at = rs.getString("updated_at");
                String role = rs.getString("role");
                String status = rs.getString("status");
                User user = new User(user_id, firstname, lastname, email, password, created_at, updated_at, role, status);
                users.add(user);
            }

        } catch (SQLException e) {
            Logger.getLogger(Categories.class.getName()).log(Level.SEVERE, null, e);
        }
        return users;
    }
    
    
    public String getSelectedValue(JRadioButton b1, JRadioButton b2) {
        AbstractButton selectedButton = getSelectedButton(b1, b2);
        if (selectedButton != null) {
            return selectedButton.getText();
        } else {
            return null; // Return null if no radio button is selected
        }
    }
    
    public String getSelectedValueOfStatus(JRadioButton b1, JRadioButton b2, JRadioButton b3) {
        AbstractButton selectedButton = getSelectedButton(b1, b2, b3);
        if (selectedButton != null) {
            return selectedButton.getText();
        } else {
            return null; // Return null if no radio button is selected
        }
    }
    public AbstractButton getSelectedButton(AbstractButton... buttons) {
        for (AbstractButton button : buttons) {
            if (button.isSelected()) {
                return button;
            }
        }
        return null;
    }
    
}
