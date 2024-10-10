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
        int telefono = sc.nextInt();

        nuevo.setId_usuario(util.utilidades.calculoId());
        nuevo.setNombre(nombre);
        nuevo.setApellidos(apellidos);
        nuevo.setTelefono(telefono);

        conexionPostgreeSQL conexionDB = new conexionPostgreeSQL();
        conexionDB.insertarUsuarioEnDB(nuevo);

        inicio.listaUsuarios.add(nuevo);
        System.out.println("Usuario añadido correctamente.");
    }
    
    public void anyadirClub() {
    	
        Scanner sc = new Scanner(System.in);

        clubDto nuevo = new clubDto();

        System.out.println("Escriba el nombre del club:");
        String nombre = sc.nextLine();

        System.out.println("Escriba la fecha de fundacion:");
        String fechaFundacion = sc.nextLine();

        nuevo.setId_club(util.utilidades.calculoIdClub());;
        nuevo.setNombre_club(nombre);
        
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fechaDateTimeCambiada = LocalDate.parse(fechaFundacion, formato);
        
        nuevo.setFecha_fundacion(fechaDateTimeCambiada);

        conexionPostgreeSQL conexionDB = new conexionPostgreeSQL();
        conexionDB.insertarClubEnDB(nuevo);

        inicio.listaClubs.add(nuevo);
        System.out.println("Club añadido correctamente.");
    }
}
