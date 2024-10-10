package util;

import controladores.inicio;
import dtos.clubDto;
import dtos.usuarioDto;

public class utilidades {

	public static int calculoId() {

		if (inicio.listaUsuarios.isEmpty()) {
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

	public static int calculoIdClub() {

		if (inicio.listaClubs.isEmpty()) {
			return 100;
		}

		int id = 0;

		for (clubDto club : inicio.listaClubs) {

			if (club.getId_club() > id) {
				id = club.getId_club();
			}
		}

		return id + 1;
	}
}