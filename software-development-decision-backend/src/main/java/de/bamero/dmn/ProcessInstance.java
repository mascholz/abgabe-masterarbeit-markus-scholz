package de.bamero.dmn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.json.JSONObject;

import de.bamero.dmn.enums.DevelopmentType;
import de.bamero.dmn.objects.Characteristic;
import de.bamero.dmn.objects.Rating;

public class ProcessInstance {

	public JSONObject processInstance(Object object) {
		// Input

		InputData inputData = new InputData(object);
		VariableMap variables = inputData.loadInput();
		List<Rating> listRating = inputData.loadRating();

		// new
		DevelopmentType[] developmentTypeArray = DevelopmentType.values();

		HashMap<String, String> listKeyNew = DecisionApplication.getKeys();

		DecisionService decisionService = DecisionApplication.getService();

		List<Characteristic> characteristicList = new ArrayList<Characteristic>();

		for (Map.Entry<String, String> key : listKeyNew.entrySet()) {
			if (!(key.getKey().contains("decisionSoftware")) && !(key.getKey().contains("Risk"))) {
				Characteristic characteristic = new Characteristic();
				characteristic.setName(key.getValue());
				for (Rating rating : listRating) {
					if (key.getKey().equals(rating.getKey()) && rating.isUncertainty()) {
						characteristic.setUncertainty(true);
					}
					if (key.getKey().equals(rating.getKey()) && rating.isImportance()) {
						characteristic.setImportance(true);
					}
				}

				DmnDecisionTableResult decisionResult = decisionService.evaluateDecisionTableByKey(key.getKey(),
						variables);

				for (int i = 0; i < developmentTypeArray.length; i++) {
					List<Object> listRisks = decisionResult.collectEntries(developmentTypeArray[i].toString());
					for (Object risk : listRisks) {
						if (characteristic.isUncertainty()) {
							risk = Complexity.updateComplexity(risk.toString());
						}
						switch (developmentTypeArray[i]) {
						case noCode:
							characteristic.setNoCodeComplexity(risk.toString());
							break;
						case lowCode:
							characteristic.setLowCodeComplexity(risk.toString());
							break;
						case code:
							characteristic.setCodeComplexity(risk.toString());
							break;
						}
					}
				}

				DmnDecisionTableResult decisionResultRisk = decisionService
						.evaluateDecisionTableByKey(key.getKey().concat("Risk"), variables);
				for (int i = 0; i < developmentTypeArray.length; i++) {
					List<Object> listRisks = decisionResultRisk.collectEntries(developmentTypeArray[i].toString());
					String riskString = "";
					for (Object risk : listRisks) {
						riskString = riskString.concat(" " + risk.toString());
					}

					switch (developmentTypeArray[i]) {
					case noCode:
						characteristic.setNoCodeRisk(riskString);
						break;
					case lowCode:
						characteristic.setLowCodeRisk(riskString);
						break;
					case code:
						characteristic.setCodeRisk(riskString);
						break;
					}
				}
				characteristicList.add(characteristic);
			}
		}

		List<Map<String, Object>> conclusionList = decisionService
				.evaluateDecisionTableByKey("Decision_1xdcqhoRisk", variables).getResultList();

		Map<String, Double> effort = Complexity.getEffort(characteristicList);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("risks", characteristicList);
		jsonObject.put("conclusionResultList", conclusionList);
		jsonObject.put("effort", effort);

		return jsonObject;
	}

}
