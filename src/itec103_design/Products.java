/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package itec103_design;

import itec103_design.Connection.DBConnection;
import itec103_design.Helpers.HelperClass;
import itec103_design.Model.Category;
import itec103_design.Model.Product;
import itec103_design.Model.UserManager;
import itec103_design.Model.User;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Janice
 */

public class Products extends javax.swing.JFrame {
    
    /**
     * Creates new form JFrame4
     */
        HelperClass hp = null;
        Connection con = null;
    public Products() {
        
        initComponents();
        setUser();
        con = DBConnection.connect();
        hp = new HelperClass();
        this.setLocationRelativeTo(null);
        titleH1.setBorder(new MatteBorder(0, 0, 2, 0, Color.ORANGE));
        getCategories();
        getAllproducts();
    }
    
    public String getCategoryId() {
      String name = String.valueOf(categoryDropdown.getSelectedItem());
      return hp.getCategoryByName(name);
      
    }
    public void setUser(){
        User currentUser = UserManager.getCurrentUser();
        System.out.println("User: " + currentUser.getFirstname());
        user.setText(currentUser.getFirstname() + ' ' + currentUser.getLastname());
        String role = currentUser.getRole();
        int number = Integer.parseInt(role);
        if(number == 0) {
            usersLink.setVisible(false);
        } 
    }
    
    
    public void getCategories(){
        categoryDropdown.removeAllItems(); 
        List<Category> cat = hp.getAllCategories();
        categoryDropdown.addItem("");
        for (Category category : cat) {
            categoryDropdown.addItem(category.getCategoryName());
        }
    }
    
    public void getAllproducts() {
        List<Product> data = hp.getAllProducts();
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        TableColumnModel columnModel = productTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(20);
        productTable.setRowHeight(30);
//        productTable.setSelectionBackground(Color.ORANGE);
        JTableHeader header = productTable.getTableHeader();
        header.setBackground(Color.BLACK);
        header.setForeground(Color.WHITE);

        model.setNumRows(0); 
        int i = 0;
        for (Product prod : data) {
            i = i+1;
            String cat_name = hp.getCategoryById(prod.getCategoryId());
            String price = prod.getPrice();
            double amount = Double.parseDouble(price);
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            price = formatter.format(amount);
            model.addRow(new Object[]{i, prod.getProductName(), price, cat_name});
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
        purchased = new javax.swing.JButton();
        usersLink = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        user = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        titleH1 = new javax.swing.JLabel();
        price_label = new javax.swing.JLabel();
        categoryDropdown = new javax.swing.JComboBox<>();
        price = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        product_name = new javax.swing.JTextField();
        addProductBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        productTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(237, 121, 13));
        setResizable(false);

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

        dashboard.setBackground(new java.awt.Color(242, 242, 242));
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
        jPanel2.add(categories, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 180, 40));

        products.setBackground(new java.awt.Color(237, 121, 13));
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
        jPanel2.add(products, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 180, 40));

        purchased.setBackground(new java.awt.Color(242, 242, 242));
        purchased.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        purchased.setIcon(new javax.swing.ImageIcon(getClass().getResource("/purchased.png"))); // NOI18N
        purchased.setText("Orders");
        purchased.setBorderPainted(false);
        purchased.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        purchased.setDisabledIcon(null);
        purchased.setFocusPainted(false);
        purchased.setFocusable(false);
        purchased.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        purchased.setIconTextGap(10);
        purchased.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                purchasedActionPerformed(evt);
            }
        });
        jPanel2.add(purchased, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 180, 40));

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
        jPanel3.add(user, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 10, 130, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("Products");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 130, 40));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setDoubleBuffered(false);
        jPanel4.setFocusable(false);
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        titleH1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        titleH1.setText("Add New Product");
        titleH1.setFocusTraversalPolicyProvider(true);
        jPanel4.add(titleH1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 30, 300, 40));

        price_label.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        price_label.setText("Price (PHP)");
        jPanel4.add(price_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 220, 110, -1));

        categoryDropdown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        categoryDropdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryDropdownActionPerformed(evt);
            }
        });
        jPanel4.add(categoryDropdown, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 110, 300, 30));

        price.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        price.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        price.setToolTipText("");
        price.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        price.setMaximumSize(new java.awt.Dimension(5, 5));
        price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priceActionPerformed(evt);
            }
        });
        price.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                priceKeyPressed(evt);
            }
        });
        jPanel4.add(price, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 250, 300, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Choose Category");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, 110, -1));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 380, 300, 20));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 340, 300, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Product Name");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 150, 110, -1));

        product_name.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        product_name.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        product_name.setToolTipText("");
        product_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        product_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                product_nameActionPerformed(evt);
            }
        });
        product_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                product_nameKeyPressed(evt);
            }
        });
        jPanel4.add(product_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 180, 300, 30));

        addProductBtn.setBackground(new java.awt.Color(0, 0, 0));
        addProductBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        addProductBtn.setForeground(new java.awt.Color(255, 255, 255));
        addProductBtn.setText("Add");
        addProductBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductBtnlogInBtn(evt);
            }
        });
        addProductBtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                addProductBtnKeyPressed(evt);
            }
        });
        jPanel4.add(addProductBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 290, 80, -1));

        productTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "#", "Name", "Price", "Category"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(productTable);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 400, 390));

        jScrollPane1.setViewportView(jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 791, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        // TODO add your handling code here:
        
        int input = hp.confirmDialog("Confirm Log Out?");

	// 0=yes, 1=no, 2=cancel
        if(input == 0) {
            Login LoginFrame = new Login();
            LoginFrame.setVisible(true);
            LoginFrame.pack();
            LoginFrame.setLocationRelativeTo(null);
            this.dispose();   
        }
    }//GEN-LAST:event_logoutActionPerformed


    private void dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboardActionPerformed
        // TODO add your handling code here:
        Dash Frame = new Dash();
        Frame.setVisible(true);
        Frame.pack();
        Frame.setLocationRelativeTo(null);
        this.dispose(); 
    }//GEN-LAST:event_dashboardActionPerformed

    private void categoriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoriesActionPerformed
        try {
            // TODO add your handling code here:
            Categories Frame = new Categories();
            Frame.setVisible(true);
            Frame.pack();
            Frame.setLocationRelativeTo(null);
            this.dispose();         // TODO add your handling code here:
        } catch (SQLException ex) {
            Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_categoriesActionPerformed

    private void productsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productsActionPerformed
        // TODO add your handling code here:
           
    }//GEN-LAST:event_productsActionPerformed

    private void purchasedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_purchasedActionPerformed
        // TODO add your handling code here:
        Orders Frame = new Orders();
        Frame.setVisible(true);
        Frame.pack();
        Frame.setLocationRelativeTo(null);
        this.dispose(); 
    }//GEN-LAST:event_purchasedActionPerformed

    private void usersLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usersLinkActionPerformed
        // TODO add your handling code here:
        Orders Frame = new Orders();
        Frame.setVisible(true);
        Frame.pack();
        Frame.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_usersLinkActionPerformed

    private void priceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_priceActionPerformed

    private void priceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_priceKeyPressed
        String regex = "^\\d*\\.?\\d*$";
        String value = price.getText();
        if (!value.matches(regex)) {
            hp.errorMessageDialog("Enter Number Only");
            price.setText("");
            if(!price.getText().isEmpty()) {
                price.setText("");
            }
        }
    }//GEN-LAST:event_priceKeyPressed

    private void product_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_product_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_product_nameActionPerformed

    private void product_nameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_product_nameKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_product_nameKeyPressed

    private void addProductBtnlogInBtn(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductBtnlogInBtn
        String cat_id = getCategoryId();
//        System.out.println("Original Password: " + cat_id);
        String product = product_name.getText();
        float price_val;
        if (cat_id == null){
            hp.errorMessageDialog("Please Select a Category");
        }
        else if(product_name.getText().isBlank()){
            hp.errorMessageDialog("Please Enter a Product Name");
        } 
        else if(price.getText().isBlank()){
            hp.errorMessageDialog("Please Enter Price");
        } 
        else{
            price_val = Float.parseFloat(price.getText());
            try{
                Statement st = con.createStatement();
                String selectquery = "SELECT * FROM products WHERE product_name= '"+product+"' AND category_id = '"+cat_id+"' AND status = 0";
                ResultSet rs = st.executeQuery(selectquery);
               if(rs.next()){
                    hp.errorMessageDialog("Product is already exist!");
                } else {
                    String query = "INSERT INTO products(category_id, product_name, price)" +
                    "VALUES('"+cat_id+"','"+product+"', '"+price_val+"')";
                    st.execute(query);
                    hp.messageDialog("Added new product successfully!");
                    getAllproducts();
                    categoryDropdown.setSelectedIndex(-1);
                    product_name.setText("");
                    price.setText("");
                }

            }
            
            catch(SQLException ex ){
                Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_addProductBtnlogInBtn

    private void addProductBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addProductBtnKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_addProductBtnKeyPressed

    private void categoryDropdownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryDropdownActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_categoryDropdownActionPerformed

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
    javax.swing.JButton addProductBtn;
    javax.swing.JButton categories;
    javax.swing.JComboBox<String> categoryDropdown;
    javax.swing.JButton dashboard;
    javax.swing.JLabel jLabel1;
    javax.swing.JLabel jLabel3;
    javax.swing.JLabel jLabel7;
    javax.swing.JLabel jLabel8;
    javax.swing.JPanel jPanel1;
    javax.swing.JPanel jPanel2;
    javax.swing.JPanel jPanel3;
    javax.swing.JPanel jPanel4;
    javax.swing.JPanel jPanel6;
    javax.swing.JPanel jPanel7;
    javax.swing.JScrollPane jScrollPane1;
    javax.swing.JScrollPane jScrollPane2;
    javax.swing.JButton logout;
    javax.swing.JTextField price;
    javax.swing.JLabel price_label;
    javax.swing.JTable productTable;
    javax.swing.JTextField product_name;
    javax.swing.JButton products;
    javax.swing.JButton purchased;
    javax.swing.JLabel titleH1;
    javax.swing.JLabel user;
    javax.swing.JButton usersLink;
    // End of variables declaration//GEN-END:variables
}
