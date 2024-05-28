/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import itec103_design.Helpers.HelperClass;
import itec103_design.Model.Order;
import itec103_design.Model.OrderItem;

import itec103_design.Model.OrderManager;
import itec103_design.Model.OrderReference;
import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.border.EmptyBorder;
import java.util.List;
/**
 *
 * @author celis
 */

public class LoopingOrderItem {
    
    
    public static String getReference(){
        OrderReference ref = OrderManager.getCurrentOrderRef();
        String reference = ref.getReference();
        return reference;
    }
    public static String[][] getOrderItems() {
        
        HelperClass hp = new HelperClass();
        String reference_id = getReference();
        
        String[][] orderItem = {};
        
        List<OrderItem> item_data = hp.getOrderItemByRef("SELECT * FROM order_items where order_reference =" + reference_id);
        for (OrderItem order : item_data) {
             // New set of data to add
             
            
            String getNameQuery = "SELECT * FROM products WHERE product_id = '"+order.getProductId()+"'";
            String prod_name = hp.getDetail(getNameQuery, "product_name");            
            String price = hp.getDetail(getNameQuery, "price");
            price = hp.numberFormatter(price);
            
            String[] newData = {prod_name, order.getQty(), price};
            // Expand the array size by 1
            String[][] newArray = new String[orderItem.length + 1][];

            // Copy existing elements to the new array
            for (int i = 0; i < orderItem.length; i++) {
                newArray[i] = orderItem[i];
            }
            // Add the new set of data
            newArray[newArray.length - 1] = newData;
            // Update the reference
            orderItem = newArray;
        }
        return orderItem;
    }
    
    public static String parseAndFormatDate(String dateTimeString) {
        // Define the date and time format for parsing
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Define the date and time format for output
        SimpleDateFormat outputFormat = new SimpleDateFormat(" MMM, d yyyy hh:mm a");

        try {
            // Parse the string into a Date object
            Date date = inputFormat.parse(dateTimeString);
            
            // Format the Date object back to a string
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Or handle the error as needed
        }
    }
    
    public static void main(String[] args) {
        HelperClass hp = new HelperClass();
        
        String cus_name = null, 
               ordrtyp = null,
               reference_id = getReference(),
               processed = null,
               cash_type = null,
               date = null;
        int cash = 0;
        
        List<Order> data = hp.getAllOrders("SELECT * FROM orders where reference =" + reference_id);
        for (Order order : data) {
            cus_name = order.getCustomerName();
            ordrtyp = order.getOrderType();
            cash = order.getCash();
            cash_type = order.getCashType();
            String query = "SELECT CONCAT_WS(' ', `firstname`, `lastname`) AS `fullname` FROM `users` WHERE user_id ='"+order.getUserId()+"'";
            processed = hp.getDetail(query, "fullname");
            // Test the function
            String dateTimeString = order.getCreatedAt();
            String formattedDate = parseAndFormatDate(dateTimeString);
            
            date = formattedDate;

        }
        
        String[][] items = getOrderItems();

       //font sizing
        String fontFam = "Segoe UI";
        Font h1 = new Font(fontFam, Font.BOLD, 20);
        Font h2 = new Font(fontFam, Font.BOLD, 16);
        Font h3 = new Font(fontFam, Font.PLAIN, 14);        
        Font h4 = new Font(fontFam, Font.PLAIN, 12);

        
        EmptyBorder br = new EmptyBorder (10,10,10,10);
        //color
        

       // Create the main frame
        JFrame frame = new JFrame("Order Details");
        frame.setSize(600, 500);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        
        

        // Create a panel to hold the receipt content
        JPanel receiptPanel = new JPanel();
        receiptPanel.setLayout(new BoxLayout(receiptPanel, BoxLayout.Y_AXIS));
        receiptPanel.setBorder(br);

        // Create a panel to hold store name and phone number labels
        JPanel headerPanel = new JPanel(new GridLayout(5,0, 0, -5));
//        headerPanel.setBackground(Color.red);
        
        // Add store name
        JLabel storeNameLabel = new JLabel("Hol's Kitchen");
//        storeNameLabel.setForeground(Color.orange);
        storeNameLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align
        storeNameLabel.setFont(h1);
        headerPanel.add(storeNameLabel);
        

        String name = cus_name;
        // Add Customer name
        JLabel customerName = new JLabel("Customer: " + name);
        customerName.setFont(h3);
        headerPanel.add(customerName);
        
        
        String reference = reference_id;
        // Add reference
        JLabel ref = new JLabel("Reference: " + reference);
        ref.setFont(h3);
        headerPanel.add(ref);
        
        //Add order type
        String order_type = ordrtyp;
        JLabel order = new JLabel( order_type);
        order.setFont(h3);
        headerPanel.add(order);
        
        
        //Add order type
        String fullname = processed;
        JLabel full = new JLabel("Processed By: " +fullname);
        order.setFont(h3);
        headerPanel.add(full);
         
        

        receiptPanel.add(headerPanel);
        
        
       

        // Add horizontal separator line
        receiptPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

        // Add items panel
        JPanel itemsPanel = new JPanel(new GridLayout(0, 3, 0,0)); // Decreased gap between items
//        itemsPanel.setBorder(BorderFactory.createTitledBorder(""));
        

        // Add item headers with customized font
         // Bold and increased size
        JLabel itemHeaderLabel = new JLabel("Item");
        itemHeaderLabel.setFont(h2);
        JLabel qtyHeaderLabel = new JLabel("Qty");
        qtyHeaderLabel.setFont(h2);
        JLabel priceHeaderLabel = new JLabel("Price");
        priceHeaderLabel.setFont(h2);

        itemsPanel.add(itemHeaderLabel);
        itemsPanel.add(qtyHeaderLabel);
        itemsPanel.add(priceHeaderLabel);

        // Add items dynamically
        double total = 0, cash_amount = cash;
        for (String[] item : items) {
            JLabel nameLabel = new JLabel(item[0]);
            JLabel quantityLabel = new JLabel("x"+item[1]);
            JLabel priceLabel = new JLabel(item[2]);
            nameLabel.setFont(h4);
            quantityLabel.setFont(h4);
            priceLabel.setFont(h4);
            
            itemsPanel.add(nameLabel);
            itemsPanel.add(quantityLabel);
            itemsPanel.add(priceLabel);

            // Calculate total price
            total += Integer.parseInt(item[1]) * Double.parseDouble(item[2].replace(",", ""));
        }
        receiptPanel.add(itemsPanel);
        
        receiptPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

        // Create a panel to hold store name and phone number labels
        JPanel footerPanel = new JPanel(new GridLayout(0, 2, 0, 0));
        
        // Add item headers with customized font
        JLabel totalLabel = new JLabel("Total");
        totalLabel.setFont(h1);
        JLabel totalNum  = new JLabel("PHP " + hp.numberFormatter(Double.toString(total)));
        totalNum.setFont(h1);
        
        JLabel cashLabel = new JLabel("Cash");
        cashLabel.setFont(h3);
        JLabel cashNum  = new JLabel("PHP " + hp.numberFormatter(Double.toString(cash_amount)));
        cashNum.setFont(h3);
        
        JLabel changeLabel = new JLabel("Change");
        changeLabel.setFont(h3);
        JLabel changeNum  = new JLabel("PHP " + hp.numberFormatter(Double.toString(cash_amount - total)));
        changeNum.setFont(h3);
        
        footerPanel.add(totalLabel);
        footerPanel.add(totalNum);
        footerPanel.add(cashLabel);
        footerPanel.add(cashNum);
        footerPanel.add(changeLabel);
        footerPanel.add(changeNum);
        
        
        receiptPanel.add(footerPanel);
        
        // Add horizontal separator line
        receiptPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        
        JPanel mopPanel = new JPanel();
        String mopVal = "<html>Mode of Payment: " + cash_type +"<br/>"+date+" </html>" ;
        // Add reference
        JLabel mopLabel = new JLabel( mopVal);
        mopLabel.setFont(h3);
        mopPanel.add(mopLabel);
        
        receiptPanel.add(mopPanel);

        // Create a JScrollPane and add the receipt panel to it
        JScrollPane scrollPane = new JScrollPane(receiptPanel);

        // Add the scroll pane to the frame
        frame.add(scrollPane, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);
    }
   
    
   
}
