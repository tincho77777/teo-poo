package com.esba.biblioteca.GUI;

import com.esba.biblioteca.entity.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class LibrosListWindow {
	public static void mostrarLibros(List<Libro> libros) {
		var frame = new JFrame("Libros Disponibles");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		var tableModel = new DefaultTableModel();
		tableModel.addColumn("Título");
		tableModel.addColumn("ISBN");
		tableModel.addColumn("Autor");
		tableModel.addColumn("Disponible");

		for (Libro libro : libros) {
			tableModel.addRow(new Object[]{
					libro.getTitulo(),
					libro.getIsbn(),
					libro.getAutor(),
					libro.getDisponible()
			});
		}

		var table = new JTable(tableModel);

		frame.add(new JScrollPane(table));

		frame.setSize(600, 400);
		frame.setVisible(true);
	}
}
