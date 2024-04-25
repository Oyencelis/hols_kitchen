/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itec103_design;

/**
 *
 * @author celis
 */
public class User {
    private String user_id;
    private String firstname;    
    private String lastname;
    private String email;
    private String role;

    public User(String user_id, String firstname, String lastname, String email, String role) {
        this.user_id = user_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
    }
    
    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    

    // You might also include other user-related properties and methods

}
