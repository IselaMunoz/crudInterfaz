package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
public class CrudClient {


	 Connection connection;

	    public CrudClient(Connection conn){
	        this.connection = conn;
	    }

	    public Cliente getCliente(int id){
	    	IntegerProperty clienteId = new SimpleIntegerProperty(0);
	        StringProperty nombre=new SimpleStringProperty(""),
	        		apellidos=new SimpleStringProperty(""),
	        		direccion=new SimpleStringProperty("");

	        String query = "SELECT clienteid, nombre, apellidos, direccion " +
	                "FROM cliente " +
	                "WHERE clienteID = " + id ;
	        System.out.println(query);
	        try {
	            Statement stmt = connection.createStatement();
	            ResultSet rs = stmt.executeQuery(query);

	              if (rs.next()) {
	                    clienteId = new SimpleIntegerProperty(rs.getInt("clienteid"));
	                    nombre = new SimpleStringProperty(rs.getString("nombre"));
	                    apellidos = new SimpleStringProperty(rs.getString("apellidos"));
	                    direccion = new SimpleStringProperty(rs.getString("direccion"));
	              }
	            return new Cliente(clienteId, nombre, apellidos, direccion);
	        }
	        catch (java.sql.SQLException ex){
	            ex.printStackTrace();
	            System.out.println("SQLException:␣" + ex.getMessage());
	            System.out.println("SQLState:␣" + ex.getSQLState());
	            System.out.println("VendorError:␣" + ex.getErrorCode());

	            return null;
	        }
	    }
	//Busqueda Cliente por nombre
	    public ArrayList<Cliente> getClientes(String nombreCliente){
	    	IntegerProperty clienteId = new SimpleIntegerProperty(0);
	        StringProperty nombre=new SimpleStringProperty(""),
	        		apellidos=new SimpleStringProperty(""),
	        		direccion=new SimpleStringProperty("");

	        String query = "SELECT clienteid, nombre, apellidos, direccion " +
	                "FROM cliente " +
	                "WHERE nombre = '" + nombreCliente + "'";
	        System.out.println(query);
	        try {
	            Statement stmt = connection.createStatement();
	            ResultSet rs = stmt.executeQuery(query);

	            ArrayList<Cliente> arrayClientes= new ArrayList<>();

	            while(rs.next()) {
	                clienteId = new SimpleIntegerProperty(rs.getInt("clienteid"));
	                System.out.print(clienteId);
	                nombre = new SimpleStringProperty(rs.getString("nombre"));
	                apellidos = new SimpleStringProperty(rs.getString("apellidos"));
	                direccion = new SimpleStringProperty(rs.getString("direccion"));
	                arrayClientes.add(new Cliente(clienteId, nombre, apellidos, direccion));
	            }
	            return arrayClientes;
	        }
	        catch (java.sql.SQLException ex){
	            ex.printStackTrace();
	            System.out.println("SQLException:␣" + ex.getMessage());
	            System.out.println("SQLState:␣" + ex.getSQLState());
	            System.out.println("VendorError:␣" + ex.getErrorCode());

	            return null;
	        }
	    }

	    //Borrar
	    public int deleteCliente(int id){


	        String query = "delete from cliente where clienteID = " + id;

	        int numRegs = 0;
	        try {
	            Statement stmt = connection.createStatement();
	            numRegs = stmt.executeUpdate(query);

	            System.out.println("Cantidad de registros afectados: " + numRegs);
	        }
	        catch (java.sql.SQLException ex){
	            ex.printStackTrace();
	            System.out.println("SQLException:␣" + ex.getMessage());
	            System.out.println("SQLState:␣" + ex.getSQLState());
	            System.out.println("VendorError:␣" + ex.getErrorCode());
	        }

	        return numRegs;
	    }

	    //Insertar
	    public int insertCliente(String nombre, String apellidos, String direccion){

	        String query = "insert into cliente(nombre, apellidos, direccion) " +
	                "values ('" + nombre + "', '" + apellidos + "', '" + direccion + "')";

	        int numRegs = 0;
	        try {
	            Statement stmt = connection.createStatement();
	            numRegs = stmt.executeUpdate(query);

	            System.out.println("Cantidad de registros afectados: " + numRegs);

	        }
	        catch (java.sql.SQLException ex){
	            ex.printStackTrace();
	            System.out.println("SQLException:␣" + ex.getMessage());
	            System.out.println("SQLState:␣" + ex.getSQLState());
	            System.out.println("VendorError:␣" + ex.getErrorCode());
	        }

	        return numRegs;
	    }


	   //Actualizar
	    public int updateCliente(int id1, String nombre1, String apellidos1, String direccion1){

	        String query = "UPDATE cliente set nombre = '" + nombre1 + "', apellidos = '" + apellidos1 + "', direccion = '" +direccion1+ "' where clienteID = " + id1;
	        System.out.println(query);

	        int numRegs = 0;
	        try {
	            Statement stmt = connection.createStatement();
	            numRegs = stmt.executeUpdate(query);

	            System.out.println("Cantidad de registros afectados: " + numRegs);
	        }
	        catch (java.sql.SQLException ex){
	            ex.printStackTrace();
	            System.out.println("SQLException:␣" + ex.getMessage());
	            System.out.println("SQLState:␣" + ex.getSQLState());
	            System.out.println("VendorError:␣" + ex.getErrorCode());
	        }

	        return numRegs;
	    }
}