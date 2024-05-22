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
    private int user_id;
    private String customer_name;
    private int cash;
    private String cash_type;
    private String order_type;
    private String created_at;
    private String updated_at;
    private int status;
            
    
    public Order(int order_id, int reference, int user_id, String customer_name, int cash,String cash_type, String order_type, String created_at, String updated_at, int status) {
        this.order_id = order_id;
        this.reference = reference;
        this.user_id = user_id;
        this.customer_name = customer_name;
        this.cash = cash;
        this.cash_type = cash_type;
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
    
    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }
    
    public String getCashType() {
        return cash_type;
    }
    
    public void setCashType(String cash_type) {
        this.cash_type = cash_type;
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
                    + "      \"user_id\": \""+user_id+"\","   
                    + "      \"customer_name\": \""+customer_name+"\","  
                    + "      \"cash\": \""+cash+"\","
                    + "      \"cash_type\": \""+cash_type+"\","
                    + "      \"order_type\": \""+order_type+"\","
                    + "      \"created_at\": \""+created_at+"\","   
                    + "      \"updated_at\": \""+updated_at+"\","   
                    + "      \"status\": \""+status+"\","   
            + "}," ;
    }
            
}
