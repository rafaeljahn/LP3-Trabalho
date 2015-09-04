package br.edu.unijui.lp3.server;

import java.sql.SQLException;
import java.util.List;

public interface DBActions<T> {
	
	public void insert(T obj) throws SQLException;
	public void delete(T obj) throws SQLException;
	public void update(T obj) throws SQLException;
	public T get(T obj) throws SQLException;
	public List<T> list() throws SQLException;
	
}