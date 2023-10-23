package com.esba.biblioteca.DAO;

import com.esba.biblioteca.Configuraciones.DataBaseConfig;
import com.esba.biblioteca.entity.Libro;
import com.esba.biblioteca.exceptions.ConnectionDataBaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class LibroDAO {
	private static final Logger logger = Logger.getLogger(LibroDAO.class.getName());
	private final String jdbcUrl;
	private final String jdbcUsername;
	private final String jdbcPassword;
	private Connection jdbcConnection;

	public LibroDAO(DataBaseConfig dataBaseConfig) {
		this.jdbcUrl = dataBaseConfig.getJdbcUrl();
		this.jdbcUsername = dataBaseConfig.getJdbcUsername();
		this.jdbcPassword = dataBaseConfig.getJdbcPassword();
	}

	public void conectar() throws SQLException {
		if (jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				jdbcConnection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
				logger.info("Conexión exitosa a la base de datos");
			} catch (SQLException e) {
				throw new ConnectionDataBaseException(e);
			}
		}
	}

	public void desconectar() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
			logger.info("Desconexión exitosa a la base de datos");
		}
	}

	public void agregarLibro(Libro libro) throws Exception {
		var query = "INSERT INTO libros (titulo, autor, isbn, activo) VALUES (?, ?, ?, ?)";
		try {
			conectar();
			try (var estado = jdbcConnection.prepareStatement(query)) {
				estado.setString(1, libro.getTitulo());
				estado.setString(2, libro.getAutor());
				estado.setString(3, libro.getIsbn());
				estado.setBoolean(4, libro.getDisponible());
				estado.executeUpdate();
			}
		} catch (SQLException e) {
			throw new ConnectionDataBaseException(e);
		} finally {
			desconectar();
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
				libros.add(new Libro(id, titulo, isbn, disponibilidad, autor));
			}
			desconectar();
			return libros;
		} catch (Exception e) {
			throw new Exception();
			//agregar excepcion
		}
	}

	public void actualizarLibro(Libro libro) throws SQLException {
		var query = "UPDATE libros SET ";
		conectar();
		try (var estado = jdbcConnection.prepareStatement(query)) {
			var valores = new ArrayList<>();
			validarParametros(libro, query, valores);
			reemplazarParametrosEnQuery(libro, valores, estado);
			estado.executeUpdate();
			desconectar();
		} catch (Exception e) {
			//
		}
	}

	private void reemplazarParametrosEnQuery(Libro libro,
	                                         ArrayList<Object> valores,
	                                         PreparedStatement estado) throws SQLException {
		int i = 1;
		for (var valor : valores) {
			estado.setObject(i, valor);
			i++;
		}
		estado.setInt(i, libro.getId());
	}

	private void validarParametros(Libro libro, String query, ArrayList<Object> valores) {
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
	}

	public void eliminarLibro(Integer idLibro) throws Exception {
		var query = "DELETE FROM libros WHERE id = ? ";
		conectar(); //el conectar siempre tiene que estar antes del estado
		try (var estado = jdbcConnection.prepareStatement(query)) {
			estado.setInt(1, idLibro);
			estado.executeUpdate();
			desconectar();
		} catch (Exception e) {
			//
		}

	}
}
