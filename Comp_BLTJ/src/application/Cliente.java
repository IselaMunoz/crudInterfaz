package application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Cliente {

	 	private IntegerProperty clienteId;//Se crean las variables privadas con propiedades
	    private StringProperty nombre;
	    private StringProperty apellidos;
	    private StringProperty direccion;

	    public Cliente(IntegerProperty clienteId, StringProperty nombre, StringProperty apellidos, StringProperty direccion) {
			// TODO Auto-generated constructor stub

        this.clienteId = clienteId;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
    }

	public int getClienteId (){
		return clienteId.get();
		}

    public IntegerProperty SetCliente(){
    	return clienteId;
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty setNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos.get();
    }

    public StringProperty setApellidos() {
        return apellidos;
    }

    public String getDireccion() {
        return direccion.get();
    }

    public StringProperty setDireccion() {
        return direccion;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "clienteId=" + clienteId +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }

}
