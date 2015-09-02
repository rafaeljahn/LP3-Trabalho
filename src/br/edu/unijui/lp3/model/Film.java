package br.edu.unijui.lp3.model;

import java.time.LocalDateTime;

public class Film {
	private String filmId;
	private String title;
	private long description;
	private short year;
	private int languageId;
	private int originalLanguageId;
	private int rentalDuration;
	private double rentalRate;
	private int length;
	private int replaceCost;
	private Rating rating;
	private String specialFeatures;
	private LocalDateTime lastUpadate;
	
	public String getFilmId() {
		return filmId;
	}

	public void setFilmId(String filmId) {
		this.filmId = filmId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getDescription() {
		return description;
	}

	public void setDescription(long description) {
		this.description = description;
	}

	public short getYear() {
		return year;
	}

	public void setYear(short year) {
		this.year = year;
	}

	public int getLanguageId() {
		return languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public int getOriginalLanguageId() {
		return originalLanguageId;
	}

	public void setOriginalLanguageId(int originalLanguageId) {
		this.originalLanguageId = originalLanguageId;
	}

	public int getRentalDuration() {
		return rentalDuration;
	}

	public void setRentalDuration(int rentalDuration) {
		this.rentalDuration = rentalDuration;
	}

	public double getRentalRate() {
		return rentalRate;
	}

	public void setRentalRate(double rentalRate) {
		this.rentalRate = rentalRate;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getReplaceCost() {
		return replaceCost;
	}

	public void setReplaceCost(int replaceCost) {
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

	public enum Rating {
		PG13,
		G,
		PG,
		R,
		NC17,
	}
}
