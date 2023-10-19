package com.esba.biblioteca;

import com.esba.biblioteca.DAO.LibroDAO;
import com.esba.biblioteca.entity.Libro;

import java.util.Scanner;

public class BibliotecaApplication {
	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {

		var jdbcUrl = "jdbc:mysql://localhost:3306/biblioteca_esba";
		var jdbcUsername = "root";
		var jdbcPassword = "root";

		var libroDAO = new LibroDAO(jdbcUrl, jdbcUsername, jdbcPassword);

		try {
			crearLibro(libroDAO);
			listarLibros(libroDAO);
			actualizarLibro(libroDAO);
			eliminarLibro(libroDAO);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void crearLibro(LibroDAO libroDAO) throws Exception {
		var libroNuevo = new Libro(
				"Harry Potter",
				"1234567891234",
				true,
				"J.K.ROULING"
		);
		libroDAO.agregarLibro(libroNuevo);
	}

	private static void listarLibros(LibroDAO libroDAO) throws Exception {
		var libros = libroDAO.listarLibros();
		for (var libro : libros) {
			System.out.println(libro);
		}
	}

	private static void actualizarLibro(LibroDAO libroDAO) throws Exception {
		var libros = libroDAO.listarLibros();

		System.out.println("Seleccione el id del libro a modificar: ");
		var id = scanner.nextInt() + 1;

		var libroAActualizar = libros.get(id);

		libroAActualizar.setTitulo("Los siete enanitos");
		libroAActualizar.setAutor("YO");
		libroDAO.actualizarLibro(libroAActualizar);
	}

	private static void eliminarLibro(LibroDAO libroDAO) throws Exception {
		libroDAO.listarLibros();
		System.out.println("Seleccione el id del libro a eliminar: ");
		var id = scanner.nextInt();

		libroDAO.eliminarLibro(id);
	}
}