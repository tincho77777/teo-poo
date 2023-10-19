package com.esba.biblioteca.entity;

public class Libro {
	private Integer id;
	private String titulo;
	private String isbn;
	private Boolean disponible;
	private String autor;

	public Libro() {
	}

	public Libro(String titulo, String isbn, Boolean disponible, String autor) {
		this.titulo = titulo;
		this.isbn = isbn;
		this.disponible = disponible;
		this.autor = autor;
	}

	public Libro(Integer id, String titulo, String isbn, Boolean disponible, String autor) {
		this.id = id;
		this.titulo = titulo;
		this.isbn = isbn;
		this.disponible = disponible;
		this.autor = autor;
	}

	public Integer getId() {
		return id;
	}
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String ejemplares) {
		this.isbn = isbn;
	}

	public Boolean getDisponible() {
		return disponible;
	}

	public void setDisponible(Boolean disponible) {
		this.disponible = disponible;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	@Override
	public String toString() {
		return "Libro { id: " + id
				+ "\ntitulo: " + titulo
				+ "\nisbn: " + isbn
				+ "\nautor: " + autor
				+ "\ndisponibilidad: " + disponible + " }";
	}
}
