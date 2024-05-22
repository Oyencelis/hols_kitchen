/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itec103_design.Model;

/**
 *
 * @author celis
 */
public class OrderItem {
    
    private int order_item_id;
    private int order_reference;
    private int product_id;
    private String qty;
    
    public OrderItem(int order_item_id, int order_reference, int product_id, String qty) {
        this.order_item_id = order_item_id;
        this.order_reference = order_reference;
        this.product_id = product_id;
        this.qty = qty;

    }
    
    public int getOrderItemId() {
        return order_item_id;
    }

    public void setOrderItemId(int order_item_id) {
        this.order_item_id = order_item_id;
    }
    
    public int getOrderReference() {
        return order_reference;
    }

    public void setOrderReference(int order_reference) {
        this.order_reference = order_reference;
    }
    
    public int getProductId() {
        return product_id;
    }

    public void getProductId(int product_id) {
        this.product_id = product_id;
    }
    
    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
    
    public String toString() {
        return "{" 
//                    + "      \"order_item_id\": \""+order_item_id+"\","                 
                    + "      \"order_reference\": \""+order_reference+"\","   
                    + "      \"product_id\": \""+product_id+"\","
                    + "      \"qty\": \""+qty+"\","
            + "}," ;
    }
}


