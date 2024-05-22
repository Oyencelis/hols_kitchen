/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package itec103_design;

import static com.mysql.cj.conf.PropertyKey.PASSWORD;
import itec103_design.Connection.DBConnection;
import itec103_design.Helpers.HelperClass;
import itec103_design.Model.Category;
import itec103_design.Model.Product;
import itec103_design.Model.User;
import itec103_design.Model.UserManager;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.sql.PreparedStatement;

/**
 *
 * @author Janice
 */
public class Ordering extends javax.swing.JFrame {
    /**
     * Creates new form JFrame4
     */
    HelperClass hp = null;
    Connection con = null;
    String cus_name = null, 
           ordrtyp = null,
           reference_id = null,
           cash_type = null,
           date = null;
//    dou cash_amount = 0, total = 0;
    double total = 0, cash_amount = 0;
    String[][] orderItem = {};
    private Object button;
    
    public Ordering() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../hols.jpg")));
        initComponents();
        con = DBConnection.connect();
        hp = new HelperClass();
        this.setLocationRelativeTo(null);
        getProductItemAndCat();
        orderTable.setRowHeight(30);
        
        reference_id = getFinalRef();
        reference_num.setText("#"+reference_id);
        getFinalOrder();
        
        
    }
    
    private String getFinalRef(){
        String getNameQuery = "SELECT reference FROM orders ORDER BY order_id DESC LIMIT 1";
        String ref = hp.getDetail(getNameQuery, "reference"); 
        if(ref != null) {
            ref = String.valueOf(Integer.parseInt(ref) + 1);
        } else {
            ref = "1000000001";
        }
        return ref;
    }
    
    public static boolean checkDataIdExists(String[][] data, String dataIdToCheck) {
        for (String[] row : data) {
            for (String element : row) {
                if (element.equals(dataIdToCheck)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private void getFinalOrder() {
        DefaultTableModel model = (DefaultTableModel) orderTable.getModel();
        TableColumnModel columnModel = orderTable.getColumnModel();
        columnModel.getColumn(0).setMinWidth(0);        
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(1).setMinWidth(150);        
        columnModel.getColumn(1).setMaxWidth(150);
        double subtotal = 0;
        model.setNumRows(0); 
        if(orderItem.length == 0) {
            cashInput.setEnabled(false);
            cashInput.setText("");
        } else {
            cashInput.setEnabled(true);
        }
        for (String[] item : orderItem) {
            
            model.addRow(new Object[]{item[0], item[1], item[2], hp.numberFormatter(item[3])});
            subtotal += Integer.parseInt(item[2]) * Double.parseDouble(item[3].replace(",", ""));
        }
        total_amount.setText("PHP "+hp.numberFormatter(String.valueOf(subtotal)));
        total = subtotal;
        if(!cashInput.getText().isBlank()) {
            changeAmount.setText("PHP " + hp.numberFormatter(Double.toString(cash_amount - total)));
        } else {
            changeAmount.setText("PHP 0.00");
        }
        
        
    }
    private void addOrderItem(String prod_id,String prod_name, String qty, String price) {
        // Expand the array size by 1
        int quantityToAdd = 1;
        // Check if the data ID exists and update or add details
        orderItem = updateOrAddAndSumQuantity(orderItem, prod_id, quantityToAdd, prod_name, qty, price);
        getFinalOrder();
        
        
    }
    
    
    
    public static String[][] updateOrAddAndSumQuantity(String[][] data, String dataIdToCheck, int quantityToAdd, String prod_name, String qty, String price) {
        boolean found = false;

        for (int i = 0; i < data.length; i++) {
            if (data[i].length > 0 && data[i][0].equals(dataIdToCheck)) {
                // Parse the current quantity, add the new quantity, and update the row
                int currentQuantity = Integer.parseInt(data[i][2]);
                data[i][2] = String.valueOf(currentQuantity + quantityToAdd);
                found = true;
                break;
            }
        }

        if (!found) {
            // If the data ID was not found, add new details as a new row
            String[][] newData = new String[data.length + 1][];
            for (int i = 0; i < data.length; i++) {
                newData[i] = data[i];
            }
            // New row with the specified ID and quantity
            String[] newDetails = {dataIdToCheck, prod_name, qty, price};
            newData[data.length] = newDetails;
            data = newData;
        }

        return data;
    }
    
    
    public static String[][] removeByIdAndSumQuantity(String[][] data, String dataIdToRemove) {
        boolean found = false;
        int removeIndex = -1;

        for (int i = 0; i < data.length; i++) {
            if (data[i].length > 0 && data[i][0].equals(dataIdToRemove)) {
                // Found the ID to remove
                found = true;
                removeIndex = i;
                break;
            }
        }

        if (found) {
            // Remove the row at removeIndex
            String[][] newData = new String[data.length - 1][];
            int newIndex = 0;
            for (int i = 0; i < data.length; i++) {
                if (i != removeIndex) {
                    newData[newIndex++] = data[i];
                }
            }
            data = newData;
        } else {
            // ID not found, no need to remove
            System.out.println("ID not found.");
        }

//        // Recalculate sum quantity
//        int sumQuantity = 0;
//        for (String[] row : data) {
//            sumQuantity += Integer.parseInt(row[3]);
//        }
//
//        // Update sum quantity in the last row
//        data[data.length - 1][3] = String.valueOf(sumQuantity);

        return data;
    }
    
    
  
    private void getProductItemAndCat(){
        tab_item.removeAll(); 
        List<Category> cat = hp.getAllCategories();
        
        
        tab_item.setFont(new Font("Arial", Font.PLAIN, 14));
        tab_item.setBackground(Color.BLACK);
        tab_item.setForeground(Color.WHITE);
        
        for (Category category : cat) {
            
            String tabName = category.getCategoryName();
            int cat_id = category.getCategoryId();
            String query = "SELECT * FROM products where category_id ='"+cat_id+"' AND status = 0 ORDER BY product_name ASC";
            List<Product> data = hp.getAllProducts(query);
            // Create tab component dynamicallyad

            JPanel tabPanel = new JPanel(new GridLayout(4,0,10,10));
            for (Product prod : data) {
                JButton button = new JButton(
                        "<html>"
                            + "<center>"
                                + "<h2 style='color:orange;'>"
                                    + prod.getProductName()
                                +"</h2>"
                                + "<br />"
                                + "<span>"+hp.numberFormatter(prod.getPrice())+"</span>"
                            + "</center>"
                        + "</html>"
                );
                button.setPreferredSize(new Dimension(40, 40));
                button.setFont(new Font("Segoe UI",Font.BOLD, 15));
//                button.setBackground(new Color(154,150,144));//import java.awt.Color;    
                button.setBackground(Color.DARK_GRAY);//import java.awt.Color;    


                button.setForeground(Color.WHITE);
                button.setFocusPainted(false);
                button.setBorderPainted(false);
                tabPanel.add(button);
                
                // Add action listeners to buttons
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        addOrderItem(String.valueOf(prod.getProductId()), prod.getProductName(), "1", prod.getPrice());
                    }
                });
            }

                // Add the tab to the JTabbedPane
            tab_item.addTab(tabName, tabPanel);

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

        orderTypeGroup = new javax.swing.ButtonGroup();
        mopGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        reference_num = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        customer_name = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        dineIn = new javax.swing.JRadioButton();
        takeOut = new javax.swing.JRadioButton();
        mopCash = new javax.swing.JRadioButton();
        mopGcash = new javax.swing.JRadioButton();
        cashInput = new javax.swing.JTextField();
        price_label = new javax.swing.JLabel();
        placeOrder = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        changeAmount = new javax.swing.JLabel();
        orderItemPane = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        total_amount = new javax.swing.JLabel();
        productPanel = new javax.swing.JScrollPane();
        tab_item = new javax.swing.JTabbedPane();
        backBTN = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(237, 121, 13));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Order Details");

        reference_num.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        reference_num.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        reference_num.setText("Set Reference");
        reference_num.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Customer Name");

        customer_name.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        customer_name.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        customer_name.setToolTipText("");
        customer_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        customer_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customer_nameActionPerformed(evt);
            }
        });
        customer_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                customer_nameKeyPressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Order Type");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Mode Of Payment");

        orderTypeGroup.add(dineIn);
        dineIn.setSelected(true);
        dineIn.setText("Dine In");
        dineIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dineInActionPerformed(evt);
            }
        });

        orderTypeGroup.add(takeOut);
        takeOut.setText("Take Out");

        mopGroup.add(mopCash);
        mopCash.setSelected(true);
        mopCash.setText("Cash");

        mopGroup.add(mopGcash);
        mopGcash.setText("GCash");

        cashInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cashInput.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        cashInput.setToolTipText("");
        cashInput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cashInput.setEnabled(false);
        cashInput.setMaximumSize(new java.awt.Dimension(5, 5));
        cashInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cashInputActionPerformed(evt);
            }
        });
        cashInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cashInputKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cashInputKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cashInputKeyTyped(evt);
            }
        });

        price_label.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        price_label.setText("Cash (PHP)");

        placeOrder.setBackground(new java.awt.Color(237, 121, 13));
        placeOrder.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        placeOrder.setForeground(new java.awt.Color(255, 255, 255));
        placeOrder.setText("Place Order");
        placeOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                placeOrderlogInBtn(evt);
            }
        });
        placeOrder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                placeOrderKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("CHANGE");

        changeAmount.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        changeAmount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        changeAmount.setText("PHP 0.00");
        changeAmount.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(dineIn)
                            .addComponent(takeOut))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(mopCash)
                            .addComponent(mopGcash))
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cashInput, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(customer_name))
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(placeOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                                .addComponent(changeAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(price_label, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(customer_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dineIn)
                    .addComponent(mopCash))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(takeOut)
                    .addComponent(mopGcash))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(price_label)
                .addGap(4, 4, 4)
                .addComponent(cashInput, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(changeAmount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(placeOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        orderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Item", "Qty", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        orderTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orderTableMouseClicked(evt);
            }
        });
        orderItemPane.setViewportView(orderTable);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("TOTAL");

        total_amount.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        total_amount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        total_amount.setText("PHP 0.00");
        total_amount.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(total_amount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(orderItemPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(reference_num, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(reference_num))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(orderItemPane, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(total_amount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        productPanel.setBackground(new java.awt.Color(255, 255, 255));
        productPanel.setViewportView(tab_item);

        backBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/back.png"))); // NOI18N
        backBTN.setBorder(null);
        backBTN.setBorderPainted(false);
        backBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBTNActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Add New Order");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(backBTN)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 491, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(productPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(backBTN)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(productPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 627, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void customer_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customer_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_customer_nameActionPerformed

    private void customer_nameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_customer_nameKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_customer_nameKeyPressed

    private void dineInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dineInActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dineInActionPerformed

    private void cashInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cashInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cashInputActionPerformed

    private void cashInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cashInputKeyPressed
//        String regex = "^\\d*\\.?\\d*$";
//        String value = cashInput.getText();
//        if (!value.matches(regex)) {
//            hp.errorMessageDialog("Enter Number Only");
//            cashInput.setText("");
//            if(!cashInput.getText().isEmpty()) {
//                cashInput.setText("");
//            }
//        }
    }//GEN-LAST:event_cashInputKeyPressed
 
    public String getSelectedValue(JRadioButton b1, JRadioButton b2) {
        AbstractButton selectedButton = getSelectedButton(b1, b2);
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

    private void placeOrderlogInBtn(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_placeOrderlogInBtn

        cus_name = customer_name.getText();
        String orderType = getSelectedValue(dineIn, takeOut);        
        String cashType = getSelectedValue(mopCash, mopGcash);

        String cashVal = cashInput.getText();
        if(orderItem.length == 0) {
            hp.errorMessageDialog("Please add order");
        } else if(customer_name.getText().isBlank()) {
            hp.errorMessageDialog("Customer name is required");
        } else if(cashInput.getText().isBlank()) {
            hp.errorMessageDialog("Please cash amount");
        } else if(total > cash_amount) {
            hp.errorMessageDialog("Cash cannot be less than the total amount");
        } else {
            User currentUser = UserManager.getCurrentUser();
            try{
                    Statement st = con.createStatement();
                
                    String query = "INSERT INTO orders(reference, user_id, customer_name, cash, cash_type, order_type)" +
                    "VALUES('"+reference_id+"','"+currentUser.getUserId()+"', '"+cus_name+"', '"+cash_amount+"', '"+cashType+"', '"+orderType+"')";
                    st.execute(query);
                    boolean success = insertOrderItem(orderItem);
                    if (success) {
                        hp.messageDialog("New order has been added");
                        changeFrame();
                    } else {
                        hp.errorMessageDialog("Something went wrong");
                    }

            }
            
            catch(SQLException ex ){
                Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }//GEN-LAST:event_placeOrderlogInBtn
    private void changeFrame() {
        Orders Frame = new Orders();
        Frame.setVisible(true);
        Frame.pack();
        Frame.setLocationRelativeTo(null);
        this.dispose(); 
    }
    public boolean insertOrderItem(String[][] data) {
        // SQL query to insert data
        String query = "INSERT INTO order_items (order_reference, product_id, qty) VALUES (?, ?, ?)";

        try {
            PreparedStatement statement = con.prepareStatement(query);
            // Iterate over the 2D array and insert each record
            for (String[] row : data) {
                statement.setString(1, reference_id); // reference
                statement.setString(2, row[0]); // prod_id
                statement.setString(3, row[2]); // qty
                statement.addBatch(); // Add the statement to the batch
            }

            // Execute the batch insert
            int[] rowsInserted = statement.executeBatch();

            // Check if all records were inserted successfully
            for (int rows : rowsInserted) {
                if (rows == PreparedStatement.EXECUTE_FAILED) {
                    return false; // Return false if any insertion failed
                }
            }

            return true; // Return true if all insertions were successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if an SQL exception occurred
        }
    }
    private void placeOrderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_placeOrderKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_placeOrderKeyPressed

    private void orderTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderTableMouseClicked
        // TODO add your handling code here:
        
        if (evt.getClickCount() == 1) {
            int column = 0;
            int row = orderTable.getSelectedRow();
            String prod_id = orderTable.getModel().getValueAt(row, column).toString();
            
            orderItem = removeByIdAndSumQuantity(orderItem, prod_id);
            getFinalOrder();
        }
    }//GEN-LAST:event_orderTableMouseClicked

    private void cashInputKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cashInputKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_cashInputKeyTyped

    private void cashInputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cashInputKeyReleased
        // TODO add your handling code here:
        String regex = "^\\d*\\.?\\d*$";
        String value = cashInput.getText();
        if (!value.matches(regex)) {
            hp.errorMessageDialog("Enter Number Only");
            cashInput.setText("");
            if(!cashInput.getText().isEmpty()) {
                cashInput.setText("");
            }
        } else {
            cash_amount = Double.parseDouble(cashInput.getText());
            getFinalOrder();
        }
    }//GEN-LAST:event_cashInputKeyReleased

    private void backBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBTNActionPerformed
        // TODO add your handling code here:
        changeFrame();
    }//GEN-LAST:event_backBTNActionPerformed

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
                new Ordering().setVisible(true);
                
            }
        });
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton backBTN;
    javax.swing.JTextField cashInput;
    javax.swing.JLabel changeAmount;
    javax.swing.JTextField customer_name;
    javax.swing.JRadioButton dineIn;
    javax.swing.JLabel jLabel1;
    javax.swing.JLabel jLabel10;
    javax.swing.JLabel jLabel2;
    javax.swing.JLabel jLabel3;
    javax.swing.JLabel jLabel4;
    javax.swing.JLabel jLabel8;
    javax.swing.JLabel jLabel9;
    javax.swing.JPanel jPanel1;
    javax.swing.JPanel jPanel2;
    javax.swing.JPanel jPanel3;
    javax.swing.JRadioButton mopCash;
    javax.swing.JRadioButton mopGcash;
    javax.swing.ButtonGroup mopGroup;
    javax.swing.JScrollPane orderItemPane;
    javax.swing.JTable orderTable;
    javax.swing.ButtonGroup orderTypeGroup;
    javax.swing.JButton placeOrder;
    javax.swing.JLabel price_label;
    javax.swing.JScrollPane productPanel;
    javax.swing.JLabel reference_num;
    javax.swing.JTabbedPane tab_item;
    javax.swing.JRadioButton takeOut;
    javax.swing.JLabel total_amount;
    // End of variables declaration//GEN-END:variables
}
