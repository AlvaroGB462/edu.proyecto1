package servicios;

import java.util.Scanner;

import controladores.inicio;
import dtos.usuarioDto;
import conexion.conexionPostgreeSQL;

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
}
