package com.esba.biblioteca.DAO;

import com.esba.biblioteca.entity.Libro;
import com.esba.biblioteca.exceptions.ConnectionDataBaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {
	private final String jdbcUrl;
	private final String jdbcUsername;
	private final String jdbcPassword;
	private Connection jdbcConnection;

	public LibroDAO(String jdbcUrl, String jdbcUsername, String jdbcPassword) {
		this.jdbcUrl = jdbcUrl;
		this.jdbcUsername = jdbcUsername;
		this.jdbcPassword = jdbcPassword;
	}

	public void conectar() throws Exception {
		if (jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				jdbcConnection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
				System.out.println("Conexión exitosa a la base de datos");
			} catch (Exception e) {
				throw new Exception(e);
			}
		}
	}

	public void desconectar() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
			System.out.println("Desconexión exitosa a la base de datos");
		}
	}

	public void agregarLibro(Libro libro) throws Exception {
		var query = "INSERT INTO libros (titulo, autor, isbn, activo) VALUES (?, ?, ?, ?) ";
		conectar();
		try (var estado = jdbcConnection.prepareStatement(query)) {
			estado.setString(1, libro.getTitulo());
			estado.setString(2, libro.getAutor());
			estado.setString(3, libro.getIsbn());
			estado.setInt(4, libro.getDisponible().equals(true) ? 1 : 0);

			desconectar();
		} catch (Exception e) {
			//agregar excepcion
		}
	}

	public List<Libro> listarLibros() throws Exception {
		var libros = new ArrayList<Libro>();
		var query = "SELECT * FROM libros ";
		conectar();

		try (var estado = jdbcConnection.createStatement()) {
			var resultSet = estado.executeQuery(query);

			while (resultSet.next()) {
				var id = resultSet.getInt("id");
				var titulo = resultSet.getString("titulo");
				var autor = resultSet.getString("autor");
				var isbn = resultSet.getString("isbn");
				var activo = resultSet.getInt("activo");
				var disponibilidad = activo != 0;
				libros.add(new Libro(id, titulo, autor, disponibilidad, isbn ));
			}

			desconectar();
			return libros;
		} catch (Exception e) {
			throw new Exception();
			//agregar excepcion
		}
	}

	public boolean actualizarLibro(Libro libro) throws Exception {
		var query = "UPDATE libros SET ";
		var valores = new ArrayList<>();

		if (libro.getTitulo() != null && !libro.getTitulo().isEmpty()) {
			query += "titulo = ?, ";
			valores.add(libro.getTitulo());
		}

		if (libro.getAutor() != null && !libro.getAutor().isEmpty()) {
			query += "autor = ?, ";
			valores.add(libro.getAutor());
		}

		if (libro.getIsbn() != null && !libro.getIsbn().isEmpty()) {
			query += "isbn = ?, ";
			valores.add(libro.getIsbn());
		}

		if (libro.getDisponible() != null) {
			query += "activo = ?, ";
			valores.add(libro.getDisponible());
		}

		// Eliminar la coma final y agregar la condición WHERE
		query = query.substring(0, query.length() - 2) + " WHERE id = ?";

		conectar();
		var estado = jdbcConnection.prepareStatement(query);

		int i = 1;
		for (var valor : valores) {
			estado.setObject(i, valor);
			i++;
		}

		estado.setInt(i, libro.getId());

		var filaActualizada = estado.executeUpdate() > 0;
		estado.close();
		desconectar();
		return filaActualizada;
	}

	public void eliminarLibro(Integer idLibro) throws Exception {
		var query = "DELETE FROM libros WHERE id = ? ";
		conectar();
		var estado = jdbcConnection.prepareStatement(query);
		estado.setInt(1, idLibro);
		estado.executeUpdate();

		estado.close();
		desconectar();
	}
}
