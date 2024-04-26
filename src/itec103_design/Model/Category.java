/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itec103_design.Model;

/**
 *
 * @author celis
 */
public class Category {
    private int category_id;
    private String category_name;
    private int status;
    
    public Category(int category_id, String category_name, int status) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.status = status;
    }
    
    public int getCategoryId() {
        return category_id;
    }

    public void setCategoryId(int category_id) {
        this.category_id = category_id;
    }
    
    public String getCategoryName() {
        return category_name;
    }

    public void setCategoryName(String category_name) {
        this.category_name = category_name;
    }
    
    public int getStatus() {
        return category_id;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public String toString() {
        return "{" 
                    + "      \"id\": \""+category_id+"\","                 
                    + "      \"name\": \""+category_name+"\","   
                    + "      \"status\": \""+status+"\","   
            + "}," ;
    }
}
