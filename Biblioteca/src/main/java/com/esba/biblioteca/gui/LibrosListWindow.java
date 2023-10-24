package com.esba.biblioteca.gui;

import com.esba.biblioteca.entity.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LibrosListWindow extends JDialog {

	public static void mostrarLibros(Frame parent, List<Libro> libros) {
		var dialog = new JDialog(parent, "Libros Disponibles", true); // Hace que la ventana sea modal
		dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		var tableModel = new DefaultTableModel();
		tableModel.addColumn("ID");
		tableModel.addColumn("Título");
		tableModel.addColumn("ISBN");
		tableModel.addColumn("Autor");
		tableModel.addColumn("Disponible");

		for (var libro : libros) {
			tableModel.addRow(new Object[]{
					libro.getId(),
					libro.getTitulo(),
					libro.getIsbn(),
					libro.getAutor(),
					libro.getDisponible()
			});
		}

		var table = new JTable(tableModel);

		dialog.add(new JScrollPane(table));

		dialog.setSize(600, 400);
		dialog.setVisible(true);
	}
}
