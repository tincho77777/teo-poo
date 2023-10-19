package com.esba.biblioteca.entity;

public class Usuario {

	private Long id;
	private String nombre;
	private String mail;

	public Usuario() {
	}

	public Usuario(Long id, String nombre, String mail) {
		this.id = id;
		this.nombre = nombre;
		this.mail = mail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Override
	public String toString() {
		return "Usuario { id: " + id
				+ "\nnombre: " + nombre
				+ " \nmail: " + mail + "}";
	}
}
