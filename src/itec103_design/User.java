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
    private String fullname;
    private String email;

    public User(String username, String email) {
        this.fullname = username;
        this.email = email;
    }

    public String getUsername() {
        return fullname;
    }

    public void setUsername(String username) {
        this.fullname = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // You might also include other user-related properties and methods

}
