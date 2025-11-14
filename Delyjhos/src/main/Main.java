/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;
import vista.Login;

/**
 *
 * @author jhery
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        java.awt.EventQueue.invokeLater(() -> {
            Login loginForm = new Login();
            loginForm.setVisible(true);
            loginForm.setLocationRelativeTo(null);
        });
        
    }
    
}
