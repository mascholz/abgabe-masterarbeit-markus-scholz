package de.bamero.dmn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.repository.DecisionDefinition;
import org.camunda.bpm.engine.variable.VariableMap;

import de.bamero.dmn.objects.ComplexityPair;

public class ProcessDmn {

	DecisionService decisionService;

	public ProcessDmn(DecisionService decisionService) {
		super();
		this.decisionService = decisionService;
	}

	public static HashMap<String, String> getDecisionDefinitionFromDmn(List<DecisionDefinition> listDecisionDefinition,
			String dmnId) {
		HashMap<String, String> listKey = new HashMap<String, String>();
		for (DecisionDefinition decisionDefinition : listDecisionDefinition) {
			if (dmnId.equals(decisionDefinition.getDecisionRequirementsDefinitionKey())) {
				listKey.put(decisionDefinition.getKey(), decisionDefinition.getName());
			}
		}
		return listKey;
	}

	public List<String[]> getRisks(List<String> listKey, VariableMap variables, String developmentType) {
		List<String[]> listDecisionResultRisks = new ArrayList<String[]>();

		for (String key : listKey) {
			if (key.contains("Risk")) {
				DmnDecisionTableResult decisionResult = decisionService.evaluateDecisionTableByKey(key, variables);
				List<Object> listRisks = decisionResult.collectEntries(developmentType);
				for (Object risk : listRisks) {
					listDecisionResultRisks.add(new String[] { key, risk.toString() });
				}
			}
		}

		return listDecisionResultRisks;
	}

	public List<ComplexityPair> getComplexityAll(List<String> listKey, VariableMap variables, String developmentType) {
		List<ComplexityPair> listDecisionResult = new ArrayList<ComplexityPair>();
		for (String key : listKey) {
			if (!(key.contains("decisionSoftware")) && !(key.contains("Risk"))) {
				DmnDecisionTableResult decisionResult = decisionService.evaluateDecisionTableByKey(key, variables);
				List<Map<String, Object>> decisionResultMapList = decisionResult.getResultList();
				for (Map<String, Object> decisionResultMap : decisionResultMapList) {
					for (Map.Entry<String, Object> entry : decisionResultMap.entrySet()) {
						if (entry.getKey().contains(developmentType)) {
							String outputKey = entry.getKey();
							Object outputValue = entry.getValue();
							System.out.println(entry.getKey() + " - " + entry.getValue());
							listDecisionResult.add(new ComplexityPair(outputKey, outputValue.toString()));
						}
					}
				}
			}
		}
		return listDecisionResult;
	}

}
