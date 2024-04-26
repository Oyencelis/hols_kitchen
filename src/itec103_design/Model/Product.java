/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itec103_design.Model;

import java.time.Instant;

/**
 *
 * @author celis
 */
public class Product {
    private int product_id;
    private int category_id;
    private String product_name;
    private String price;
    private String created_at;
    private String updated_at;
    private int status;
    
    public Product(int product_id, int category_id, String product_name, String price, String created_at, String updated_at, int status) {
        this.product_id = product_id;
        this.category_id = category_id;
        this.product_name = product_name;
        this.price = price;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.status = status;
        
    }
    
    public int getProductId() {
        return product_id;
    }
    
    public void setProductId(int product_id) {
        this.product_id = product_id;
    }
    
    public int getCategoryId() {
        return category_id;
    }
    
    public void setCategoryId(int category_id) {
        this.category_id = category_id;
    }
    
    public String getProductName() {
        return product_name;
    }
    
    public void setProductName(String product_name) {
        this.product_name = product_name;
    }
    
    public String getPrice() {
        return price;
    }
    
    public void setPrice(String price) {
        this.price = price;
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
        return category_id;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public String toString() {
        
        return "{" 
                    + "      \"product_id\": \""+product_id+"\","                 
                    + "      \"category_id\": \""+category_id+"\","   
                    + "      \"product_name\": \""+product_name+"\","   
                    + "      \"price\": \""+price+"\","   
                    + "      \"created_at\": \""+created_at+"\","   
                    + "      \"updated_at\": \""+updated_at+"\","   
                    + "      \"status\": \""+status+"\","   
            + "}," ;
    }
}
