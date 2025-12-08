/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author jhery
 */
public class ConexionDB {
    
    private static final String URL = "jdbc:mysql://localhost:3306/bd_delyjhos";
    private static final String USER = "root"; 
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection connection = null;

    public static Connection getConnection() {

        if (connection == null) {
            try {
                Class.forName(DRIVER);
                // Establecer la conexión
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                
                System.out.println("¡Conexion a bd_delyjhos EXITOSA!");
                
            } catch (ClassNotFoundException | SQLException e) {
                System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            }
        }
        return connection;
    }

    // 5. Método para cerrar la conexión (opcional, pero buena práctica)
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Conexion cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexion: " + e.getMessage());
            }
        }
    }
    
    // --- MÉTODO DE PRUEBA ---
    // Puedes ejecutar este archivo (Run File) para probar la conexión
    
    public static void main(String[] args) {
        Connection testCon = ConexionDB.getConnection();
        
        if (testCon != null) {
            System.out.println("Prueba de conexion finalizada.");
            ConexionDB.closeConnection();
        } else {
            System.out.println("La prueba de conexion fallo.");
        }
    }
    
}
