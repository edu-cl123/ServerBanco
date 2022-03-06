package Conexion;

import java.io.EOFException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import dao.AdminDAO;
import dao.CuentaDAO;
import dao.TransaccionDAO;
import dao.UsuarioDAO;

import modeloPaqueteEnvio.PaqueteEnv;
import modelos.Admin;
import modelos.Cuenta;
import modelos.Transaccion;
import modelos.Usuario;
import utils.GeneradorIdCard;

public class Connection {

	public static void main(String[] args) {

		ServerSocket servidor;
		Socket cliente = null;
		try {
			servidor = new ServerSocket(1995);
			while (true) {
				cliente = servidor.accept();
				readServerInputs(cliente);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void readServerInputs(final Socket cliente) {
		new Thread(() -> {
			System.out.println("Server");
			try {
				while (!cliente.isClosed()) {

					leer(cliente);
				}
			} catch (Exception ex) {
				try {
					cliente.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}).start();

	}

	public static void leer(Socket cliente) throws SocketException {
		ObjectInputStream dataInputStream = null;
		try {
			dataInputStream = new ObjectInputStream(cliente.getInputStream());

			PaqueteEnv paquete = (PaqueteEnv) dataInputStream.readObject();
			System.out.println(paquete);

			Usuario userFromClient = (Usuario) paquete.getObjeto1();
			Cuenta accountFromClient = (Cuenta) paquete.getObjeto2();
			Admin adminFromClient = (Admin) paquete.getObjeto3();
			PaqueteEnv newPaquete;

			int opcion = paquete.getOpcion();
			switch (opcion) {

			case 1:

				if (AdminDAO.comprobarPin(adminFromClient.getId(), adminFromClient.getPin())) {
					System.out.println("Admin dentro");
					adminFromClient = AdminDAO.getAdminPorId(adminFromClient.getId());
					newPaquete = new PaqueteEnv(1, userFromClient, accountFromClient, adminFromClient, true);
					sendDataToClient(cliente, newPaquete);
					System.out.println(newPaquete);
					System.out.println("Paquete enviado");

				}

				break;

			// --> LOGIN
			case 3:

				if (UsuarioDAO.comprobarPin(userFromClient.getId_card(), userFromClient.getPin())) {
					// EXISTE EL USUARIO
					System.out.println("EL USUARIO INTRODUCIDO ES CORRECTO");
					userFromClient = UsuarioDAO.getUsuarioPorId_card(userFromClient.getId_card());
					accountFromClient = CuentaDAO.getCuentaById_User(userFromClient.getId_card());
					// Paquete enviado
					newPaquete = new PaqueteEnv(3, userFromClient, accountFromClient, true);
					// enviar al cliente
					sendDataToClient(cliente, newPaquete);

				} else {
					System.out.println("EL USUARIO INTRODUCIDO ES INCORRECTO");
					// Paquete enviado
					newPaquete = new PaqueteEnv(3, new Object(), false);
					// enviar al cliente
					sendDataToClient(cliente, newPaquete);
				}
				break;
			// --> REGISTRAR

			case 4:

				System.out.println("Enviando pÃ quete al login");
				// usuario recibido

				System.out.println("eee" + userFromClient);

				String id_cardGenerada = GeneradorIdCard.generador(userFromClient.getDNI(), userFromClient.getNombre());
				System.out.println("SE HA GENERADO UNA NUEVA ID-CARD PARA EL USUARIO REGISTRADO");

				userFromClient.setId_card(id_cardGenerada);
				userFromClient.setId_Admin(AdminDAO.getAdminPorId(1));
				userFromClient = UsuarioDAO.anadir(userFromClient);
				System.out.println("USUARIO AÃ‘ADIDO A LA BD");
				// cuenta creada

				accountFromClient.setId_usuario(userFromClient);

				accountFromClient = CuentaDAO.anadir(accountFromClient);
				System.out.println("CUANTA CREADA");
				System.out.println("CUANTA AÃ‘ADIDA");

				// Paquete enviado
				newPaquete = new PaqueteEnv(4, userFromClient, accountFromClient, true);
				// enviar al cliente
				sendDataToClient(cliente, newPaquete);

				break;
			// --> INGRESAR DINERO
			case 6:

				// true es para añadir
				// CUENTA CON EL INCRESO HECHO Y CAMBIADO EN LA BD
				System.out.println("Cuenta obtenida" + accountFromClient.toString());

				accountFromClient = CuentaDAO.Insertar_Retirar(accountFromClient, true, paquete.getCantidad());
				System.out.println("INGRESO REALIZADO CORRECTAMENTE");
				System.out.println("Cuenta enviada" + accountFromClient);

				// paquete actualizado
				newPaquete = new PaqueteEnv(6, accountFromClient, true);
				sendDataToClient(cliente, newPaquete);

				break;
			// --> RETIRAR DINERO

			case 7:

				// true es para añadir
				// CUENTA CON EL INCRESO HECHO Y CAMBIADO EN LA BD
				System.out.println("Cuenta obtenida" + accountFromClient.toString());

				accountFromClient = CuentaDAO.Insertar_Retirar(accountFromClient, false, paquete.getCantidad());
				System.out.println("RETIRADA REALIZADO CORRECTAMENTE");
				System.out.println("Cuenta enviada" + accountFromClient);

				// paquete actualizado
				newPaquete = new PaqueteEnv(6, accountFromClient, true);
				sendDataToClient(cliente, newPaquete);

				break;

			// --> TRANSACCIONES

			case 8:

				List<Transaccion> ls = TransaccionDAO.ListarTransCuenta(accountFromClient);
				accountFromClient.setLista_Transacciones(ls);
				System.out.println("LISTA DE TRANSACCIONES OPTENIDA");
				System.out.println("LISTA: " + ls);
				// Paquete con la lista insertada
				newPaquete = new PaqueteEnv(8, userFromClient, accountFromClient, true);
				sendDataToClient(cliente, newPaquete);
				break;
				
			case 9:
				List<Usuario> lu=UsuarioDAO.ListarTodos();
				System.out.println("Obtenemos todos los clientes");
				System.out.println(lu);
				newPaquete = new PaqueteEnv(9, lu, true);
				newPaquete.setTodoUsuarios(lu);
				sendDataToClient(cliente, newPaquete);
				break;
				
			case 10:
				List<Cuenta> lc=CuentaDAO.ListarTodos();
				System.out.println("Obtenemos todos los clientes");
				System.out.println(lc);
				newPaquete = new PaqueteEnv(10, lc, true);
				newPaquete.setTodoCuentas(lc);
				sendDataToClient(cliente, newPaquete);
				break;
				
			default:
				break;
			}
		} catch (IOException | ClassNotFoundException e) {
			if (e instanceof EOFException) {
				throw new SocketException(e.getMessage());
			} else if (e instanceof ClassNotFoundException) {
				throw new SocketException("Clase no encontrada");
			} else {
				e.printStackTrace();
			}
		}
	}

	private static void sendDataToClient(Socket client, Object objeto) {
		if (client != null && !client.isClosed()) {
			ObjectOutputStream objectOutputStream;
			try {
				objectOutputStream = new ObjectOutputStream(client.getOutputStream());
				System.out.println(objeto.getClass());
				objectOutputStream.writeObject(objeto);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
