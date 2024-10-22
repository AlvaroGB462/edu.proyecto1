package servicios;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import conexion.conexionPostgreeSQL;
import controladores.inicio;
import dtos.clubDto;
import dtos.usuarioDto;

public class operativaImplementacion implements operativaInterfaz {

    @Override
    public void anyadirUsuario() {
        Scanner sc = new Scanner(System.in);
        usuarioDto nuevo = new usuarioDto();

        System.out.println("Escriba su nombre:");
        String nombre = sc.nextLine();

        System.out.println("Escriba sus apellidos:");
        String apellidos = sc.nextLine();

        System.out.println("Escriba su número de teléfono:");
        String telefono = sc.nextLine();

        System.out.println("Escriba su alias:");
        String alias = sc.nextLine();

        System.out.println("Escriba su email:");
        String email = sc.nextLine();

        System.out.println("Escriba su contraseña:");
        String contraseña = sc.nextLine();

        // Asignar valores a los atributos
        nuevo.setId_usuario(util.utilidades.calculoId());
        nuevo.setNombre(nombre);
        nuevo.setApellidos(apellidos);
        nuevo.setTelefono(telefono);
        nuevo.setAlias(alias);
        nuevo.setEmail(email);
        nuevo.setContraseña(contraseña);

        // Insertar en la base de datos
        conexionPostgreeSQL conexionDB = new conexionPostgreeSQL();
        conexionDB.insertarUsuarioEnDB(nuevo);
        inicio.listaUsuarios.add(nuevo);
        System.out.println("Usuario añadido correctamente.");
    }

    public void borrarUsuario() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el número de teléfono del usuario a borrar:");
        String telefono = sc.nextLine(); // Cambiado a String

        usuarioDto usuarioABorrar = null;

        for (usuarioDto usuario : inicio.listaUsuarios) {
            if (usuario.getTelefono().equals(telefono)) { // Comparar con String
                usuarioABorrar = usuario;
                break;
            }
        }

        if (usuarioABorrar != null) {
            inicio.listaUsuarios.remove(usuarioABorrar);
            conexionPostgreeSQL conexionDB = new conexionPostgreeSQL();
            conexionDB.borrarUsuarioDeDB(usuarioABorrar.getId_usuario());
            System.out.println("Usuario borrado correctamente.");
        } else {
            System.out.println("Usuario no encontrado con el número de teléfono proporcionado.");
        }
    }

    public void modificarUsuario() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el número de teléfono del usuario a modificar:");
        String telefonoUsuario = sc.nextLine(); // Cambiado a String

        usuarioDto usuarioAModificar = null;

        for (usuarioDto usuario : inicio.listaUsuarios) {
            if (usuario.getTelefono().equals(telefonoUsuario)) { // Comparar con String
                usuarioAModificar = usuario;
                break;
            }
        }

        if (usuarioAModificar != null) {
            while (true) {
                System.out.println("Usuario encontrado: " + usuarioAModificar.getNombre());
                System.out.println("Seleccione el campo a modificar:");
                System.out.println("1. Nombre");
                System.out.println("2. Apellidos");
                System.out.println("3. Teléfono");
                System.out.println("4. Alias");
                System.out.println("5. Email");
                System.out.println("6. Salir");

                int opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                case 1:
                    System.out.println("Escriba el nuevo nombre:");
                    String nuevoNombre = sc.nextLine();
                    usuarioAModificar.setNombre(nuevoNombre);
                    break;

                case 2:
                    System.out.println("Escriba los nuevos apellidos:");
                    String nuevosApellidos = sc.nextLine();
                    usuarioAModificar.setApellidos(nuevosApellidos);
                    break;

                case 3:
                    System.out.println("Escriba el nuevo número de teléfono:");
                    String nuevoTelefono = sc.nextLine(); // Cambiado a String
                    boolean telefonoEnUso = false;
                    for (usuarioDto usuario : inicio.listaUsuarios) {
                        if (usuario.getTelefono().equals(nuevoTelefono)) {
                            telefonoEnUso = true;
                            break;
                        }
                    }
                    if (!telefonoEnUso) {
                        usuarioAModificar.setTelefono(nuevoTelefono);
                    } else {
                        System.out.println("El número de teléfono ingresado ya está en uso.");
                    }
                    break;

                case 4:
                    System.out.println("Escriba el nuevo alias:");
                    String nuevoAlias = sc.nextLine();
                    usuarioAModificar.setAlias(nuevoAlias);
                    break;

                case 5:
                    System.out.println("Escriba el nuevo email:");
                    String nuevoEmail = sc.nextLine();
                    usuarioAModificar.setEmail(nuevoEmail);
                    break;

                case 6:
                    System.out.println("Saliendo del menú de modificación.");
                    conexionPostgreeSQL conexionDB = new conexionPostgreeSQL();
                    conexionDB.modificarUsuarioEnDB(usuarioAModificar);
                    return;

                default:
                    System.out.println("Opción no válida, por favor intente de nuevo.");
                }
            }
        } else {
            System.out.println("Usuario no encontrado con el número de teléfono proporcionado.");
        }
    }

    public void anyadirClub() {
        Scanner sc = new Scanner(System.in);
        clubDto nuevo = new clubDto();

        System.out.println("Escriba el nombre del club:");
        String nombre = sc.nextLine();

        System.out.println("Escriba la fecha de fundacion:");
        String fechaFundacion = sc.nextLine();

        nuevo.setId_club(util.utilidades.calculoIdClub());
        nuevo.setNombre_club(nombre);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fechaDateTimeCambiada = LocalDate.parse(fechaFundacion, formato);

        nuevo.setFecha_fundacion(fechaDateTimeCambiada);

        conexionPostgreeSQL conexionDB = new conexionPostgreeSQL();
        conexionDB.insertarClubEnDB(nuevo);
        inicio.listaClubs.add(nuevo);
        System.out.println("Club añadido correctamente.");
    }

    public void borrarClub() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el nombre del club a borrar:");
        String nombreClub = sc.nextLine();

        clubDto clubABorrar = null;

        for (clubDto club : inicio.listaClubs) {
            if (club.getNombre_club().equalsIgnoreCase(nombreClub)) {
                clubABorrar = club;
                break;
            }
        }

        if (clubABorrar != null) {
            inicio.listaClubs.remove(clubABorrar);
            conexionPostgreeSQL conexionDB = new conexionPostgreeSQL();
            conexionDB.borrarClubDeDB(clubABorrar.getId_club());
            System.out.println("Club borrado correctamente.");
        } else {
            System.out.println("Club no encontrado con el nombre proporcionado.");
        }
    }

    public void modificarClub() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el nombre del club a modificar:");
        String nombreClub = sc.nextLine();

        clubDto clubAModificar = null;

        for (clubDto club : inicio.listaClubs) {
            if (club.getNombre_club().equalsIgnoreCase(nombreClub)) {
                clubAModificar = club;
                break;
            }
        }

        if (clubAModificar != null) {
            while (true) {
                System.out.println("Club encontrado: " + clubAModificar.getNombre_club());
                System.out.println("Seleccione el campo a modificar:");
                System.out.println("1. Nombre del club");
                System.out.println("2. Fecha de fundación");
                System.out.println("3. Salir");

                int opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                case 1:
                    System.out.println("Escriba el nuevo nombre del club:");
                    String nuevoNombre = sc.nextLine();
                    clubAModificar.setNombre_club(nuevoNombre);
                    break;

                case 2:
                    System.out.println("Escriba la nueva fecha de fundación (dd-MM-yyyy):");
                    String nuevaFecha = sc.nextLine();
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate nuevaFechaDateTime = LocalDate.parse(nuevaFecha, formato);
                    clubAModificar.setFecha_fundacion(nuevaFechaDateTime);
                    break;

                case 3:
                    System.out.println("Saliendo del menú de modificación.");
                    conexionPostgreeSQL conexionDB = new conexionPostgreeSQL();
                    conexionDB.modificarClubEnDB(clubAModificar);
                    return;

                default:
                    System.out.println("Opción no válida, por favor intente de nuevo.");
                }
            }
        } else {
            System.out.println("Club no encontrado con el nombre proporcionado.");
        }
    }
}
