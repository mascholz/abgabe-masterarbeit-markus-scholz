package de.bamero.dmn.objects;

public class ComplexityPair {

	private String key;
	private String complexity;
	private boolean uncertainty;
	private boolean importance;

	public ComplexityPair(String key, String complexity, boolean uncertainty, boolean importance) {
		super();
		this.key = key;
		this.complexity = complexity;
		this.uncertainty = uncertainty;
		this.importance = importance;
	}

	public ComplexityPair(String key, String complexity) {
		super();
		this.key = key;
		this.complexity = complexity;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getComplexity() {
		return complexity;
	}
	public void setComplexity(String complexity) {
		this.complexity = complexity;
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
