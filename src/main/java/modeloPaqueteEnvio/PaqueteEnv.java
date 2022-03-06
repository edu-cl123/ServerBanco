package modeloPaqueteEnvio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import modelos.Cuenta;
import modelos.Transaccion;
import modelos.Usuario;

public class PaqueteEnv implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int opcion;
    protected Object objeto1;
    protected Object objeto2;
    protected Object objeto3;
    protected boolean comprobante;
    protected float cantidad;
    protected List<Usuario> todoUsuarios=new ArrayList<Usuario>();
    protected List<Cuenta> todoCuentas=new ArrayList<Cuenta>();
    
    
    public PaqueteEnv() {
        super();
    }
    
  

	public PaqueteEnv(int opcion, Object objeto1, boolean comprobante) {
        super();
        this.opcion = opcion;
        this.objeto1 = objeto1;
        this.comprobante = comprobante;
    }

    public PaqueteEnv(int opcion, Object objeto1, Object objeto2, boolean comprobante) {
        super();
        this.opcion = opcion;
        this.objeto1 = objeto1;
        this.objeto2 = objeto2;
        this.comprobante = comprobante;
    }
    
    

    public PaqueteEnv(int opcion, Object objeto1, boolean comprobante, float cantidad) {
		super();
		this.opcion = opcion;
		this.objeto1 = objeto1;
		this.comprobante = comprobante;
		this.cantidad = cantidad;
	}

	public PaqueteEnv(int opcion, Object objeto1, Object objeto2, boolean comprobante, float cantidad) {
		super();
		this.opcion = opcion;
		this.objeto1 = objeto1;
		this.objeto2 = objeto2;
		this.comprobante = comprobante;
		this.cantidad = cantidad;
	}
	
	

	public PaqueteEnv(int opcion, Object objeto1, Object objeto2, Object objeto3, boolean comprobante ) {
		super();
		this.opcion = opcion;
		this.objeto1 = objeto1;
		this.objeto2 = objeto2;
		this.objeto3 = objeto3;
		this.comprobante = comprobante;
	}
	
	

	public PaqueteEnv(int opcion, boolean comprobante, List<Usuario> todoUsuarios) {
		super();
		this.opcion = opcion;
		this.comprobante = comprobante;
		this.todoUsuarios = todoUsuarios;
	}
	

	public List<Cuenta> getTodoCuentas() {
		return todoCuentas;
	}

	public void setTodoCuentas(List<Cuenta> todoCuentas) {
		this.todoCuentas = todoCuentas;
	}


	public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    public Object getObjeto1() {
        return objeto1;
    }

    
    
    public List<Usuario> getTodoUsuarios() {
		return todoUsuarios;
	}

	public void setTodoUsuarios(List<Usuario> todoUsuarios) {
		this.todoUsuarios = todoUsuarios;
	}

	public void setObjeto1(Object objeto1) {
        this.objeto1 = objeto1;
    }

    public Object getObjeto2() {
        return objeto2;
    }

    public void setObjeto2(Object objeto2) {
        this.objeto2 = objeto2;
    }

    public boolean isComprobante() {
        return comprobante;
    }

    public void setComprobante(boolean comprobante) {
        this.comprobante = comprobante;
    }
    
    

    public Object getObjeto3() {
		return objeto3;
	}

	public void setObjeto3(Object objeto3) {
		this.objeto3 = objeto3;
	}

	public float getCantidad() {
		return cantidad;
	}

	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaqueteEnv that = (PaqueteEnv) o;

        if (opcion != that.opcion) return false;
        if (comprobante != that.comprobante) return false;
        if (Float.compare(that.cantidad, cantidad) != 0) return false;
        if (objeto1 != null ? !objeto1.equals(that.objeto1) : that.objeto1 != null) return false;
        return objeto2 != null ? objeto2.equals(that.objeto2) : that.objeto2 == null;
    }

    @Override
    public int hashCode() {
        int result = opcion;
        result = 31 * result + (objeto1 != null ? objeto1.hashCode() : 0);
        result = 31 * result + (objeto2 != null ? objeto2.hashCode() : 0);
        result = 31 * result + (comprobante ? 1 : 0);
        result = 31 * result + (cantidad != +0.0f ? Float.floatToIntBits(cantidad) : 0);
        return result;
    }

	@Override
	public String toString() {
		return "PaqueteEnv [opcion=" + opcion + ", objeto1=" + objeto1 + ", objeto2=" + objeto2 + ", objeto3=" + objeto3
				+ ", comprobante=" + comprobante + ", cantidad=" + cantidad + "]";
	}

 
    
    
    
}