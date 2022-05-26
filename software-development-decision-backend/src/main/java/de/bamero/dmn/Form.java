package de.bamero.dmn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.camunda.bpm.model.dmn.instance.Decision;
import org.camunda.bpm.model.dmn.instance.DecisionTable;
import org.camunda.bpm.model.dmn.instance.Input;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.json.JSONArray;
import org.json.JSONObject;

public class Form {

	public static List<JSONObject> createJsonObjectForCharacteristic(DmnModelInstance modelInstance,
			Iterator<ModelElementInstance> iteratorDecision) {
		ModelElementType decisionTableType = modelInstance.getModel().getType(DecisionTable.class);
		ModelElementType inputType = modelInstance.getModel().getType(Input.class);

		List<JSONObject> object = new ArrayList<JSONObject>();

		while (iteratorDecision.hasNext()) {
			Decision decision = (Decision) iteratorDecision.next();
			if (!decision.getId().contains("Risk") && !decision.getId().contains("decisionSoftware")) {

				System.out.println("-------- " + decision.getId());

				JSONObject objectProperties = new JSONObject();
				JSONObject objectCharacteristic = new JSONObject();

				Collection<ModelElementInstance> collectionDecisionTable = decision
						.getChildElementsByType(decisionTableType);
				Iterator<ModelElementInstance> iteratorDecisionTable = collectionDecisionTable.iterator();
				Iterator<ModelElementInstance> iteratorInput = iteratorDecisionTable.next()
						.getChildElementsByType(inputType).iterator();

				JSONArray jsonArray = Form.createJsonArrayForProperties(iteratorInput, decision.getId());

				objectProperties.put("elements", jsonArray);
				objectProperties.put("title", decision.getName());
				objectProperties.put("name", decision.getId());
				objectCharacteristic.put(decision.getId(), objectProperties);
				object.add(objectProperties);
			}
		}
		return object;
	}

	public static JSONArray createJsonArrayForProperties(Iterator<ModelElementInstance> iteratorInput,
			String characteristic) {
		JSONArray jsonArray = new JSONArray();
		while (iteratorInput.hasNext()) {
			JSONObject properties = new JSONObject();
			Input currentPosition = (Input) iteratorInput.next();
			properties.put("title", currentPosition.getLabel());
			System.out.println(currentPosition.getLabel());

			String dataType = currentPosition.getInputExpression().getTypeRef();
			System.out.println(dataType);
			System.out.println(currentPosition.getInputExpression().getTextContent());
			properties.put("name", currentPosition.getInputExpression().getTextContent());
			properties.put("isRequired", true);
			switch (dataType) {
			case "string":
				properties.put("type", "dropdown");
				System.out.println("### " + currentPosition.getInputValues().getTextContent());
				String options = currentPosition.getInputValues().getTextContent();

				options = options.replace("\"", "");
				String[] optionArray = options.split(",");
				properties.put("choices", optionArray);
				break;
			case "integer":
				properties.put("type", "text");
				properties.put("inputType", "number");
				break;
			default:
				properties.put("type", dataType);
			}
			jsonArray.put(properties);

		}

		JSONObject choicesEvaluationImportance = new JSONObject();
		choicesEvaluationImportance.put("value", "importance");
		choicesEvaluationImportance.put("text", "Die Charakteristik ist sehr wichtig");

		JSONObject choicesEvaluationUncertainty = new JSONObject();
		choicesEvaluationUncertainty.put("value", "uncertainty");
		choicesEvaluationUncertainty.put("text",
				"Bei der Beantwortung der Fragen der Charakteristik bin ich mir unsicher");

		JSONArray choicesEvaluation = new JSONArray();
		choicesEvaluation.put(choicesEvaluationImportance);
		choicesEvaluation.put(choicesEvaluationUncertainty);

		JSONObject choices = new JSONObject();
		choices.put("type", "checkbox");
		choices.put("name", characteristic);
		choices.put("title", "Beurteilung");
		choices.put("choices", choicesEvaluation);

		JSONArray panelArray = new JSONArray();
		panelArray.put(choices);

		JSONObject panel = new JSONObject();
		panel.put("type", "panel");
		panel.put("innerIndent", 1);
		panel.put("elements", panelArray);

		jsonArray.put(panel);
		return jsonArray;
	}
}
