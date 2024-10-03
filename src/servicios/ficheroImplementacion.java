package servicios;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import controladores.inicio;
import dtos.usuarioDto;

public class ficheroImplementacion implements ficheroInterfaz{
	
	public void cargarUsuariosDesdeFichero() {
        try (BufferedReader br = new BufferedReader(new FileReader(inicio.RUTA_FICHERO))) {
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
