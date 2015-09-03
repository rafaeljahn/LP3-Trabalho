package br.edu.unijui.lp3.model;

import java.time.LocalDateTime;

public class Category {
	
	private int categoryId;
	private String name;
	private LocalDateTime lastUpdate;
	
	public int getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}
	
	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
}