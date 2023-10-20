package com.esba.biblioteca;

import com.esba.biblioteca.Configuraciones.DataBaseConfig;
import com.esba.biblioteca.GUI.LibrosListWindow;
import com.esba.biblioteca.DAO.LibroDAO;
import com.esba.biblioteca.entity.Libro;

import javax.swing.*;
import java.util.Scanner;
import java.util.logging.Logger;

public class BibliotecaApplication {
	private static final Logger logger = Logger.getLogger(BibliotecaApplication.class.getName());
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		var dataBaseConfig = new DataBaseConfig(
				"jdbc:mysql://localhost:3306/biblioteca_esba",
				"root",
				"root"
		);
		var libroDAO = new LibroDAO(dataBaseConfig);

		var opcion = Integer.parseInt(JOptionPane.showInputDialog("Por favor, ingresa una opcion a realizar: "
				+ "\n1.Crear un Libro\n2.Listar Libros\n3.Actualizar Libro\n4.Eliminar un Libro")
		);

		try {
			switch (opcion) {
				case 1:
					crearLibro(libroDAO);
					break;
				case 2:
					listarLibros(libroDAO);
					break;
				case 3:
					actualizarLibro(libroDAO);
					break;
				case 4:
					eliminarLibro(libroDAO);
					break;
				default:
					throw new Exception();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void crearLibro(LibroDAO libroDAO) throws Exception {
		var libroNuevo = new Libro(
				"Harry Potter",
				"1234567891235",
				true,
				"J.K.ROWLING"
		);
		libroDAO.agregarLibro(libroNuevo);
	}

	private static void listarLibros(LibroDAO libroDAO) throws Exception {
		var libros = libroDAO.listarLibros();
		LibrosListWindow.mostrarLibros(libros);
	}

	private static void actualizarLibro(LibroDAO libroDAO) throws Exception {
		var libros = libroDAO.listarLibros();

		var id = Integer.parseInt(JOptionPane.showInputDialog("Seleccione el id del libro a modificar: "));

		var libroAActualizar = libros.get(id);

		libroAActualizar.setTitulo("Los siete enanitos");
		libroAActualizar.setAutor("YO");
		libroDAO.actualizarLibro(libroAActualizar);
	}

	private static void eliminarLibro(LibroDAO libroDAO) throws Exception {
		libroDAO.listarLibros();
		var id = Integer.parseInt(JOptionPane.showInputDialog("Seleccione el id del libro a eliminar: "));
		libroDAO.eliminarLibro(id);
	}
}