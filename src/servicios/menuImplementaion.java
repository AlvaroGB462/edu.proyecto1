package servicios;

import java.util.Scanner;

public class menuImplementaion implements menuInterfaz {

	Scanner sc = new Scanner(System.in);

	@Override
	public int menuPrincipal() {

		int opcion;

		System.out.println("------------------");
		System.out.println("|0. Cerrar menu  |");
		System.out.println("|1. Usuario      |");
		System.out.println("|2. Club         |");
		System.out.println("------------------");

		opcion = sc.nextInt();

		return opcion;
	}

	public int menuUsuario() {

		int opcion;

		System.out.println("------------------------------");
		System.out.println("|0. Volver al menu principal |");
		System.out.println("|1. Añadir usuario           |");
		System.out.println("|2. Modificar usuario        |");
		System.out.println("|3. Borrar usuario           |");
		System.out.println("------------------------------");

		opcion = sc.nextInt();

		return opcion;
	}
	
	public int menuClub() {

		int opcion;

		System.out.println("------------------------------");
		System.out.println("|0. Volver al menu principal |");
		System.out.println("|1. Añadir club              |");
		System.out.println("|2. Modificar club           |");
		System.out.println("|3. Borrar club              |");
		System.out.println("------------------------------");

		opcion = sc.nextInt();

		return opcion;
	}

}
