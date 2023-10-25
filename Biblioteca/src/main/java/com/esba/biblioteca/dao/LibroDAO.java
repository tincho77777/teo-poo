package com.esba.biblioteca.dao;

import com.esba.biblioteca.configuraciones.DataBaseConfig;
import com.esba.biblioteca.entity.Libro;
import com.esba.biblioteca.exceptions.*;

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

	public void agregarLibro(Libro libro) throws AltaLibroException, SQLException {
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
			throw new AltaLibroException(e.getMessage());
		} finally {
			desconectar();
		}
	}

	public List<Libro> listarLibros() throws ListarLibrosException, SQLException {
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
			throw new ListarLibrosException(e.getMessage());
		}
	}

	public void actualizarLibro(Libro libro) throws SQLException, ActualizarLibroException {
		conectar();
		try {
			validarParametros(libro);
			desconectar();
		} catch (Exception e) {
			throw new ActualizarLibroException(e.getMessage());
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

	private void validarParametros(Libro libro) throws SQLException {
		var query = "UPDATE libros SET titulo = ?, autor = ?, isbn = ?, activo = ? WHERE id = ?";
		var estado = jdbcConnection.prepareStatement(query);

		estado.setString(1, libro.getTitulo());
		estado.setString(2, libro.getAutor());
		estado.setString(3, libro.getIsbn());
		estado.setBoolean(4, libro.getDisponible());
		estado.setInt(5, libro.getId());

		estado.executeUpdate();
	}

	public void eliminarLibro(Integer idLibro) throws Exception {
		var query = "DELETE FROM libros WHERE id = ? ";
		conectar(); //el conectar siempre tiene que estar antes del estado
		try (var estado = jdbcConnection.prepareStatement(query)) {
			estado.setInt(1, idLibro);
			estado.executeUpdate();
			desconectar();
		} catch (Exception e) {
			throw new EliminarLibroException(e.getMessage());
		}
	}
}
