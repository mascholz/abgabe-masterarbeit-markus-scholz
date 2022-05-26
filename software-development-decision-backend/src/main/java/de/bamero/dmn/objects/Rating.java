package de.bamero.dmn.objects;

public class Rating {
	
	private String key;
	private boolean uncertainty;
	private boolean importance;
		
	public Rating(String key, boolean uncertainty, boolean importance) {
		super();
		this.key = key;
		this.uncertainty = uncertainty;
		this.importance = importance;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public boolean isUncertainty() {
		return uncertainty;
	}
	public void setUncertainty(boolean uncertainty) {
		this.uncertainty = uncertainty;
	}
	public boolean isImportance() {
		return importance;
	}
	public void setImportance(boolean importance) {
		this.importance = importance;
	}
}
