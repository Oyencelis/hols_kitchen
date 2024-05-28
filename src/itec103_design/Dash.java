/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package itec103_design;

import com.toedter.calendar.JTextFieldDateEditor;
import itec103_design.Helpers.HelperClass;
import itec103_design.Model.Order;
import itec103_design.Model.UserManager;
import itec103_design.Model.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
/**
 *
 * @author Janice
 */
public class Dash extends javax.swing.JFrame {

    /**
     * Creates new form JFrame4
     */
    HelperClass hp = null;
    LocalDateTime now = LocalDateTime.now();
    
    String dateChoose = null;
    public Dash() {
        initComponents();
        setUser();
        hp = new HelperClass();
        this.setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../hols.jpg")));


        getEmployeeCount();
        getSumSales();
        getTotalOrder();
        getSumTodaySales();
        onchangeDate();
        
        
    }
    

    private String DateFormatter(String formatFrmDate, String newformat, String date) {
        String returndate = null;
         try {

            SimpleDateFormat format = new SimpleDateFormat(formatFrmDate);
            SimpleDateFormat myFormat = new SimpleDateFormat(newformat);
            returndate = myFormat.format(format.parse(date));
            return returndate;
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returndate;
    }
    private void onchangeDate() {
        JTextFieldDateEditor txt = (JTextFieldDateEditor) datepick.getDateEditor();
        txt.setEditable(false);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");        
        DateTimeFormatter formatForChart = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        String cur = now.format(formatter);
        selected_date.setText(cur);
        
        dateChoose = now.format(formatForChart);
        getMonthlyReport(dateChoose);
        getYearReport(dateChoose);
        datepick.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    
                    dateChoose = String.valueOf(format.format(datepick.getDate()));
                    
                    String selected = DateFormatter("yyyy-MM-dd","MMM d, yyyy",dateChoose);
                    dateChoose = DateFormatter("yyyy-MM-dd","yyyy-MM-dd",dateChoose);
                    selected_date.setText(selected);
                    getMonthlyReport(dateChoose);
                    getYearReport(dateChoose);
                   
                }
            }
        });
    }
    
    private void getSumSales() {
        String query = "SELECT sum(total) as total_sum FROM `orders`;";
        String count = hp.getDetail(query, "total_sum");
        all_sales.setText("PHP "+hp.numberFormatter(count));
    }
    private void getSumTodaySales() {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayDateTime = now.format(formatter);
        String query = "SELECT sum(total) as total_sum FROM `orders` WHERE created_at LIKE '"+todayDateTime+"%';";
        String count = hp.getDetail(query, "total_sum");
        if(count != null) {
            today_sale.setText("PHP "+hp.numberFormatter(count));
        } else {
            today_sale.setText("PHP 0.00");
        }  
    }
    private void getEmployeeCount() {
        String query = "SELECT count(*) as count FROM `users` WHERE role = 0";
        String count = hp.getDetail(query, "count");
        total_employee.setText(count);
    }
    
    private void getTotalOrder() {
        String query = "SELECT count(*) as total FROM `orders`";
        String count = hp.getDetail(query, "total");
        total_order.setText(count);
        
    }
    
    private void getMonthlyReport(String date) {
        try {
            SimpleDateFormat formatFrmDate = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat newformat = new SimpleDateFormat("yyyy-MM");

            String finaldate = newformat.format(formatFrmDate.parse(date));
            
            String query = "SELECT * FROM orders WHERE created_at LIKE'"+finaldate+"%'";
            List<Order> data = hp.getAllOrders(query);
            showPieChartMonth(monthlySale, data); 
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
    }
    
    private void getYearReport(String date) {
        try {
            SimpleDateFormat formatFrmDate = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat newformat = new SimpleDateFormat("yyyy");

            String finaldate = newformat.format(formatFrmDate.parse(date));
            
            String query = "SELECT * FROM orders WHERE created_at LIKE'"+finaldate+"%'";
            List<Order> data = hp.getAllOrders(query);
            showPieChartYear(anualSales, data); 
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
    }
    
    private void showPieChartMonth(JScrollPane scrollpane, List<Order> data){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout()); // Use BorderLayout for the main panel
        
        String chartName = DateFormatter("yyyy-MM-dd","MMM yyyy", dateChoose);
        // Create a dataset
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for(Order order: data) {
                String sameDay = DateFormatter("yyyy-MM-dd","yyyy-MM-dd", order.getCreatedAt());
                String query = "SELECT sum(total) as total_sum FROM `orders` WHERE created_at LIKE '"+sameDay+"%';";
                String total = hp.getDetail(query, "total_sum");
                double doubleValue = Double.parseDouble(total);
                int intValue = (int) doubleValue;
                String day = DateFormatter("yyyy-MM-dd","dd", order.getCreatedAt());
                dataset.addValue(intValue, day, day);
                
            }
            
            // Create a chart
            JFreeChart chart = ChartFactory.createBarChart(
                    chartName,    // Chart title
                    "Date",     // X-axis label
                    "Sales",        // Y-axis label
                    dataset);       // Dataset


        // Customize the chart
        chart.setBackgroundPaint(Color.white);

        // Create a chart panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(300, 300));

        panel.add(chartPanel, BorderLayout.CENTER); // Add chart panel to the center of the main panel

        scrollpane.setViewportView(panel); // Set the main panel as the view

        getContentPane().add(scrollpane); // Add the scroll pane to the content pane of the JFrame
        revalidate(); // Revalidate the container
        repaint(); // Repaint the container
   
    }
    
    
    private void showPieChartYear(JScrollPane scrollpane, List<Order> data){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout()); // Use BorderLayout for the main panel
        
        String chartName = DateFormatter("yyyy-MM-dd","yyyy", dateChoose);
        // Create a dataset
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for(Order order: data) {
                String sameMonth = DateFormatter("yyyy-MM-dd","yyyy-MM", order.getCreatedAt());
                String query = "SELECT sum(total) as total_sum FROM `orders` WHERE created_at LIKE '"+sameMonth+"%';";
                String total = hp.getDetail(query, "total_sum");
                double doubleValue = Double.parseDouble(total);
                int intValue = (int) doubleValue;
                String month = DateFormatter("yyyy-MM-dd","MMM", order.getCreatedAt());
                dataset.addValue(intValue, month, month);
                
            }
            
            // Create a chart
            JFreeChart chart = ChartFactory.createBarChart(
                    "Year "+chartName,    // Chart title
                    "Date",     // X-axis label
                    "Sales",        // Y-axis label
                    dataset);       // Dataset


        // Customize the chart
        chart.setBackgroundPaint(Color.white);

        // Create a chart panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(350, 250));

        panel.add(chartPanel, BorderLayout.CENTER); // Add chart panel to the center of the main panel

        scrollpane.setViewportView(panel); // Set the main panel as the view

        getContentPane().add(scrollpane); // Add the scroll pane to the content pane of the JFrame
        revalidate(); // Revalidate the container
        repaint(); // Repaint the container
   
    }
    


    
    
    
    private void setUser(){
        User currentUser = UserManager.getCurrentUser();
        user.setText(currentUser.getFirstname() + ' ' + currentUser.getLastname());
        String role = currentUser.getRole();
        int number = Integer.parseInt(role);
        if(number == 0) {
            usersLink.setVisible(false);            
            categories.setVisible(false);
            products.setVisible(false);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        logout = new javax.swing.JButton();
        dashboard = new javax.swing.JButton();
        categories = new javax.swing.JButton();
        products = new javax.swing.JButton();
        usersLink = new javax.swing.JButton();
        purchased1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        user = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        total_employee = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        total_order = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        all_sales = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        selected_date = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        datepick = new com.toedter.calendar.JDateChooser();
        jPanel10 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        today_sale = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        monthlySale = new javax.swing.JScrollPane();
        anualSales = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(237, 121, 13));
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setAutoscrolls(true);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/itec103_design/hols_logo.png"))); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel1.setRequestFocusEnabled(false);
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 190));

        logout.setBackground(new java.awt.Color(242, 242, 242));
        logout.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logout.png"))); // NOI18N
        logout.setText("Logout");
        logout.setBorderPainted(false);
        logout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logout.setDisabledIcon(null);
        logout.setFocusPainted(false);
        logout.setFocusable(false);
        logout.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        logout.setIconTextGap(10);
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });
        jPanel2.add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 520, 180, 40));

        dashboard.setBackground(new java.awt.Color(237, 121, 13));
        dashboard.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        dashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dashboard.png"))); // NOI18N
        dashboard.setText("Dashboard");
        dashboard.setBorderPainted(false);
        dashboard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        dashboard.setDisabledIcon(null);
        dashboard.setFocusPainted(false);
        dashboard.setFocusable(false);
        dashboard.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        dashboard.setIconTextGap(10);
        dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dashboardActionPerformed(evt);
            }
        });
        jPanel2.add(dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 180, 40));

        categories.setBackground(new java.awt.Color(242, 242, 242));
        categories.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        categories.setIcon(new javax.swing.ImageIcon(getClass().getResource("/categories.png"))); // NOI18N
        categories.setText("Categories");
        categories.setAutoscrolls(true);
        categories.setBorderPainted(false);
        categories.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        categories.setDisabledIcon(null);
        categories.setFocusPainted(false);
        categories.setFocusable(false);
        categories.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        categories.setIconTextGap(10);
        categories.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoriesActionPerformed(evt);
            }
        });
        jPanel2.add(categories, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 180, 40));

        products.setBackground(new java.awt.Color(242, 242, 242));
        products.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        products.setIcon(new javax.swing.ImageIcon(getClass().getResource("/products.png"))); // NOI18N
        products.setText("Products");
        products.setBorderPainted(false);
        products.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        products.setDisabledIcon(null);
        products.setFocusPainted(false);
        products.setFocusable(false);
        products.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        products.setIconTextGap(10);
        products.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productsActionPerformed(evt);
            }
        });
        jPanel2.add(products, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 180, 40));

        usersLink.setBackground(new java.awt.Color(242, 242, 242));
        usersLink.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        usersLink.setIcon(new javax.swing.ImageIcon(getClass().getResource("/users.png"))); // NOI18N
        usersLink.setText("Users");
        usersLink.setBorderPainted(false);
        usersLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        usersLink.setFocusPainted(false);
        usersLink.setFocusable(false);
        usersLink.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        usersLink.setIconTextGap(10);
        usersLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usersLinkActionPerformed(evt);
            }
        });
        jPanel2.add(usersLink, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 180, 40));

        purchased1.setBackground(new java.awt.Color(242, 242, 242));
        purchased1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        purchased1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/purchased.png"))); // NOI18N
        purchased1.setText("Orders");
        purchased1.setBorderPainted(false);
        purchased1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        purchased1.setDisabledIcon(null);
        purchased1.setFocusPainted(false);
        purchased1.setFocusable(false);
        purchased1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        purchased1.setIconTextGap(10);
        purchased1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                purchased1ActionPerformed(evt);
            }
        });
        jPanel2.add(purchased1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 180, 40));

        jPanel1.setBackground(new java.awt.Color(255, 102, 0));
        jPanel1.setToolTipText("");
        jPanel1.setPreferredSize(new java.awt.Dimension(10, 547));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel3.setFocusable(false);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        user.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        user.setForeground(new java.awt.Color(237, 121, 13));
        user.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/user.png"))); // NOI18N
        user.setText("User");
        user.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel3.add(user, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 540, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("Dashboard");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 130, 40));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setAutoscrolls(true);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/users_all.png"))); // NOI18N

        total_employee.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        total_employee.setText("1259");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(153, 153, 153));
        jLabel8.setText("Total Employees");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(total_employee, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel5)
                .addGap(0, 0, 0)
                .addComponent(total_employee)
                .addGap(0, 0, 0)
                .addComponent(jLabel8)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setAutoscrolls(true);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/order.png"))); // NOI18N

        total_order.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        total_order.setText("1259");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setText("Total Orders");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(total_order, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel2)
                .addGap(0, 0, 0)
                .addComponent(total_order)
                .addGap(0, 0, 0)
                .addComponent(jLabel6)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setAutoscrolls(true);

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sales.png"))); // NOI18N

        all_sales.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        all_sales.setText("PHP 0.00");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(153, 153, 153));
        jLabel14.setText("All Sales");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(all_sales, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(all_sales)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        selected_date.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        selected_date.setText("January 01, 2024");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Selected Date: ");

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setAutoscrolls(true);

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/todaysale.png"))); // NOI18N

        today_sale.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        today_sale.setText("PHP 0.00");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(153, 153, 153));
        jLabel17.setText("Today Sales");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(today_sale, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(today_sale)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(selected_date, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(datepick, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 647, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2))
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(datepick, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(selected_date)
                        .addContainerGap())))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                    .addContainerGap(130, Short.MAX_VALUE)
                    .addComponent(jLabel16)
                    .addContainerGap()))
        );

        jPanel4.setBackground(new java.awt.Color(214, 217, 223));
        jPanel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel4.setFocusable(false);
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Monthly Sales Report");
        jPanel4.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 170, -1));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("Annually Sales Report");
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, 160, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 791, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(monthlySale, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(anualSales, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(monthlySale)
                    .addComponent(anualSales, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        // TODO add your handling code here:
        
        int input = hp.confirmDialog("Confirm Log Out?");
	// 0=yes, 1=no, 2=cancel
        if(input == 0) {
            boolean d = hp.changeFrame(new Login());
            if(d)this.dispose(); 
        }
    }//GEN-LAST:event_logoutActionPerformed


    private void dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboardActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_dashboardActionPerformed

    private void categoriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoriesActionPerformed
        try {
            boolean d = hp.changeFrame(new Categories());
            if(d)this.dispose();         // TODO add your handling code here:
        } catch (SQLException ex) {
            Logger.getLogger(Dash.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }//GEN-LAST:event_categoriesActionPerformed

    private void productsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productsActionPerformed
        // TODO add your handling code here:
        
        boolean d = hp.changeFrame(new Products());
        if(d)this.dispose(); 
    }//GEN-LAST:event_productsActionPerformed

    private void usersLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usersLinkActionPerformed
        // TODO add your handling code here:
        
        boolean d = hp.changeFrame(new Users());
        if(d)this.dispose();
    }//GEN-LAST:event_usersLinkActionPerformed

    private void purchased1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_purchased1ActionPerformed
        // TODO add your handling code here:
        boolean d = hp.changeFrame(new Orders());
        if(d)this.dispose();
    }//GEN-LAST:event_purchased1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new Dash().setVisible(true);
                
            }
        });
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JLabel all_sales;
    javax.swing.JScrollPane anualSales;
    javax.swing.JButton categories;
    javax.swing.JButton dashboard;
    com.toedter.calendar.JDateChooser datepick;
    javax.swing.JLabel jLabel1;
    javax.swing.JLabel jLabel12;
    javax.swing.JLabel jLabel13;
    javax.swing.JLabel jLabel14;
    javax.swing.JLabel jLabel16;
    javax.swing.JLabel jLabel17;
    javax.swing.JLabel jLabel18;
    javax.swing.JLabel jLabel19;
    javax.swing.JLabel jLabel2;
    javax.swing.JLabel jLabel3;
    javax.swing.JLabel jLabel5;
    javax.swing.JLabel jLabel6;
    javax.swing.JLabel jLabel8;
    javax.swing.JPanel jPanel1;
    javax.swing.JPanel jPanel10;
    javax.swing.JPanel jPanel2;
    javax.swing.JPanel jPanel3;
    javax.swing.JPanel jPanel4;
    javax.swing.JPanel jPanel5;
    javax.swing.JPanel jPanel6;
    javax.swing.JPanel jPanel7;
    javax.swing.JPanel jPanel9;
    javax.swing.JButton logout;
    javax.swing.JScrollPane monthlySale;
    javax.swing.JButton products;
    javax.swing.JButton purchased1;
    javax.swing.JLabel selected_date;
    javax.swing.JLabel today_sale;
    javax.swing.JLabel total_employee;
    javax.swing.JLabel total_order;
    javax.swing.JLabel user;
    javax.swing.JButton usersLink;
    // End of variables declaration//GEN-END:variables
}
