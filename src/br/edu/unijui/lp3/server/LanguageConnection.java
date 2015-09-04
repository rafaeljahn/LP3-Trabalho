package br.edu.unijui.lp3.server;

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
	private final String LIST = "SELECT * FROM language";
	
	public void insert(Language language) throws SQLException {
		PreparedStatement ppst = connection.prepareStatement(INSERT);
		ppst.setString(1, language.getName());
		ppst.execute();
		ppst.close();
	}
	
	public void delete(Language language) throws SQLException {
		PreparedStatement ppst = connection.prepareStatement(DELETE);
		ppst.setInt(1, language.getLanguageId());
		ppst.execute();
		ppst.close();
	}
	
	public void update(Language language) throws SQLException {
		PreparedStatement ppst = connection.prepareStatement(UPDATE);
		ppst.setString(1, language.getName());
		ppst.setInt(2, language.getLanguageId());
		ppst.execute();
		ppst.close();
	}
	
	public Language get(Language language) throws SQLException {
		PreparedStatement ppst = connection.prepareStatement(GET);
		ppst.setInt(1, language.getLanguageId());
		ResultSet rs = ppst.executeQuery();
		rs.next();
		
		language = new Language();
		language.setLanguageId(rs.getInt(0));
		language.setName(rs.getString(2));
		language.setLastUpdate(rs.getTimestamp(3).toLocalDateTime());
		ppst.close();
		return language;
	}
	
	public List<Language> list() throws SQLException {
		List<Language> list = new LinkedList<Language>();
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(LIST);
		Language language;
		while (rs.next()) {
			language = new Language();
			language.setLanguageId(rs.getInt(1));
			language.setName(rs.getString(2));
			language.setLastUpdate(rs.getTimestamp(3).toLocalDateTime());
			list.add(language);
		}
		st.close();
		return list;
	}
	
}