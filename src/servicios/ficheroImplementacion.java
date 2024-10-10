package servicios;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import controladores.inicio;
import dtos.clubDto;
import dtos.usuarioDto;

public class ficheroImplementacion implements ficheroInterfaz{
	
	public void cargarClubsDesdeFichero() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    try (BufferedReader lector = new BufferedReader(new FileReader(inicio.RUTACOMPLETAFICHEROCLUB))) {
	        String linea;
	        while ((linea = lector.readLine()) != null) {
	            String[] datos = linea.split(",");
	            clubDto club = new clubDto(
	            		Integer.parseInt(datos[0]), datos[1], LocalDate.parse(datos[2], formatter));
	            inicio.listaClubs.add(club);
	        }
	        System.out.println("Datos cargados en la lista correctamente."); 
	    } catch (IOException e) {
	        System.out.println("Error al leer del fichero: " + e.getMessage());
	    }
	}
	
	public void cargarUsuariosDesdeFichero() {
        try (BufferedReader br = new BufferedReader(new FileReader(inicio.RUTACOMPLETAFICHEROUSUARIOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                usuarioDto usuario = new usuarioDto(
                        Integer.parseInt(datos[0]), datos[1], datos[2], Integer.parseInt(datos[3]));
               inicio.listaUsuarios.add(usuario);
            }
            System.out.println("Usuarios cargados en la lista desde el fichero.");
        } catch (IOException e) {
            System.out.println("Error al cargar los usuarios desde el fichero: " + e.getMessage());
        }
    }

}
