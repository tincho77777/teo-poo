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
			if (!salir) {
				salir = menuDeSalida(libroDAO, salir);
			}
		}
	}

	private static boolean menuDeSalida(LibroDAO libroDAO,
	                                    boolean salir) throws MenuSalidaException {
		var opcionSalida = Integer.parseInt(JOptionPane.showInputDialog("Desea salir del menu?"
				+ "\n1.Salir"
				+ "\n2.Realizar otra acción"
		));

		try {
			switch (opcionSalida) {
				case 1:
					salir = true;
					break;
				case 2:
					menuDeOpciones(libroDAO, salir);
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
					salir = menuDeSalida(libroDAO, salir);
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
		libroDAO.agregarLibro(libroNuevo);
	}

	private static void listarLibros(LibroDAO libroDAO) throws Exception {
		var libros = libroDAO.listarLibros();
		var frameTemporal = new JFrame();
		LibrosListWindow.mostrarLibros(frameTemporal, libros);
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