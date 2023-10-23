package com.esba.biblioteca;

import com.esba.biblioteca.Configuraciones.DataBaseConfig;
import com.esba.biblioteca.DAO.LibroDAO;
import com.esba.biblioteca.GUI.LibrosListWindow;
import com.esba.biblioteca.entity.Libro;
import com.esba.biblioteca.exceptions.EleccionOpcionLibroException;
import com.esba.biblioteca.exceptions.EleccionOpcionSalidaException;
import com.esba.biblioteca.exceptions.MenuLibroException;
import com.esba.biblioteca.exceptions.MenuSalidaException;

import javax.swing.*;
import java.util.List;

public class BibliotecaApplication {

	public static void main(String[] args) throws MenuLibroException {

		var dataBaseConfig = new DataBaseConfig(
				"jdbc:mysql://localhost:3306/biblioteca_esba",
				"root",
				"root"
		);
		var libroDAO = new LibroDAO(dataBaseConfig);

		menuLibro(libroDAO);
	}

	private static void menuLibro(LibroDAO libroDAO) throws MenuLibroException {
		var salir = false;

		while (!salir) {
			salir = menuDeOpciones(libroDAO, salir);
		}
	}

	private static boolean menuDeSalida() throws MenuSalidaException {
		var opcionSalida = Integer.parseInt(JOptionPane.showInputDialog("Desea salir del menu?"
				+ "\n1.Salir"
				+ "\n2.Realizar otra acción"
		));

		boolean salir;
		try {
			switch (opcionSalida) {
				case 1:
					salir = true;
					break;
				case 2:
					salir = false;
					break;
				default:
					throw new EleccionOpcionSalidaException();
			}
		} catch (Exception e) {
			throw new MenuSalidaException();
		}
		return salir;
	}

	private static boolean menuDeOpciones(LibroDAO libroDAO,
	                                      boolean salir) throws MenuLibroException {
		var opcion = Integer.parseInt(JOptionPane.showInputDialog("Por favor, ingresa una opcion a realizar: "
				+ "\n1.Crear un Libro"
				+ "\n2.Listar Libros"
				+ "\n3.Actualizar Libro"
				+ "\n4.Eliminar un Libro"
				+ "\n5.Salir")
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
				case 5:
					salir = menuDeSalida();
					break;
				default:
					throw new EleccionOpcionLibroException();
			}
		} catch (Exception e) {
			throw new MenuLibroException();
		}
		return salir;
	}

	private static void crearLibro(LibroDAO libroDAO) throws Exception {
		var libroNuevo = new Libro(
				"Harry Potter",
				"1234567891235",
				true,
				"J.K.ROWLING"
		);

		try {
			libroDAO.agregarLibro(libroNuevo);
			var mensaje = "Libro creado con éxito:\n" +
					"Título: " + libroNuevo.getTitulo() + "\n" +
					"ISBN: " + libroNuevo.getIsbn() + "\n" +
					"Disponibilidad: " + libroNuevo.getDisponible() + "\n" +
					"Autor: " + libroNuevo.getAutor();
			JOptionPane.showMessageDialog(null, mensaje);
		} catch (MenuLibroException e) {
			JOptionPane.showMessageDialog(null, "Error al crear el libro: " + e.getMessage());
		}
	}

	private static void listarLibros(LibroDAO libroDAO) throws Exception {
		try {
			mostrarLibros(libroDAO);
		} catch (MenuLibroException e) {
			JOptionPane.showMessageDialog(null, "Error al listar los libros: " + e.getMessage());
		}

	}

	private static void actualizarLibro(LibroDAO libroDAO) throws Exception {
		var libros = mostrarLibros(libroDAO);

		var id = Integer.parseInt(JOptionPane.showInputDialog("Seleccione el id del libro a modificar: "));

		if (id < 0 || id >= libros.size()) {
			JOptionPane.showMessageDialog(null, "ID no válido. Introduce un ID válido.");
			return;
		}

		var libroAActualizar = libros.get(id);

		libroAActualizar.setTitulo("Los siete enanitos");
		libroAActualizar.setAutor("YO");

		try {
			libroDAO.actualizarLibro(libroAActualizar);
			JOptionPane.showMessageDialog(null, "Libro actualizado con éxito.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al actualizar el libro: " + e.getMessage());
		}
	}

	private static void eliminarLibro(LibroDAO libroDAO) throws Exception {
		mostrarLibros(libroDAO);

		var id = Integer.parseInt(JOptionPane.showInputDialog("Seleccione el id del libro a eliminar: "));

		if (id < 0) {
			JOptionPane.showMessageDialog(null, "ID no válido. Introduce un ID válido.");
			return;
		}

		try {
			libroDAO.eliminarLibro(id);
			JOptionPane.showMessageDialog(null, "Libro eliminado con éxito.");
		} catch (MenuLibroException e) {
			JOptionPane.showMessageDialog(null, "Error al eliminar el libro: " + e.getMessage());
		}
	}

	private static List<Libro> mostrarLibros(LibroDAO libroDAO) throws Exception {
		var libros = libroDAO.listarLibros();
		LibrosListWindow.mostrarLibros(null, libros);
		return libros;
	}
}