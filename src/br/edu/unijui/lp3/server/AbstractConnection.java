package br.edu.unijui.lp3.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractConnection<T> implements DBActions<T> {
	
	private final static String DATABASE_URL = "jdbc:mysql://localhost:3306/sakila_teste";
	private final static String DATABASE_USERNAME = "trabalho";
	private final static String DATABASE_PASSWORD = "trabalho";
	
	public static Connection getConnection() {
		try {
			Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
			return connection;
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro ao tentar conectar-se com o banco!!!");
			e.printStackTrace();
		}
		return null;
	}
	
}