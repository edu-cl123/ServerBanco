package conexionBD;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlRootElement(name = "DataConnection")
@XmlAccessorType(XmlAccessType.FIELD)
public class DatosDeConexion {

	private  String server;
	private  String database;
	private  String username;
	private  String password;
	
	public DatosDeConexion() {
		this("","","","");
	}

	public DatosDeConexion(String server, String database, String usename, String password) {
		super();
		this.server = server;
		this.database = database;
		this.username = usename;
		this.password = password;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String usename) {
		this.username = usename;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}
