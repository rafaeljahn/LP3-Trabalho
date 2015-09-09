package br.edu.unijui.lp3.model;

import java.time.LocalDateTime;

public class Language {
	
	private int languageId;
	private String name;
	private LocalDateTime lastUpdate;
	
	public int getLanguageId() {
		return languageId;
	}
	
	public void setLanguageId(int languageId) {
		this.languageId = languageId;
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
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Language && this.languageId == ((Language) obj).getLanguageId());
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}