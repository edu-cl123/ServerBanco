package modelos;

import java.io.Serializable;
import java.util.List;

public  class Cuenta implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected int id;
	protected float saldo;
	protected Usuario id_usuario;
	protected List<Transaccion> lista_Transacciones;
	public Cuenta() {
		super();
	}
	public Cuenta(float saldo, Usuario id_usuario, List<Transaccion> lista_Transacciones) {
		super();
		this.saldo = saldo;
		this.id_usuario = id_usuario;
		this.lista_Transacciones = lista_Transacciones;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getSaldo() {
		return saldo;
	}
	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}
	public Usuario getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(Usuario id_usuario) {
		this.id_usuario = id_usuario;
	}
	public List<Transaccion> getLista_Transacciones() {
		return lista_Transacciones;
	}
	public void setLista_Transacciones(List<Transaccion> lista_Transacciones) {
		this.lista_Transacciones = lista_Transacciones;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cuenta other = (Cuenta) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Cuenta [id=" + id + ", saldo=" + saldo + ", id_usuario=" + id_usuario + ", lista_Transacciones="
				+ lista_Transacciones + "]";
	}
	
	

	

}
