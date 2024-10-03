package util;

import controladores.inicio;
import dtos.usuarioDto;


public class utilidades {
    
    public static int calculoId() {
        
        if (inicio.listaUsuarios == null || inicio.listaUsuarios.isEmpty()) {
            return 1;
        }

        int id = 0;

        for (usuarioDto usuario : inicio.listaUsuarios) {
            
            if (usuario.getId_usuario() > id) {
                id = usuario.getId_usuario();
            }
        }

        return id + 1;
    }
}