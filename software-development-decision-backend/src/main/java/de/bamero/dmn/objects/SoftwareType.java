package de.bamero.dmn.objects;

import java.util.List;

import de.bamero.dmn.enums.DevelopmentType;

public class SoftwareType {

	private DevelopmentType developmentType;
	private List<String[]> listDecisionResultRisks;
	private List<ComplexityPair> listDecisionResult;
	private String effort;
	private boolean decisiveFactor;
	private long numberSimple;
	private long numberComplicated;
	private long numberComplex;
	private long numberChaotic;
	
	public SoftwareType(DevelopmentType developmentType) {
		super();
		this.developmentType = developmentType;
	}
	public DevelopmentType getDevelopmentType() {
		return developmentType;
	}
	public void setDevelopmentType(DevelopmentType developmentType) {
		this.developmentType = developmentType;
	}
	public String getEffort() {
		return effort;
	}
	public void setEffort(String effort) {
		this.effort = effort;
	}
	public boolean isDecisiveFactor() {
		return decisiveFactor;
	}
	public void setDecisiveFactor(boolean decisiveFactor) {
		this.decisiveFactor = decisiveFactor;
	}
	public long getNumberSimple() {
		return numberSimple;
	}
	public void setNumberSimple(long numberSimple) {
		this.numberSimple = numberSimple;
	}
	public long getNumberComplicated() {
		return numberComplicated;
	}
	public void setNumberComplicated(long numberComplicated) {
		this.numberComplicated = numberComplicated;
	}
	public long getNumberComplex() {
		return numberComplex;
	}
	public void setNumberComplex(long numberComplex) {
		this.numberComplex = numberComplex;
	}
	public List<String[]> getListDecisionResultRisks() {
		return listDecisionResultRisks;
	}
	public void setListDecisionResultRisks(List<String[]> listDecisionResultRisks) {
		this.listDecisionResultRisks = listDecisionResultRisks;
	}
	public List<ComplexityPair> getListDecisionResult() {
		return listDecisionResult;
	}
	public void setListDecisionResult(List<ComplexityPair> listDecisionResult) {
		this.listDecisionResult = listDecisionResult;
	}
	public long getNumberChaotic() {
		return numberChaotic;
	}
	public void setNumberChaotic(long numberChaotic) {
		this.numberChaotic = numberChaotic;
	}
	
	
	
	
}
