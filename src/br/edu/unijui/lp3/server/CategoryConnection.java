package br.edu.unijui.lp3.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import br.edu.unijui.lp3.model.Category;

public class CategoryConnection extends AbstractConnection<Category> {
	
	private final String INSERT = "INSERT INTO category (name) VALUES (?)";
	private final String DELETE = "DELETE FROM category WHERE category_id = ?";
	private final String UPDATE = "UPDATE category SET name = ? WHERE category_id = ?";
	private final String GET = "SELECT * FROM category WHERE category_id = ?";
	private final String LIST = "SELECT * FROM category ORDER BY name";
	
	@Override
	public void insert(Category category) throws SQLException {
		Connection connection = null;
		PreparedStatement ppst = null;
		try {
			connection = getConnection();
			ppst = connection.prepareStatement(INSERT);
			ppst.setString(1, category.getName());
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
				System.out.println("Erro ao fechar a conexão!");
				e.printStackTrace();
			}
		}
	}
		
	@Override
	public void delete(Category category) throws SQLException {
		Connection connection = null;
		PreparedStatement ppst = null;
		try {
			connection = getConnection();
			ppst = connection.prepareStatement(DELETE);
			ppst.setInt(1, category.getCategoryId());
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
				System.out.println("Erro ao fechar a conexão!");
				e.printStackTrace();
			}
		}
		ppst.close();
	}
	
	@Override
	public void update(Category category) throws SQLException {
		Connection connection = null;
		PreparedStatement ppst = null;
		try {
			connection = getConnection();
			ppst = connection.prepareStatement(UPDATE);
			ppst.setString(1, category.getName());
			ppst.setInt(2, category.getCategoryId());
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
				System.out.println("Erro ao fechar a conexão!");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public Category get(Category category) throws SQLException {
		if (category != null && category.getCategoryId() > 0) {
			Connection connection = null;
			PreparedStatement ppst = null;
			ResultSet rs = null;
			try {
				connection = getConnection();
				ppst = connection.prepareStatement(GET);
				ppst.setInt(1, category.getCategoryId());
				rs = ppst.executeQuery();
				rs.next();
				
				category = new Category();
				category.setCategoryId(rs.getInt(1));
				category.setName(rs.getString(2));
				category.setLastUpdate(rs.getTimestamp(3).toLocalDateTime());
				return category;
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
					System.out.println("Erro ao fechar a conexão!");
					e.printStackTrace();
				}
			}
			
		}
		return null;
	}
	
	@Override
	public List<Category> list() throws SQLException {
		List<Category> list = new LinkedList<Category>();
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			st = connection.createStatement();
			rs = st.executeQuery(LIST);
			
			Category category;
			while (rs.next()) {
				category = new Category();
				category.setCategoryId(rs.getInt(1));
				category.setName(rs.getString(2));
				category.setLastUpdate(rs.getTimestamp(3).toLocalDateTime());
				list.add(category);
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
				System.out.println("Erro ao fechar a conexão!");
				e.printStackTrace();
			}
		}
		return list;
	}
	
}