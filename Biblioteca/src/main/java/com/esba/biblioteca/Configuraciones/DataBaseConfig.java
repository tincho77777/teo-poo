package com.esba.biblioteca.Configuraciones;

public class DataBaseConfig {

	private final String jdbcUrl;
	private final String jdbcUsername;
	private final String jdbcPassword;

	public DataBaseConfig(String jdbcUrl, String jdbcUsername, String jdbcPassword) {
		this.jdbcUrl = jdbcUrl;
		this.jdbcUsername = jdbcUsername;
		this.jdbcPassword = jdbcPassword;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public String getJdbcUsername() {
		return jdbcUsername;
	}

	public String getJdbcPassword() {
		return jdbcPassword;
	}
}
