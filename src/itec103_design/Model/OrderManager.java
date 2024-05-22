/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itec103_design.Model;

/**
 *
 * @author celis
 */
public class OrderManager {
    private static OrderReference currentOrder;
    
    public static OrderReference getCurrentOrderRef() {
        return currentOrder;
    }

    public static void setCurrentOrderRef(OrderReference order) {
        currentOrder = order;
    }
}
