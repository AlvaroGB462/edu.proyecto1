package conexion;

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

    private final String url = "jdbc:postgresql://localhost:2004/muc_db";
    private final String user = "postgres";
    private final String password = "ldts123$%";

    // Método para conectar a la base de datos
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

    // Cargar usuarios de la base de datos a la lista en memoria
    public void cargarUsuariosEnLista() {
        String consultaSQL = "SELECT * FROM muc.\"usuario\"";
        try (Connection conexion = conectar();
             Statement statement = conexion.createStatement();
             ResultSet resultado = statement.executeQuery(consultaSQL)) {

            inicio.listaUsuarios.clear(); // Limpiamos la lista antes de agregar datos

            while (resultado.next()) {
                int id = resultado.getInt("id_usuario");
                String alias = resultado.getString("alias");
                String nombre = resultado.getString("nombre");
                String apellidos = resultado.getString("apellido");
                String telefono = resultado.getString("telefono");
                String email = resultado.getString("email");
                String contraseña = resultado.getString("contraseña");

                usuarioDto usuario = new usuarioDto(id, alias, nombre, apellidos, telefono, email, contraseña);
                inicio.listaUsuarios.add(usuario); // Añadimos a la lista
            }
            System.out.println("Datos de usuarios cargados correctamente en la lista.");
        } catch (SQLException e) {
            System.out.println("Error al cargar los datos de usuarios: " + e.getMessage());
        }
    }

    // Método para insertar un usuario en la base de datos
    public void insertarUsuarioEnDB(usuarioDto usuario) {
        String consultaSQL = String.format(
                "INSERT INTO muc.\"usuario\" (id_usuario, alias, nombre, apellido, telefono, email, contraseña) " +
                "VALUES (%d, '%s', '%s', '%s', '%s', '%s', '%s')",
                usuario.getId_usuario(), usuario.getAlias(), usuario.getNombre(), usuario.getApellidos(),
                usuario.getTelefono(), usuario.getEmail(), usuario.getContraseña());

        try (Connection conexion = conectar();
             Statement statement = conexion.createStatement()) {
            statement.executeUpdate(consultaSQL);
            System.out.println("Usuario insertado correctamente en la base de datos.");

            // Actualizamos la lista
            inicio.listaUsuarios.add(usuario);
        } catch (SQLException e) {
            System.out.println("Error al insertar el usuario: " + e.getMessage());
        }
    }

    // Método para modificar un usuario en la base de datos
    public void modificarUsuarioEnDB(usuarioDto usuario) {
        String consultaSQL = String.format(
                "UPDATE muc.\"usuario\" SET alias='%s', nombre='%s', apellido='%s', telefono='%s', email='%s', contraseña='%s' " +
                "WHERE id_usuario=%d",
                usuario.getAlias(), usuario.getNombre(), usuario.getApellidos(), usuario.getTelefono(),
                usuario.getEmail(), usuario.getContraseña(), usuario.getId_usuario());

        try (Connection conexion = conectar();
             Statement statement = conexion.createStatement()) {
            statement.executeUpdate(consultaSQL);
            System.out.println("Usuario modificado correctamente en la base de datos.");

            // Actualizamos la lista
            for (usuarioDto u : inicio.listaUsuarios) {
                if (u.getId_usuario() == usuario.getId_usuario()) {
                    u.setAlias(usuario.getAlias());
                    u.setNombre(usuario.getNombre());
                    u.setApellidos(usuario.getApellidos());
                    u.setTelefono(usuario.getTelefono());
                    u.setEmail(usuario.getEmail());
                    u.setContraseña(usuario.getContraseña());
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar el usuario: " + e.getMessage());
        }
    }

    // Método para borrar un usuario de la base de datos
    public void borrarUsuarioDeDB(int idUsuario) {
        String consultaSQL = String.format("DELETE FROM muc.\"usuario\" WHERE id_usuario = %d", idUsuario);

        try (Connection conexion = conectar();
             Statement statement = conexion.createStatement()) {
            int lineasAfectadas = statement.executeUpdate(consultaSQL);
            if (lineasAfectadas > 0) {
                System.out.println("Usuario borrado de la base de datos.");

                // Actualizamos la lista
                inicio.listaUsuarios.removeIf(usuario -> usuario.getId_usuario() == idUsuario);
            } else {
                System.out.println("No se encontró ningún usuario con el ID proporcionado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al borrar el usuario: " + e.getMessage());
        }
    }

    // Cargar clubs de la base de datos a la lista en memoria
    public void cargarClubsEnLista() {
        String consultaSQL = "SELECT * FROM muc.\"club\"";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try (Connection conexion = conectar();
             Statement statement = conexion.createStatement();
             ResultSet resultado = statement.executeQuery(consultaSQL)) {

            inicio.listaClubs.clear(); // Limpiamos la lista antes de agregar datos

            while (resultado.next()) {
                int id = resultado.getInt("id_club");
                String nombre = resultado.getString("nombre_club");
                String fechaString = resultado.getString("fecha_fundacion").trim();
                LocalDate fecha = LocalDate.parse(fechaString, formatter);

                clubDto club = new clubDto(id, nombre, fecha);
                inicio.listaClubs.add(club); // Añadimos a la lista
            }
            System.out.println("Datos de clubs cargados correctamente en la lista.");
        } catch (SQLException e) {
            System.out.println("Error al cargar los datos de clubs: " + e.getMessage());
        }
    }

    // Insertar un club en la base de datos y actualizar la lista en memoria
    public void insertarClubEnDB(clubDto club) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fechaFundacion = club.getFecha_fundacion().format(formatter);
        String consultaSQL = String.format(
                "INSERT INTO muc.\"club\" (id_club, nombre_club, fecha_fundacion) VALUES (%d, '%s', '%s')",
                club.getId_club(), club.getNombre_club(), fechaFundacion);

        try (Connection conexion = conectar();
             Statement statement = conexion.createStatement()) {
            statement.executeUpdate(consultaSQL);
            System.out.println("Club insertado correctamente en la base de datos.");

            // Actualizamos la lista
            inicio.listaClubs.add(club);
        } catch (SQLException e) {
            System.out.println("Error al insertar el club: " + e.getMessage());
        }
    }

    // Modificar un club en la base de datos y actualizar la lista en memoria
    public void modificarClubEnDB(clubDto club) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fechaFundacion = club.getFecha_fundacion().format(formatter);
        String consultaSQL = String.format(
                "UPDATE muc.\"club\" SET nombre_club='%s', fecha_fundacion='%s' WHERE id_club=%d",
                club.getNombre_club(), fechaFundacion, club.getId_club());

        try (Connection conexion = conectar();
             Statement statement = conexion.createStatement()) {
            statement.executeUpdate(consultaSQL);
            System.out.println("Club modificado correctamente en la base de datos.");

            // Actualizamos la lista
            for (clubDto c : inicio.listaClubs) {
                if (c.getId_club() == club.getId_club()) {
                    c.setNombre_club(club.getNombre_club());
                    c.setFecha_fundacion(club.getFecha_fundacion());
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar el club: " + e.getMessage());
        }
    }

    // Borrar un club de la base de datos y actualizar la lista en memoria
    public void borrarClubDeDB(int idClub) {
        String consultaSQL = String.format("DELETE FROM muc.\"club\" WHERE id_club = %d", idClub);

        try (Connection conexion = conectar();
             Statement statement = conexion.createStatement()) {
            int lineasAfectadas = statement.executeUpdate(consultaSQL);
            if (lineasAfectadas > 0) {
                System.out.println("Club borrado de la base de datos.");

                // Actualizamos la lista
                inicio.listaClubs.removeIf(club -> club.getId_club() == idClub);
            } else {
                System.out.println("No se encontró ningún club con el ID proporcionado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al borrar el club: " + e.getMessage());
        }
    }
}
