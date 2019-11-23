package com.example.demo.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Mutant implements Serializable{

	private static final long serialVersionUI=1L;//es un numero de version,para verificar que el emisor y el receptor son compatibles
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Es para que no se repitan las ID
	private int id;
	
	private String nombre;
	
	private String[] adn;
	
	private String estado;

	public Mutant() {
		
	}

	public Mutant(int id, String nombre, String[] adn, String estado) {
		
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
