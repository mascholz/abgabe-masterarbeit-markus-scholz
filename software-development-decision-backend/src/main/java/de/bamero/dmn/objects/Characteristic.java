package de.bamero.dmn.objects;

import java.util.concurrent.atomic.AtomicInteger;

public class Characteristic {
	
	private static final AtomicInteger count = new AtomicInteger(0);
	private int id;
	private String name;
	private String noCodeComplexity;
	private String noCodeRisk;
	private String lowCodeComplexity;
	private String lowCodeRisk;
	private String codeComplexity;
	private String codeRisk;
	private boolean uncertainty;
	private boolean importance;
	
	public Characteristic() {
		this.id = count.incrementAndGet();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNoCodeComplexity() {
		return noCodeComplexity;
	}
	public void setNoCodeComplexity(String noCodeComplexity) {
		this.noCodeComplexity = noCodeComplexity;
	}
	public String getNoCodeRisk() {
		return noCodeRisk;
	}
	public void setNoCodeRisk(String noCodeRisk) {
		this.noCodeRisk = noCodeRisk;
	}
	public String getLowCodeComplexity() {
		return lowCodeComplexity;
	}
	public void setLowCodeComplexity(String lowCodeComplexity) {
		this.lowCodeComplexity = lowCodeComplexity;
	}
	public String getLowCodeRisk() {
		return lowCodeRisk;
	}
	public void setLowCodeRisk(String lowCodeRisk) {
		this.lowCodeRisk = lowCodeRisk;
	}
	public String getCodeComplexity() {
		return codeComplexity;
	}
	public void setCodeComplexity(String codeComplexity) {
		this.codeComplexity = codeComplexity;
	}
	public String getCodeRisk() {
		return codeRisk;
	}
	public void setCodeRisk(String codeRisk) {
		this.codeRisk = codeRisk;
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
