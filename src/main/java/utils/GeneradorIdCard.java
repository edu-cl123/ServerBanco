package utils;

public class GeneradorIdCard {
	
	public static String generador(String dni , String nombre) {
		
		String valor1 = "000";
		String valor2 = dni.substring(0,4);
		String valor3 = nombre.substring(0,1);
		
		String id_card = valor1+"-"+valor2+"-"+valor3;
		return id_card;
	}

	
}
