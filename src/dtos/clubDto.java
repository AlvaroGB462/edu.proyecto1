package dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class clubDto {

	int id_club;
	String nombre_club;
	LocalDate fecha_fundacion;
	
	public int getId_club() {
		return id_club;
	}
	public void setId_club(int id_club) {
		this.id_club = id_club;
	}
	public String getNombre_club() {
		return nombre_club;
	}
	public void setNombre_club(String nombre_club) {
		this.nombre_club = nombre_club;
	}
	public LocalDate getFecha_fundacion() {
		return fecha_fundacion;
	}
	public void setFecha_fundacion(LocalDate fecha_fundacion) {
		this.fecha_fundacion = fecha_fundacion;
	}
	
	public clubDto(int id_club, String nombre_club, LocalDate fecha_fundacion) {
		super();
		this.id_club = id_club;
		this.nombre_club = nombre_club;
		this.fecha_fundacion = fecha_fundacion;
	}
	
	public clubDto() {
		
	}
}
