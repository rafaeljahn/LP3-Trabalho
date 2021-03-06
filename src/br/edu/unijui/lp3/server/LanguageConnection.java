package br.edu.unijui.lp3.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import br.edu.unijui.lp3.model.Language;

public class LanguageConnection extends AbstractConnection<Language> {
	
	private final String INSERT = "INSERT INTO language (name) VALUES (?)";
	private final String DELETE = "DELETE FROM language WHERE language_id = ?";
	private final String UPDATE = "UPDATE language SET name = ? WHERE language_id = ?";
	private final String GET = "SELECT * FROM language WHERE language_id = ?";
	private final String LIST = "SELECT * FROM language ORDER BY name";
	
	@Override
	public void insert(Language language) throws SQLException {
		Connection connection = null;
		PreparedStatement ppst = null;
		try {
			connection = getConnection();
			ppst = connection.prepareStatement(INSERT);
			ppst.setString(1, language.getName());
			ppst.execute();
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (ppst != null) {
					ppst.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				System.out.println("Erro ao fechar conex�o!");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void delete(Language language) throws SQLException {
		Connection connection = null;
		PreparedStatement ppst = null;
		try {
			connection = getConnection();
			ppst = connection.prepareStatement(DELETE);
			ppst.setInt(1, language.getLanguageId());
			ppst.execute();
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (ppst != null) {
					ppst.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				System.out.println("Erro ao fechar conex�o!");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void update(Language language) throws SQLException {
		Connection connection = null;
		PreparedStatement ppst = null;
		try {
			connection = getConnection();
			ppst = connection.prepareStatement(UPDATE);
			ppst.setString(1, language.getName());
			ppst.setInt(2, language.getLanguageId());
			ppst.execute();
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (ppst != null) {
					ppst.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				System.out.println("Erro ao fechar conex�o!");
				e.printStackTrace();
			}
		}
		ppst.close();
	}
	
	@Override
	public Language get(Language language) throws SQLException {
		if (language != null && language.getLanguageId() > 0) {
			Connection connection = null;
			PreparedStatement ppst = null;
			ResultSet rs = null;
			try {
				connection = getConnection();
				ppst = connection.prepareStatement(GET);
				ppst.setInt(1, language.getLanguageId());
				rs = ppst.executeQuery();
				rs.next();
				
				language = new Language();
				language.setLanguageId(rs.getInt(1));
				language.setName(rs.getString(2));
				language.setLastUpdate(rs.getTimestamp(3).toLocalDateTime());
				return language;
			} catch (SQLException e) {
				throw e;
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (ppst != null) {
						ppst.close();
					}
					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					System.out.println("Erro ao fechar conex�o!");
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	@Override
	public List<Language> list() throws SQLException {
		List<Language> list = new LinkedList<Language>();
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			st = connection.createStatement();
			rs = st.executeQuery(LIST);

			Language language;
			while (rs.next()) {
				language = new Language();
				language.setLanguageId(rs.getInt(1));
				language.setName(rs.getString(2));
				language.setLastUpdate(rs.getTimestamp(3).toLocalDateTime());
				list.add(language);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				System.out.println("Erro ao fechar conex�o!");
				e.printStackTrace();
			}
		}
		return list;
	}
	
}