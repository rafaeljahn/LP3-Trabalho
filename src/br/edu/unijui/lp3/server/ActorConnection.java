package br.edu.unijui.lp3.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import br.edu.unijui.lp3.model.Actor;

public class ActorConnection extends AbstractConnection<Actor> {
	
	private final String INSERT = "INSERT INTO actor (first_name, last_name) VALUES (?, ?)";
	private final String DELETE = "DELETE FROM actor WHERE actor_id = ?";
	private final String UPDATE = "UPDATE actor SET first_name = ?, last_name = ? WHERE actor_id = ?";
	private final String GET = "SELECT * FROM actor WHERE actor_id = ?";
	private final String LIST = "SELECT * FROM actor ORDER BY first_name";
	
	@Override
	public void insert(Actor actor) throws SQLException {
		Connection connection = null;
		PreparedStatement ppst = null;
		try {
			connection = getConnection();
			ppst = connection.prepareStatement(INSERT);
			ppst.setString(1, actor.getFirstName());
			ppst.setString(2, actor.getLastName());
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
	public void delete(Actor actor) throws SQLException {
		Connection connection = null;
		PreparedStatement ppst = null;
		try {
			connection = getConnection();
			ppst = connection.prepareStatement(DELETE);
			ppst.setInt(1, actor.getActorId());
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
	public void update(Actor actor) throws SQLException {
		Connection connection = null;
		PreparedStatement ppst = null;
		try {
			connection = getConnection();
			ppst = connection.prepareStatement(UPDATE);
			ppst.setString(1, actor.getFirstName());
			ppst.setString(2, actor.getLastName());
			ppst.setInt(3, actor.getActorId());
			ppst.execute();
		} catch (Exception e) {
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
	public Actor get(Actor actor) throws SQLException {
		if (actor != null && actor.getActorId() > 0) {
			Connection connection = null;
			PreparedStatement ppst = null;
			ResultSet rs = null;
			try {
				connection = getConnection();
				ppst = connection.prepareStatement(GET);
				ppst.setInt(1, actor.getActorId());
				rs = ppst.executeQuery();
				rs.next();
				
				actor = new Actor();
				actor.setActorId(rs.getInt(1));
				actor.setFirstName(rs.getString(2));
				actor.setLastName(rs.getString(3));
				actor.setLastUpdate(rs.getTimestamp(4).toLocalDateTime());
				return actor;
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
	public List<Actor> list() throws SQLException {
		List<Actor> list = new LinkedList<Actor>();
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			st = connection.createStatement();
			rs = st.executeQuery(LIST);
			
			Actor actor;
			while (rs.next()) {
				actor = new Actor();
				actor.setActorId(rs.getInt(1));
				actor.setFirstName(rs.getString(2));
				actor.setLastName(rs.getString(3));
				actor.setLastUpdate(rs.getTimestamp(4).toLocalDateTime());
				list.add(actor);
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