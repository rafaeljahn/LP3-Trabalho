package br.edu.unijui.lp3.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractConnection<T> {
	
	private final static String DATABASE_URL = "jdbc:mysql://localhost:3306/sakila";
	private final static String DATABASE_USERNAME = "jarvis";
	private final static String DATABASE_PASSWORD = "jarvis";
	
	protected Connection connection;
	
	public AbstractConnection() {
		try {
			connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro ao tentar conectar-se com o banco!!!");
			e.printStackTrace();
		}
	}
	
}