/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.ConexionDB;
import modelo.entidad.Rol;
import modelo.entidad.Usuario;

/**
 *
 * @author jhery
 */
public class UsuarioDAO {
    // Objeto para la conexión
    private Connection cnn;

    // Constructor del DAO: Cada vez que creemos un UsuarioDAO,
    // se conectará a la BD.
    public UsuarioDAO() {
        // Obtenemos la conexión de nuestra clase
        this.cnn = ConexionDB.getConnection();
    }

    /**
     * Método para validar las credenciales del usuario en la BD.Recibe el nombre de usuario y la contraseña.Devuelve un objeto Usuario si es válido, o null si no lo es.
     * @param username
     * @param password
     * @return 
     */
    public Usuario validarUsuario(String username, String password) {
        
        // 1. Preparamos la consulta SQL
        // Consultamos el usuario y traemos sus datos y los de su ROL
        String sql = "SELECT * FROM USUARIO u " +
                     "INNER JOIN ROL r ON u.id_rol = r.id_rol " +
                     "WHERE u.usuario = ? AND u.contrasena = ? AND u.estado = 'Activo'";
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;

        try {
            stmt = cnn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            // 4. Ejecutamos la consulta
            rs = stmt.executeQuery();

            if (rs.next()) {
                // Creamos el objeto Rol
                Rol rol = new Rol();
                rol.setId_rol(rs.getInt("id_rol"));
                rol.setNombre_rol(rs.getString("nombre_rol"));
                
                // Creamos el objeto Usuario con los datos de la BD
                usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario"));
                usuario.setNombres(rs.getString("nombres"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setContrasena(rs.getString("contrasena")); // Opcional, por seguridad no se suele cargar
                usuario.setEstado(rs.getString("estado"));
                
                // Asignamos el objeto Rol completo al usuario
                usuario.setRol(rol);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al validar usuario: " + e.getMessage());
        } finally {
            //Cerramos los recursos
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        
        return usuario;
    }
    
    // --- AQUÍ IRÍAN OTROS MÉTODOS DEL CRUD ---
    // public boolean agregarUsuario(Usuario user) { ... }
    // public boolean modificarUsuario(Usuario user) { ... }
    // public boolean eliminarUsuario(int idUsuario) { ... }
    // public List<Usuario> listarUsuarios() { ... }
}
