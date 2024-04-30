/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itec103_design.Model;

/**
 *
 * @author celis
 */
public class Order {
    private int order_id;
    private int reference;
    private int product_id;
    private int user_id;
    private String customer_name;
    private int qty;
    private String order_type;
    private String created_at;
    private String updated_at;
    private int status;
            
            
    public Order(int order_id, int reference, int product_id, int user_id, String customer_name, int qty, String order_type, String created_at, String updated_at, int status) {
        this.order_id = order_id;
        this.reference = reference;
        this.product_id = product_id;
        this.user_id = user_id;
        this.customer_name = customer_name;
        this.qty = qty;
        this.order_type = order_type;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.status = status;
        
    } 
    
    public int getOrderId() {
        return order_id;
    }

    public void setOrderId(int order_id) {
        this.order_id = order_id;
    } 
    
    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }
    
    public int getProductId() {
        return product_id;
    }

    public void setProductId(int product_id) {
        this.product_id = product_id;
    }
    
    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }
    
    public String getCustomerName() {
        return customer_name;
    }

    public void setCustomerName(String customer_name) {
        this.customer_name = customer_name;
    }
    
    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
    
    public String getOrderType() {
        return order_type;
    }
    
    public void setOrderType(String order_type) {
        this.order_type = order_type;
    }
    
    
    public String getCreatedAt() {
        return created_at;
    }
    
    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }
    
    public String getUpdatedAt() {
        return updated_at;
    }
    
    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
    }
    
    public int getStatus() {
        return order_id;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public String toString() {
        
        return "{" 
                    + "      \"order_id\": \""+order_id+"\","
                    + "      \"reference\": \""+reference+"\","
                    + "      \"product_id\": \""+product_id+"\","                 
                    + "      \"user_id\": \""+user_id+"\","   
                    + "      \"customer_name\": \""+customer_name+"\","   
                    + "      \"qty\": \""+qty+"\","
                    + "      \"order_type\": \""+order_type+"\","
                    + "      \"created_at\": \""+created_at+"\","   
                    + "      \"updated_at\": \""+updated_at+"\","   
                    + "      \"status\": \""+status+"\","   
            + "}," ;
    }
            
}
