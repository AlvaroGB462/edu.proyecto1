package controladores;

import java.util.ArrayList;
import java.util.List;

import conexion.conexionPostgreeSQL;
import dtos.clubDto;
import dtos.usuarioDto;
import servicios.ficheroImplementacion;
import servicios.ficheroInterfaz;
import servicios.menuImplementaion;
import servicios.menuInterfaz;
import servicios.operativaImplementacion;
import servicios.operativaInterfaz;

public class inicio {

	public static List<usuarioDto> listaUsuarios = new ArrayList<usuarioDto>();

	public static List<clubDto> listaClubs = new ArrayList<clubDto>();

	public static final String RUTACOMPLETAFICHEROCLUB = "C:\\Users\\agarera\\eclipse-workspace\\edu.proyecto1\\club.txt";

	public static final String RUTACOMPLETAFICHEROUSUARIOS = "C:\\Users\\agarera\\eclipse-workspace\\edu.proyecto1\\usuarios.txt";

	public static void main(String[] args) {

		menuInterfaz mi = new menuImplementaion();
		operativaInterfaz oi = new operativaImplementacion();
		ficheroInterfaz fi = new ficheroImplementacion();

		conexionPostgreeSQL conexionDB = new conexionPostgreeSQL();

		conexionDB.cargarDatosEnFichero();
		conexionDB.cargarDatosEnFicheroClubs();
		
		fi.cargarUsuariosDesdeFichero();
		fi.cargarClubsDesdeFichero();
		

		boolean cerrarMenu = false;
		int opcionSeleccinada;

		do {
			opcionSeleccinada = mi.menuPrincipal();
			switch (opcionSeleccinada) {
			case 0:
				cerrarMenu = true;
				break;
			case 1:
				boolean cerrarUsuario = false;
				do {
					int opcionUsuario = mi.menuUsuario();
					switch (opcionUsuario) {
					case 0:
						cerrarUsuario = true;
						break;
					case 1:
						oi.anyadirUsuario();
						for (usuarioDto usuario : listaUsuarios) {
							System.out.println(usuario.getNombre() + " " + usuario.getApellidos());
						}
						break;

					default:
						System.out.println("Opción seleccionada no válida");
						break;
					}
				} while (!cerrarUsuario);
			case 2:
				boolean cerrarClub = false;
				do {
					int opcionClub = mi.menuClub();
					switch (opcionClub) {
					case 0:
						cerrarClub = true;
						break;
					case 1:
						oi.anyadirClub();
						for (clubDto buscar : listaClubs) {

							System.out.println(buscar.getId_club() + ", " + buscar.getNombre_club() + ", "
									+ buscar.getFecha_fundacion());
						}
						break;

					default:
						System.out.println("Opción seleccionada no válida");
						break;
					}
				} while (!cerrarClub);

				break;
			default:
				System.out.println("Opción seleccionada no válida");
				break;
			}
		} while (!cerrarMenu);
	}
}
