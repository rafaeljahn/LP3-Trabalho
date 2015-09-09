package br.edu.unijui.lp3.model;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Film {
	
	private int filmId;
	private String title;
	private String description;
	private short year;
	private Language language;
	private Language originalLanguage;
	private short rentalDuration;
	private double rentalRate;
	private short length;
	private double replaceCost;
	private Rating rating;
	private String specialFeatures;
	private LocalDateTime lastUpadate;
	private List<Actor> actors;
	private List<Category> categories;
	
	public Film() {
		actors = new LinkedList<Actor>();
		categories = new LinkedList<Category>();
	}
	
	public int getFilmId() {
		return filmId;
	}

	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public short getYear() {
		return year;
	}

	public void setYear(short year) {
		this.year = year;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Language getOriginalLanguage() {
		return originalLanguage;
	}

	public void setOriginalLanguage(Language originalLanguage) {
		this.originalLanguage = originalLanguage;
	}

	public short getRentalDuration() {
		return rentalDuration;
	}

	public void setRentalDuration(short rentalDuration) {
		this.rentalDuration = rentalDuration;
	}

	public double getRentalRate() {
		return rentalRate;
	}

	public void setRentalRate(double rentalRate) {
		this.rentalRate = rentalRate;
	}

	public short getLength() {
		return length;
	}

	public void setLength(short length) {
		this.length = length;
	}

	public double getReplaceCost() {
		return replaceCost;
	}

	public void setReplaceCost(double replaceCost) {
		this.replaceCost = replaceCost;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public String getSpecialFeatures() {
		return specialFeatures;
	}

	public void setSpecialFeatures(String specialFeatures) {
		this.specialFeatures = specialFeatures;
	}

	public LocalDateTime getLastUpadate() {
		return lastUpadate;
	}

	public void setLastUpadate(LocalDateTime lastUpadate) {
		this.lastUpadate = lastUpadate;
	}
	
	public void addActor(Actor actor) {
		actors.add(actor);
	}
	
	public List<Actor> getActors() {
		return actors;
	}
	
	public void removeActor(Actor actor) {
		actors.remove(actor);
	}
	
	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}
	
	public void addCategory(Category category) {
		categories.add(category);
	}
	
	public List<Category> getCategories() {
		return categories;
	}
	
	public void removeCategory(Category category) {
		categories.remove(category);
	}
	
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public enum Rating {
		G ("G"),
		PG ("PG"),
		PG13 ("PG-13"),
		R ("R"),
		NC17 ("NC-17");
		
		private String descricao;
		
		private Rating(String descricao) {
			this.descricao = descricao;
		}
		
		@Override
		public String toString() {
			return descricao;
		}
		
		public static Rating getByDescricao(String descricao) {
			for (Rating r : values()) {
				if (r.descricao.equals(descricao)) {
					return r;
				}
			}
			return null;
		}
	}
	
}