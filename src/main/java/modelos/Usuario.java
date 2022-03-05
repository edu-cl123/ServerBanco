package modelos;

import dao.UsuarioDAO;

import java.io.Serializable;

public class Usuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String id_card;
	protected String nombre;
	protected int pin;
	protected String DNI;
	protected Admin Admin;

	public Usuario() {
		super();
	}

	public Usuario(String id_card, int pin) {
		super();
		this.id_card = id_card;
		this.pin = pin;
		
	}

	public Usuario(UsuarioDAO usuario){
		this.id_card = usuario.getId_card();
		this.pin = usuario.getPin();
		this.Admin = usuario.getId_Admin();
		this.DNI = usuario.getDNI();
		this.nombre = usuario.getNombre();
	}

	

	public Usuario(String id_card, String nombre, int pin, String dNI, Admin id_Admin) {
		super();
		this.id_card = id_card;
		this.nombre = nombre;
		this.pin = pin;
		DNI = dNI;
		this.Admin = id_Admin;
	}

	public String getId_card() {
		return id_card;
	}

	public void setId_card(String id_card) {
		this.id_card = id_card;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	

	
	public String getDNI() {
		return DNI;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
	}

	public Admin getId_Admin() {
		return Admin;
	}

	public void setId_Admin(Admin id_Admin) {
		this.Admin = id_Admin;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_card == null) ? 0 : id_card.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id_card == null) {
			if (other.id_card != null)
				return false;
		} else if (!id_card.equals(other.id_card))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id_card=" + id_card + ", nombre=" + nombre + ", pin=" + pin + ", DNI=" + DNI + ", id_Admin="
				+ Admin + "]";
	}


}
