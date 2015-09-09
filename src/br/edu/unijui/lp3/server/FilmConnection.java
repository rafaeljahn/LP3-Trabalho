package br.edu.unijui.lp3.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import br.edu.unijui.lp3.model.Actor;
import br.edu.unijui.lp3.model.Category;
import br.edu.unijui.lp3.model.Film;
import br.edu.unijui.lp3.model.Film.Rating;
import br.edu.unijui.lp3.model.Language;

public class FilmConnection extends AbstractConnection<Film> {
	
	//FILM
	private final String INSERT_FILM = "INSERT INTO film (title, description, release_year, language_id, original_language_id, rental_duration, rental_rate, length, replacement_cost, rating, special_features) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final String DELETE_FILM = "DELETE FROM film WHERE film_id = ?";
//	private final String UPDATE_FILM = "UPDATE film SET title = ?, description = ?, release_year = ?, language_id = ?, original_language_id = ?, rental_duration = ?, length = ?, replacement_cost = ?, rating = ?, special_features = ? WHERE film_id = ?";
	private final String GET_FILM = "SELECT * FROM film WHERE film_id = ?";
	private final String LIST_FILM = "SELECT f.film_id, f.title, f.description, f.release_year, f.language_id, l.name, f.original_language_id, ol.name, f.rental_duration, f.rental_rate, f.length, f.replacement_cost, f.rating, f.special_features, f.last_update FROM film f JOIN language l ON f.language_id = l.language_id LEFT JOIN language ol ON f.original_language_id = ol.language_id ORDER BY f.title";
	
	//FILM ACTOR
	private final String INSERT_FILM_ACTOR = "INSERT INTO film_actor (film_id, actor_id) VALUES (?, ?)";
	private final String DELETE_FILM_ACTOR = "DELETE FROM film_actor WHERE film_id = ?";
	private final String GET_FILM_ACTOR = "SELECT * FROM film_actor WHERE film_id = ?";
	
	//FILM CATEGORY
	private final String INSERT_FILM_CATEGORY = "INSERT INTO film_category (film_id, category_id) VALUES (?, ?)";
	private final String DELETE_FILM_CATEGORY = "DELETE FROM film_category WHERE film_id = ?";
	private final String GET_FILM_CATEGORY = "SELECT * FROM film_category WHERE film_id = ?";
	
	@Override
	public void insert(Film film) throws SQLException {
		Connection connection = null;
		PreparedStatement ppst =  null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			ppst = connection.prepareStatement(INSERT_FILM, Statement.RETURN_GENERATED_KEYS);
			ppst.setString(1, film.getTitle());
			ppst.setString(2, film.getDescription());
			ppst.setShort(3, film.getYear());
			ppst.setInt(4, film.getLanguage().getLanguageId());
			ppst.setInt(5, film.getOriginalLanguage().getLanguageId());
			ppst.setShort(6, film.getRentalDuration());
			ppst.setDouble(7, film.getRentalRate());
			ppst.setShort(8, film.getLength());
			ppst.setDouble(9, film.getReplaceCost());
			ppst.setString(10, film.getRating().toString());
			ppst.setString(11, film.getSpecialFeatures());
			ppst.execute();

			rs = ppst.getGeneratedKeys();
			rs.next();
			film.setFilmId(rs.getInt(1));
			ppst.close();
			
			ppst = connection.prepareStatement(INSERT_FILM_ACTOR);
			ppst.setInt(1, film.getFilmId());
			for (Actor actor : film.getActors()) {
				ppst.setInt(2, actor.getActorId());
				ppst.execute();
			}
			ppst.close();
			
			ppst = connection.prepareStatement(INSERT_FILM_CATEGORY);
			ppst.setInt(1, film.getFilmId());
			for (Category category : film.getCategories()) {
				ppst.setInt(2, category.getCategoryId());
				ppst.execute();
			}
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
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
	
	@Override
	public void delete(Film film) throws SQLException {
		Connection connection = null;
		PreparedStatement ppst = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			
			ppst = connection.prepareStatement(DELETE_FILM_CATEGORY);
			ppst.setInt(1, film.getFilmId());
			ppst.execute();
			ppst.close();
			
			ppst = connection.prepareStatement(DELETE_FILM_ACTOR);
			ppst.setInt(1, film.getFilmId());
			ppst.execute();
			ppst.close();
			
			ppst = connection.prepareStatement(DELETE_FILM);
			ppst.setInt(1, film.getFilmId());
			ppst.execute();
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
			try {
				if (ppst != null) {
					ppst.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				System.out.println("Erro ao fechar conexão!");
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void update(Film film) throws SQLException {
		throw new SQLException("NÂO FOI POSSÍVEL ATUALIZAR O FILME!!!\nMETODO NÃO IMPLEMENTADO!!!"); 
	}
	
	@Override
	public Film get(Film film) throws SQLException {
		if (film != null && film.getFilmId() > 0) {
			Connection connection = null;
			PreparedStatement ppst = null;
			ResultSet rs = null;
			try {
				connection = getConnection();
				ppst = connection.prepareStatement(GET_FILM);
				ppst.setInt(1, film.getFilmId());
				rs = ppst.executeQuery();
				rs.next();
				
				film = new Film();
				film.setFilmId(rs.getInt(1));
				film.setTitle(rs.getString(2));
				film.setDescription(rs.getString(3));
				film.setYear(rs.getShort(4));
				film.setLanguage(new Language());
				film.getLanguage().setLanguageId(rs.getInt(5));
				film.setOriginalLanguage(new Language());
				film.getOriginalLanguage().setLanguageId(rs.getInt(6));
				film.setRentalDuration(rs.getShort(7));
				film.setRentalRate(rs.getDouble(8));
				film.setLength(rs.getShort(9));
				film.setReplaceCost(rs.getDouble(10));
				film.setRating(Rating.getByDescricao(rs.getString(11)));
				film.setSpecialFeatures(rs.getString(12));
				film.setLastUpadate(rs.getTimestamp(13).toLocalDateTime());
				ppst.close();
				rs.close();
				
				LanguageConnection languageConnection = new LanguageConnection();
				film.setLanguage(languageConnection.get(film.getLanguage()));
				film.setOriginalLanguage(languageConnection.get(film.getOriginalLanguage()));
				
				ActorConnection actorConnection = new ActorConnection();
				Actor actor;
				ppst = connection.prepareStatement(GET_FILM_ACTOR);
				ppst.setInt(1, film.getFilmId());
				rs = ppst.executeQuery();
				while (rs.next()) {
					actor = new Actor();
					actor.setActorId(rs.getInt(1));
					film.addActor(actorConnection.get(actor));
				}
				rs.close();
				ppst.close();
				
				CategoryConnection categoryConnection = new CategoryConnection();
				Category category;
				ppst = connection.prepareStatement(GET_FILM_CATEGORY);
				ppst.setInt(1, film.getFilmId());
				rs = ppst.executeQuery();
				while (rs.next()) {
					category = new Category();
					category.setCategoryId(rs.getInt(2));
					film.addCategory(categoryConnection.get(category));
				}
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
			return film;
		}
		return null;
	}
	
	@Override
	public List<Film> list() throws SQLException {
		List<Film> list = new LinkedList<Film>();
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			st = connection.createStatement();
			rs = st.executeQuery(LIST_FILM);
			
			Film film;
			while (rs.next()) {
				film = new Film();
				film.setFilmId(rs.getInt(1));
				film.setTitle(rs.getString(2));
				film.setDescription(rs.getString(3));
				film.setYear(rs.getShort(4));
				film.setLanguage(new Language());
				film.getLanguage().setLanguageId(rs.getInt(5));
				film.getLanguage().setName(rs.getString(6));
				film.setOriginalLanguage(new Language());
				film.getOriginalLanguage().setLanguageId(rs.getInt(7));
				film.getOriginalLanguage().setName(rs.getString(8));
				film.setRentalDuration(rs.getShort(9));
				film.setRentalRate(rs.getDouble(10));
				film.setLength(rs.getShort(11));
				film.setReplaceCost(rs.getDouble(12));
				film.setRating(Rating.getByDescricao(rs.getString(13)));
				film.setSpecialFeatures(rs.getString(14));
				film.setLastUpadate(rs.getTimestamp(15).toLocalDateTime());
				list.add(film);
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