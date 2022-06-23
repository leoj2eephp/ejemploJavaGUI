package ejemploncapas.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Leandro
 */
public class Persona {

    public int id;
    public String nombre;
    public Date fechaNacimiento;
    public double estatura;
    public double peso;
    
    public Persona() {
    }
    
    public Persona(String nombre, double estatura, double peso) {
        this.nombre = nombre;
        this.estatura = estatura;
        this.peso = peso;
    }
    
    public static ArrayList<Persona> obtenerPersonas() {
        ArrayList<Persona> listaPersonas = new ArrayList<>();
        try {
            ConexionBD c = new ConexionBD();
            String sql = "SELECT * FROM persona";
            PreparedStatement sentencia = c.conn.prepareStatement(sql);
            ResultSet resultado = sentencia.executeQuery();
            
            while (resultado.next()) {
                Persona p = new Persona();
                p.id = resultado.getInt("id");
                p.nombre = resultado.getString("nombre");
                p.estatura = resultado.getDouble("estatura");
                p.peso = resultado.getDouble("peso");
                
                listaPersonas.add(p);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return listaPersonas;
    }
    
    /**
     * Este método se encarga de insertar un registro Persona a la tabla persona de la base de datos
     * @return int Si es mayor a 0, la inserción fue exitosa. Si es 0, hubo algún error.
     */
    public int insertar() {
        // variable que retornaremos para saber cuántas filas fueron afectadas..
        // si el valor es superior a 0, entonces insertamos correctamente.
        int estado = 0;
        // DEBEMOS usar bloques try catch siempre que interactuamos con terceros.. ya que los mismo
        // podrían no estar disponibles.. por ejemplo, que el server mysql esté abajo
        try {
            ConexionBD c = new ConexionBD();
            String sql = "INSERT INTO persona (nombre, estatura, peso)"
                    + "VALUES (?, ?, ?)";
            PreparedStatement sentencia = c.conn.prepareStatement(sql);
            sentencia.setString(1, nombre);
            // Para transforma un string en un Double, usaremos
            // Double.parseDouble("5")
            sentencia.setDouble(2, estatura);
            sentencia.setDouble(3, peso);

            estado = sentencia.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return estado;
    }
    
    public int actualizar() {
        int estado = 0;
        try {
            ConexionBD c = new ConexionBD();
            String sql = "UPDATE persona SET nombre = ? WHERE id = ?";
            PreparedStatement sentencia = c.conn.prepareStatement(sql);
            sentencia.setString(1, nombre);
            // Para transforma un string en un Double, usaremos
            // Double.parseDouble("5")
            //sentencia.setDouble(2, estatura);
            //sentencia.setDouble(3, peso);
            sentencia.setInt(2, id);

            estado = sentencia.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        return estado;
    }

    @Override
    public String toString() {
        return "NOMBRE: " + nombre + ", PESO: " + peso;
    }
    
    
}
