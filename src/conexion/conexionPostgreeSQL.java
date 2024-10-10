package conexion;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import controladores.inicio;
import dtos.clubDto;
import dtos.usuarioDto;

public class conexionPostgreeSQL {

	private final String url = "jdbc:postgresql://localhost:2004/muc_bd";
	private final String user = "postgres";
	private final String password = "ldts123$%";

	public Connection conectar() {
		Connection conexion = null;
		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection(url, user, password);
			System.out.println("Conexión exitosa a PostgreSQL!");
		} catch (SQLException e) {
			System.out.println("Error de conexión: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Driver no encontrado: " + e.getMessage());
		}
		return conexion;
	}

	public void cargarDatosEnFichero() {
		String consultaSQL = "SELECT * FROM muc.\"usuario\"";
		try (Connection conexion = conectar();
				Statement statement = conexion.createStatement();
				ResultSet resultado = statement.executeQuery(consultaSQL);
				PrintWriter out = new PrintWriter(
						new BufferedWriter(new FileWriter(inicio.RUTACOMPLETAFICHEROUSUARIOS)))) {

			while (resultado.next()) {

				int id = resultado.getInt("id_usuario");
				String nombre = resultado.getString("nombre");
				String apellidos = resultado.getString("apellido");
				int telefono = resultado.getInt("telefono");

				out.println(id + "," + nombre + "," + apellidos + "," + telefono);
			}
			System.out.println("Datos cargados en el fichero correctamente.");
		} catch (SQLException | IOException e) {
			System.out.println("Error al cargar los datos en el fichero: " + e.getMessage());
		}
	}

	public void insertarUsuarioEnDB(usuarioDto usuario) {
		String consultaSQL = String.format(
				"INSERT INTO muc.\"usuario\" (id_usuario, nombre, apellido, telefono) VALUES (%d, '%s', '%s', %d)",
				usuario.getId_usuario(), usuario.getNombre(), usuario.getApellidos(), usuario.getTelefono());
		try (Connection conexion = conectar(); Statement statement = conexion.createStatement()) {
			statement.executeUpdate(consultaSQL);
			System.out.println("Usuario insertado correctamente en la base de datos.");
		} catch (SQLException e) {
			System.out.println("Error al insertar el usuario: " + e.getMessage());
		}
	}

	public void cargarDatosEnFicheroClubs() {
		String consultaSQL = "SELECT * FROM muc.\"club\"";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		try (Connection conexion = conectar();
				Statement statement = conexion.createStatement();
				ResultSet resultado = statement.executeQuery(consultaSQL);
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(inicio.RUTACOMPLETAFICHEROCLUB)))) {

			while (resultado.next()) {
				int id = resultado.getInt("id_club");
				String nombre = resultado.getString("nombre_club");
				String fechaString = resultado.getString("fecha_fundacion");

				fechaString = fechaString.trim();

				LocalDate fecha = LocalDate.parse(fechaString, formatter);

				out.println(id + "," + nombre + "," + fecha.format(formatter));
			}
			System.out.println("Datos cargados en el fichero correctamente.");
		} catch (SQLException | IOException e) {
			System.out.println("Error al cargar los datos en el fichero: " + e.getMessage());
		}
	}

	public void insertarClubEnDB(clubDto club) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String fechaFundacion = club.getFecha_fundacion().format(formatter);
		String consultaSQL = String.format(
				"INSERT INTO muc.\"club\" (id_club, nombre_club, fecha_fundacion) VALUES (%d, '%s', '%s')",
				club.getId_club(), club.getNombre_club(), fechaFundacion);

		try (Connection conexion = conectar(); Statement statement = conexion.createStatement()) {
			statement.executeUpdate(consultaSQL);
			System.out.println("Club insertado correctamente en la base de datos.");
			conexion.close();
		} catch (SQLException e) {
			System.out.println("Error al insertar el club: " + e.getMessage());
		}
	}
}