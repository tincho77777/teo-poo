package com.esba.biblioteca;

import com.esba.biblioteca.configuraciones.DataBaseConfig;
import com.esba.biblioteca.dao.LibroDAO;
import com.esba.biblioteca.entity.Libro;
import com.esba.biblioteca.exceptions.EleccionOpcionLibroException;
import com.esba.biblioteca.exceptions.EleccionOpcionSalidaException;
import com.esba.biblioteca.exceptions.MenuLibroException;
import com.esba.biblioteca.exceptions.MenuSalidaException;
import com.esba.biblioteca.gui.LibrosListWindow;

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
		var libroNuevo = new Libro();
		libroNuevo = altaDeLibro(libroNuevo);

		try {
			agregarLibroABase(libroDAO, libroNuevo);
		} catch (MenuLibroException e) {
			JOptionPane.showMessageDialog(null, "Error al crear el libro: " + e.getMessage());
		}
	}

	private static Libro altaDeLibro(Libro libroNuevo) {
		boolean ingresoExitoso = false;

		while (!ingresoExitoso) {
			var opcionDeAlta = Integer.parseInt(JOptionPane.showInputDialog("Desea ingresar el nuevo libro por teclado o de forma generica?" +
					"\n1.Ingresar por teclado" +
					"\n2.De forma generica:"
			));

			if (opcionDeAlta == 1) {
				var titulo = JOptionPane.showInputDialog("Ingresa el título del libro:");
				var isbn = JOptionPane.showInputDialog("Ingresa el ISBN del libro:");
				var disponible = Boolean.parseBoolean(JOptionPane.showInputDialog("¿El libro está disponible? (true/false):"));
				var autor = JOptionPane.showInputDialog("Ingresa el nombre del autor:");

				if (titulo != null && !titulo.isEmpty() &&
						isbn != null && !isbn.isEmpty() &&
						autor != null && !autor.isEmpty()) {

					libroNuevo = new Libro(titulo, isbn, disponible, autor);
					ingresoExitoso = true;
				} else {
					JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
				}
			} else if (opcionDeAlta == 2) {
				libroNuevo = new Libro(
						"Harry Potter",
						"1234567891235",
						true,
						"J.K.ROWLING"
				);
				ingresoExitoso = true;
			} else {
				JOptionPane.showMessageDialog(null, "La opción ingresada no es correcta.");
			}
		}
		return libroNuevo;
	}

	private static void agregarLibroABase(LibroDAO libroDAO, Libro libroNuevo) throws Exception {
		libroDAO.agregarLibro(libroNuevo);

		var mensaje = "Libro creado con éxito:\n" +
				"Título: " + libroNuevo.getTitulo() + "\n" +
				"ISBN: " + libroNuevo.getIsbn() + "\n" +
				"Disponibilidad: " + libroNuevo.getDisponible() + "\n" +
				"Autor: " + libroNuevo.getAutor();
		JOptionPane.showMessageDialog(null, mensaje);
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

		if (id < 0) {
			JOptionPane.showMessageDialog(null, "ID no válido. Introduce un ID válido.");
			return;
		}

		Libro libroAActualizar = null;
		for (var libro : libros) {
			if (libro.getId() == id) {
				libroAActualizar = libro;
				break;
			}
		}

		if (libroAActualizar != null) {
			modificacionDeLibro(libroAActualizar);
		}

		try {
			libroDAO.actualizarLibro(libroAActualizar);
			JOptionPane.showMessageDialog(null, "Libro actualizado con éxito.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al actualizar el libro: " + e.getMessage());
		}
	}

	private static void modificacionDeLibro(Libro libroAActualizar) {
		var opcionDeAlta = Integer.parseInt(JOptionPane.showInputDialog("Desea modificar el libro por teclado o de forma generica?" +
				"\n1.Ingresar por teclado" +
				"\n2.De forma generica:"
		));

		if (opcionDeAlta == 1) {
			var titulo = JOptionPane.showInputDialog("Ingresa el título del libro:");
			if (titulo != null) {
				libroAActualizar.setTitulo(titulo);
			}

			var isbn = JOptionPane.showInputDialog("Ingresa el ISBN del libro:");
			if (isbn != null && isbn.length() == 13) {
				libroAActualizar.setIsbn(isbn);
			}

			var disponibleString = JOptionPane.showInputDialog("¿El libro está disponible? (true/false): ");
			if (disponibleString != null) {
				if (disponibleString.equalsIgnoreCase("true") ||
						disponibleString.equalsIgnoreCase("false")) {
					var disponible = Boolean.parseBoolean(disponibleString);
					libroAActualizar.setDisponible(disponible);
				}
			}

			var autor = JOptionPane.showInputDialog("Ingresa el nombre del autor:");
			if (autor != null) {
				libroAActualizar.setAutor(autor);
			}
		} else if (opcionDeAlta == 2) {
			libroAActualizar.setTitulo("El Resplandor");
			libroAActualizar.setAutor("Madonna");
		} else {
			JOptionPane.showMessageDialog(null, "La opcion ingresada no es correcta: ");
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