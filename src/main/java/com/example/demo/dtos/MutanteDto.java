package com.example.demo.dtos;

public class MutanteDto {

	
	private int id;
	private String nombre;
	private String[] adn;
	private String estado;
	
	
	
	public MutanteDto() {
		
	}



	public MutanteDto(int id, String nombre, String[] adn, String estado) {
		
		this.id = id;
		this.nombre = nombre;
		this.adn = adn;
		this.estado = estado;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String[] getAdn() {
		return adn;
	}



	public void setAdn(String[] adn) {
		this.adn = adn;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
	
}
