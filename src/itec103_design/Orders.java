/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package itec103_design;

import Components.LoopingOrderItem;
//import Components.OrderDetails;
import itec103_design.Helpers.HelperClass;
import itec103_design.Model.UserManager;
import itec103_design.Model.User;
import itec103_design.Model.Order;
import itec103_design.Model.OrderManager;
import itec103_design.Model.OrderReference;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Inan
 */
public class Orders extends javax.swing.JFrame {

    /**
     * Creates new form JFrame4
     */
    HelperClass hp = null;
    Connection con = null;
    public Orders() {
        initComponents();
        setUser();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../hols.jpg")));
        hp = new HelperClass();
        this.setLocationRelativeTo(null);
        
        getAllorders("SELECT * FROM orders WHERE status= '0' ORDER BY updated_at DESC");
        
        
        orderDatepicker.hide();
//        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        Date newDate = new Date();
//        System.out.println(dateFormat.format(newDate));
//bill_date.setDateFormatString("yyyy-MM-dd");

        searchBy.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                String options = String.valueOf(searchBy.getSelectedItem());
                switch(options) {
                    case "Order Date":
                        orderDatepicker.show();
                        search_order.hide();
                      break;
                    default:
                      orderDatepicker.hide();
                      search_order.setText("");
                      search_order.show();
                }
            }
        });
        

        
        orderDatepicker.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    System.out.println("Selected date: " + format.format(orderDatepicker.getDate()));
                    search_order.setText(format.format(orderDatepicker.getDate()));
                }
            }
        });
    }
    
    private void setUser(){ 
        User currentUser = UserManager.getCurrentUser();
        System.out.println("User: " + currentUser.getFirstname());
        user.setText(currentUser.getFirstname() + ' ' + currentUser.getLastname());
        String role = currentUser.getRole();
        int number = Integer.parseInt(role);
        if(number == 0) {
            usersLink.setVisible(false);
            
        }
    }
    
    private void getAllorders(String query) {
        List<Order> data = hp.getAllOrders(query);
        DefaultTableModel model = (DefaultTableModel) orderTable.getModel();
        TableColumnModel columnModel = orderTable.getColumnModel();
        orderTable.setRowHeight(30);
        JTableHeader header = orderTable.getTableHeader();
//        header.setBackground(Color.BLACK);
//        header.setForeground(Color.WHITE);
        columnModel.getColumn(0).setMinWidth(80);        
        columnModel.getColumn(0).setMaxWidth(80);
        


        model.setNumRows(0); 
        for (Order order : data) {
            int ref = order.getReference();
            String cus_name = order.getCustomerName();
            model.addRow(new Object[]{ref, cus_name, order.getCreatedAt()});
        }
    }
    
        public void findOrders(){
            String options = String.valueOf(searchBy.getSelectedItem());
            String srchValue = search_order.getText();
            String query = null;


            if(!search_order.getText().isBlank()){
                switch(options) {
                    case "All":
    //                    query = "SELECT * FROM orders where reference LIKE'"+srchValue+"%' OR customer_name LIKE'"+srchValue+"%' OR product_id ='"+prod_id+"%' OR updated_at '"+srchValue+"%' ORDER BY updated_at desc";
                       query = "SELECT * FROM orders where reference LIKE '%"+srchValue+"%' OR customer_name LIKE '%"+srchValue+"%' OR updated_at LIKE '%"+srchValue+"%' ORDER BY updated_at desc";

                        break;
                    case "Reference":
                      query = "SELECT * FROM orders where reference LIKE '"+srchValue+"%'";
                      break;
                    case "Customer Name":
                      query = "SELECT * FROM orders where customer_name LIKE '"+srchValue+"%'";
                      break;
                    case "Order Date":
                      query = "SELECT * FROM orders where created_at LIKE '"+srchValue+"%' ORDER BY updated_at desc";
                      break;
                    default:
                      System.out.print("This is blank");
                }
                getAllorders(query);
            } else {
                hp.errorMessageDialog("No value to search");
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

        jCalendar1 = new com.toedter.calendar.JCalendar();
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
        searchBy = new javax.swing.JComboBox<>();
        orderDatepicker = new com.toedter.calendar.JDateChooser();
        search_order = new javax.swing.JTextField();
        find_product = new javax.swing.JButton();
        find_product1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        find_order = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

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
        jPanel2.add(products, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 180, 40));

        purchased.setBackground(new java.awt.Color(237, 121, 13));
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
        jLabel3.setText("Orders");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 130, 40));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setFocusable(false);
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        searchBy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"All", "Reference", "Customer Name", "Order Date"}));
        searchBy.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                searchByInputMethodTextChanged(evt);
            }
        });
        searchBy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchByActionPerformed(evt);
            }
        });
        jPanel4.add(searchBy, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, 110, 30));

        orderDatepicker.setMaxSelectableDate(new java.util.Date(253370739675000L));
        orderDatepicker.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                orderDatepickerKeyPressed(evt);
            }
        });
        jPanel4.add(orderDatepicker, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 60, 250, 30));

        search_order.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        search_order.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        search_order.setToolTipText("");
        search_order.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        search_order.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                search_orderMouseClicked(evt);
            }
        });
        search_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_orderActionPerformed(evt);
            }
        });
        search_order.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                search_orderKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_orderKeyReleased(evt);
            }
        });
        jPanel4.add(search_order, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 60, 250, 30));

        find_product.setBackground(new java.awt.Color(237, 121, 13));
        find_product.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        find_product.setForeground(new java.awt.Color(255, 255, 255));
        find_product.setText("Add New Order");
        find_product.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                find_productActionPerformed(evt);
            }
        });
        jPanel4.add(find_product, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 470, 210, 40));

        find_product1.setBackground(new java.awt.Color(204, 204, 204));
        find_product1.setText("Clear");
        find_product1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                find_product1ActionPerformed(evt);
            }
        });
        jPanel4.add(find_product1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 30, 70, 30));

        orderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Reference", "Customer Name", "Order Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        orderTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orderTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(orderTable);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 740, 350));

        find_order.setBackground(new java.awt.Color(237, 121, 13));
        find_order.setIcon(new javax.swing.ImageIcon(getClass().getResource("/search.png"))); // NOI18N
        find_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                find_orderActionPerformed(evt);
            }
        });
        find_order.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                find_orderKeyPressed(evt);
            }
        });
        jPanel4.add(find_order, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 60, 70, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Search Orders");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, 110, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Order List");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 110, -1));

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
            Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_categoriesActionPerformed

    private void productsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productsActionPerformed
        // TODO add your handling code here:
        Products Frame = new Products();
        Frame.setVisible(true);
        Frame.pack();
        Frame.setLocationRelativeTo(null);
        this.dispose();   
    }//GEN-LAST:event_productsActionPerformed

    private void purchasedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_purchasedActionPerformed
        
    }//GEN-LAST:event_purchasedActionPerformed

    private void usersLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usersLinkActionPerformed
        // TODO add your handling code here:
        Orders Frame = new Orders();
        Frame.setVisible(true);
        Frame.pack();
        Frame.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_usersLinkActionPerformed

    private void searchByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchByActionPerformed
        //       String cat_id = getCategoryId();
        //       String prod = search_product.getText();
        //       String Column = (String) searchBy.getSelectedItem();
        //       String sql = "Select* from categories WHERE " + searchBy + "LIKE '"+prod+"%'";
        //

    }//GEN-LAST:event_searchByActionPerformed

    private void search_orderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search_orderMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_search_orderMouseClicked

    private void search_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_orderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_orderActionPerformed

    private void search_orderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_orderKeyPressed
        findOrdersWithEnter(evt);
    }//GEN-LAST:event_search_orderKeyPressed

    private void search_orderKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_orderKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_search_orderKeyReleased

    private void find_productActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_find_productActionPerformed
        Ordering Frame = new Ordering();
        Frame.setVisible(true);
        Frame.pack();
        Frame.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_find_productActionPerformed

    private void find_product1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_find_product1ActionPerformed
        search_order.setText("");
        searchBy.setSelectedIndex(0);
        getAllorders("SELECT * FROM orders WHERE status= '0' ORDER BY created_at ASC");

    }//GEN-LAST:event_find_product1ActionPerformed

    private void orderTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderTableMouseClicked
        if (evt.getClickCount() == 1) {
            int column = 0;
            int row = orderTable.getSelectedRow();
            String reference = orderTable.getModel().getValueAt(row, column).toString();
            System.out.println("Double "+ reference);
            OrderReference currentRef = new OrderReference(reference);
            OrderManager.setCurrentOrderRef(currentRef);
            
            
            LoopingOrderItem.main(null);
            
            
            
            
            
//            orders.add(order);
            
            
        }
    }//GEN-LAST:event_orderTableMouseClicked

    private void find_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_find_orderActionPerformed
        findOrders();
    }//GEN-LAST:event_find_orderActionPerformed

    private void find_orderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_find_orderKeyPressed
        
    }//GEN-LAST:event_find_orderKeyPressed

    private void searchByInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_searchByInputMethodTextChanged
           // TODO add your handling code here
    }//GEN-LAST:event_searchByInputMethodTextChanged

    private void orderDatepickerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_orderDatepickerKeyPressed
        findOrdersWithEnter(evt);
    }//GEN-LAST:event_orderDatepickerKeyPressed

    public void findOrdersWithEnter(java.awt.event.KeyEvent evt) {
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            findOrders();
            System.out.print("yeyey");
        }
    }
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
    javax.swing.JButton categories;
    javax.swing.JButton dashboard;
    javax.swing.JButton find_order;
    javax.swing.JButton find_product;
    javax.swing.JButton find_product1;
    com.toedter.calendar.JCalendar jCalendar1;
    javax.swing.JLabel jLabel1;
    javax.swing.JLabel jLabel2;
    javax.swing.JLabel jLabel3;
    javax.swing.JLabel jLabel8;
    javax.swing.JPanel jPanel1;
    javax.swing.JPanel jPanel2;
    javax.swing.JPanel jPanel3;
    javax.swing.JPanel jPanel4;
    javax.swing.JScrollPane jScrollPane1;
    javax.swing.JScrollPane jScrollPane2;
    javax.swing.JButton logout;
    com.toedter.calendar.JDateChooser orderDatepicker;
    javax.swing.JTable orderTable;
    javax.swing.JButton products;
    javax.swing.JButton purchased;
    javax.swing.JComboBox<String> searchBy;
    javax.swing.JTextField search_order;
    javax.swing.JLabel user;
    javax.swing.JButton usersLink;
    // End of variables declaration//GEN-END:variables

    private String getPrice() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private int getReference() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private String getCustomerName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
